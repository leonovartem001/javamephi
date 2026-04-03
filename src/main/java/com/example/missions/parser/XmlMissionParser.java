package com.example.missions.parser;

import com.example.missions.model.Curse;
import com.example.missions.model.Mission;
import com.example.missions.model.MissionOutcome;
import com.example.missions.model.Sorcerer;
import com.example.missions.model.Technique;
import com.example.missions.model.ThreatLevel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlMissionParser implements MissionParser {
    @Override
    public Mission parse(Path path) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(path.toFile());
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            Mission mission = new Mission();
            mission.setMissionId(getText(root, "missionId"));
            mission.setDate(LocalDate.parse(getText(root, "date")));
            mission.setLocation(getText(root, "location"));
            mission.setOutcome(MissionOutcome.fromString(getText(root, "outcome")));
            mission.setDamageCost(Long.parseLong(getText(root, "damageCost")));

            Element curseElement = (Element) root.getElementsByTagName("curse").item(0);
            mission.setCurse(new Curse(getText(curseElement, "name"), ThreatLevel.fromString(getText(curseElement, "threatLevel"))));
            mission.setSorcerers(readSorcerers(root));
            mission.setTechniques(readTechniques(root));
            mission.setComment(firstNonBlank(optionalText(root, "comment"), optionalText(root, "note"), "—"));
            return mission;
        } catch (Exception e) {
            throw new IOException("Failed to parse XML file: " + path.getFileName(), e);
        }
    }

    private List<Sorcerer> readSorcerers(Element root) {
        List<Sorcerer> list = new ArrayList<>();
        NodeList nodes = root.getElementsByTagName("sorcerer");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            list.add(new Sorcerer(getText(el, "name"), getText(el, "rank")));
        }
        return list;
    }

    private List<Technique> readTechniques(Element root) {
        List<Technique> list = new ArrayList<>();
        NodeList nodes = root.getElementsByTagName("technique");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            list.add(new Technique(
                    getText(el, "name"),
                    getText(el, "type"),
                    getText(el, "owner"),
                    Long.parseLong(getText(el, "damage"))
            ));
        }
        return list;
    }

    private String getText(Element root, String tagName) {
        return root.getElementsByTagName(tagName).item(0).getTextContent().trim();
    }

    private String optionalText(Element root, String tagName) {
        NodeList nodes = root.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return "";
        Node node = nodes.item(0);
        return node == null ? "" : node.getTextContent().trim();
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) return value;
        }
        return "";
    }
}
