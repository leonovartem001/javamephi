package com.example.missions.parser;

import com.example.missions.model.Mission;

import java.io.IOException;
import java.nio.file.Path;

public interface MissionParser {
    Mission parse(Path path) throws IOException;
}
