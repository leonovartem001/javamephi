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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonMissionParser implements MissionParser {
    @Override
    public Mission parse(Path path) throws IOException {
        String json = Files.readString(path);
        Mission mission = new Mission();
        mission.setMissionId(findString(json, "missionId", true));
        mission.setDate(LocalDate.parse(findString(json, "date", true)));
        mission.setLocation(findString(json, "location", true));
        mission.setOutcome(MissionOutcome.fromString(findString(json, "outcome", true)));
        mission.setDamageCost(findLong(json, "damageCost", true));
        mission.setCurse(new Curse(
                findNestedString(json, "curse", "name", true),
                ThreatLevel.fromString(findNestedString(json, "curse", "threatLevel", true))
        ));
        mission.setSorcerers(readSorcerers(json));
        mission.setTechniques(readTechniques(json));
        mission.setComment(firstNonBlank(findOptionalString(json, "comment"), findOptionalString(json, "note"), "—"));
        return mission;
    }

    private List<Sorcerer> readSorcerers(String json) throws IOException {
        List<Sorcerer> result = new ArrayList<>();
        String array = findArrayBody(json, "sorcerers", true);
        Matcher matcher = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL).matcher(array);
        while (matcher.find()) {
            String obj = matcher.group(1);
            result.add(new Sorcerer(findString(obj, "name", true), findString(obj, "rank", true)));
        }
        return result;
    }

    private List<Technique> readTechniques(String json) throws IOException {
        List<Technique> result = new ArrayList<>();
        String array = findArrayBody(json, "techniques", true);
        Matcher matcher = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL).matcher(array);
        while (matcher.find()) {
            String obj = matcher.group(1);
            result.add(new Technique(
                    findString(obj, "name", true),
                    findString(obj, "type", true),
                    findString(obj, "owner", true),
                    findLong(obj, "damage", true)
            ));
        }
        return result;
    }

    private String findNestedString(String json, String objectField, String innerField, boolean required) throws IOException {
        Pattern p = Pattern.compile("\\\"" + Pattern.quote(objectField) + "\\\"\\s*:\\s*\\{(.*?)\\}", Pattern.DOTALL);
        Matcher m = p.matcher(json);
        if (!m.find()) {
            if (required) throw new IOException("Object not found in JSON: " + objectField);
            return "";
        }
        return findString(m.group(1), innerField, required);
    }

    private String findArrayBody(String json, String fieldName, boolean required) throws IOException {
        Pattern p = Pattern.compile("\\\"" + Pattern.quote(fieldName) + "\\\"\\s*:\\s*\\[(.*?)]", Pattern.DOTALL);
        Matcher m = p.matcher(json);
        if (!m.find()) {
            if (required) throw new IOException("Array field not found in JSON: " + fieldName);
            return "";
        }
        return m.group(1);
    }

    private String findOptionalString(String json, String fieldName) {
        try {
            return findString(json, fieldName, false);
        } catch (IOException e) {
            return "";
        }
    }

    private String findString(String json, String fieldName, boolean required) throws IOException {
        Pattern p = Pattern.compile("\\\"" + Pattern.quote(fieldName) + "\\\"\\s*:\\s*\\\"(.*?)\\\"", Pattern.DOTALL);
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1).trim();
        }
        if (required) throw new IOException("Field not found in JSON: " + fieldName);
        return "";
    }

    private long findLong(String json, String fieldName, boolean required) throws IOException {
        Pattern p = Pattern.compile("\\\"" + Pattern.quote(fieldName) + "\\\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return Long.parseLong(m.group(1));
        }
        if (required) throw new IOException("Numeric field not found in JSON: " + fieldName);
        return 0L;
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) return value;
        }
        return "";
    }
}
