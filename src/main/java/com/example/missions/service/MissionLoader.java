package com.example.missions.service;

import com.example.missions.model.Mission;
import com.example.missions.parser.JsonMissionParser;
import com.example.missions.parser.MissionParser;
import com.example.missions.parser.TextMissionParser;
import com.example.missions.parser.XmlMissionParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public class MissionLoader {

    public Mission load(Path path) throws IOException {
        MissionParser parser = resolveParser(path);
        return parser.parse(path);
    }

    private MissionParser resolveParser(Path path) {
        String fileName = path.getFileName().toString().toLowerCase(Locale.ROOT);
        if (fileName.endsWith(".json")) {
            return new JsonMissionParser();
        }
        if (fileName.endsWith(".xml")) {
            return new XmlMissionParser();
        }
        if (fileName.endsWith(".txt")) {
            return new TextMissionParser();
        }
        throw new IllegalArgumentException("Unsupported file format: " + fileName);
    }
}
