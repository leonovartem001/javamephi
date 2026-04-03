package com.example.missions.service;

import com.example.missions.model.Mission;
import com.example.missions.model.Sorcerer;
import com.example.missions.model.Technique;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringJoiner;

public class MissionFormatter {
    private final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("ru", "RU"));

    public String format(Mission mission) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        joiner.add("=== СВОДКА ПО МИССИИ ===");
        joiner.add("ID миссии       : " + mission.getMissionId());
        joiner.add("Дата            : " + mission.getDate());
        joiner.add("Локация         : " + mission.getLocation());
        joiner.add("Исход           : " + mission.getOutcome());
        joiner.add("Ущерб           : " + numberFormat.format(mission.getDamageCost()));
        if (mission.getCurse() != null) {
            joiner.add("Цель            : " + mission.getCurse().getName());
            joiner.add("Уровень угрозы  : " + mission.getCurse().getThreatLevel());
        }
        joiner.add("");
        joiner.add("Участники:");
        if (mission.getSorcerers().isEmpty()) {
            joiner.add("  — нет данных");
        } else {
            for (Sorcerer sorcerer : mission.getSorcerers()) {
                joiner.add("  • " + sorcerer.getName() + " [" + sorcerer.getRank() + "]");
            }
        }
        joiner.add("");
        joiner.add("Техники:");
        if (mission.getTechniques().isEmpty()) {
            joiner.add("  — нет данных");
        } else {
            for (Technique technique : mission.getTechniques()) {
                joiner.add("  • " + technique.getName() + " | тип: " + technique.getType()
                        + " | владелец: " + technique.getOwner()
                        + " | урон: " + numberFormat.format(technique.getDamage()));
            }
        }
        joiner.add("");
        joiner.add("Комментарий     : " + mission.getComment());
        return joiner.toString();
    }
}
