package com.example.missions.model;

public enum MissionOutcome {
    SUCCESS,
    FAILURE,
    PARTIAL_SUCCESS,
    UNKNOWN;

    public static MissionOutcome fromString(String value) {
        if (value == null || value.isBlank()) {
            return UNKNOWN;
        }
        try {
            return MissionOutcome.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
