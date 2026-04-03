package com.example.missions;

import com.example.missions.ui.MissionAnalyzerFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MissionAnalyzerFrame frame = new MissionAnalyzerFrame();
            frame.setVisible(true);
        });
    }
}
