package com.example.missions.parser;

import com.example.missions.model.Curse;
import com.example.missions.model.Mission;
import com.example.missions.model.MissionOutcome;
import com.example.missions.model.Sorcerer;
import com.example.missions.model.Technique;
import com.example.missions.model.ThreatLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TextMissionParser implements MissionParser {
    @Override
    public Mission parse(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        Map<String, String> fields = new HashMap<>();
        for (String line : lines) {
            if (line == null || line.isBlank() || !line.contains(":")) {
                continue;
            }
            String[] parts = line.split(":", 2);
            fields.put(parts[0].trim(), parts[1].trim());
        }

        Mission mission = new Mission();
        mission.setMissionId(fields.get("missionId"));
        mission.setDate(LocalDate.parse(fields.get("date")));
        mission.setLocation(fields.get("location"));
        mission.setOutcome(MissionOutcome.fromString(fields.get("outcome")));
        mission.setDamageCost(parseLong(fields.get("damageCost")));
        mission.setCurse(new Curse(fields.get("curse.name"), ThreatLevel.fromString(fields.get("curse.threatLevel"))));
        mission.setSorcerers(readSorcerers(fields));
        mission.setTechniques(readTechniques(fields));
        mission.setComment(firstNonBlank(fields.get("comment"), fields.get("note"), "—"));
        return mission;
    }

    private List<Sorcerer> readSorcerers(Map<String, String> fields) {
        Map<Integer, Sorcerer> map = new TreeMap<>();
        for (var entry : fields.entrySet()) {
            String key = entry.getKey();
            if (!key.startsWith("sorcerer[")) {
                continue;
            }
            int left = key.indexOf('[');
            int right = key.indexOf(']');
            int idx = Integer.parseInt(key.substring(left + 1, right));
            String property = key.substring(right + 2);
            Sorcerer s = map.computeIfAbsent(idx, i -> new Sorcerer());
            if ("name".equals(property)) s.setName(entry.getValue());
            if ("rank".equals(property)) s.setRank(entry.getValue());
        }
        return new ArrayList<>(map.values());
    }

    private List<Technique> readTechniques(Map<String, String> fields) {
        Map<Integer, Technique> map = new TreeMap<>();
        for (var entry : fields.entrySet()) {
            String key = entry.getKey();
            if (!key.startsWith("technique[")) {
                continue;
            }
            int left = key.indexOf('[');
            int right = key.indexOf(']');
            int idx = Integer.parseInt(key.substring(left + 1, right));
            String property = key.substring(right + 2);
            Technique t = map.computeIfAbsent(idx, i -> new Technique());
            if ("name".equals(property)) t.setName(entry.getValue());
            if ("type".equals(property)) t.setType(entry.getValue());
            if ("owner".equals(property)) t.setOwner(entry.getValue());
            if ("damage".equals(property)) t.setDamage(parseLong(entry.getValue()));
        }
        return new ArrayList<>(map.values());
    }

    private long parseLong(String value) {
        return value == null || value.isBlank() ? 0L : Long.parseLong(value.trim());
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return "";
    }
}
