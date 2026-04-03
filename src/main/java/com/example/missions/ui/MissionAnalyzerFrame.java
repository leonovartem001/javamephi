package com.example.missions.ui;

import com.example.missions.model.Mission;
import com.example.missions.service.MissionFormatter;
import com.example.missions.service.MissionLoader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;

public class MissionAnalyzerFrame extends JFrame {
    private final JTextArea outputArea = new JTextArea();
    private final JLabel fileLabel = new JLabel("Файл не выбран");
    private final MissionLoader missionLoader = new MissionLoader();
    private final MissionFormatter missionFormatter = new MissionFormatter();

    public MissionAnalyzerFrame() {
        super("Локальный анализатор миссий магов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initUi();
    }

    private void initUi() {
        JButton openButton = new JButton("Открыть файл миссии");
        openButton.addActionListener(e -> openMissionFile());

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(openButton, BorderLayout.WEST);
        topPanel.add(fileLabel, BorderLayout.CENTER);

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    private void openMissionFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Выберите файл миссии");
        chooser.setFileFilter(new FileNameExtensionFilter("Mission files", "txt", "json", "xml"));

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        fileLabel.setText(file.getAbsolutePath());

        try {
            Mission mission = missionLoader.load(file.toPath());
            outputArea.setText(missionFormatter.format(mission));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Не удалось обработать файл: " + ex.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
