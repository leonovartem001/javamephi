package com.example.missions.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mission {
    private String missionId;
    private LocalDate date;
    private String location;
    private MissionOutcome outcome;
    private long damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();
    private String comment;

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public MissionOutcome getOutcome() { return outcome; }
    public void setOutcome(MissionOutcome outcome) { this.outcome = outcome; }
    public long getDamageCost() { return damageCost; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }
    public Curse getCurse() { return curse; }
    public void setCurse(Curse curse) { this.curse = curse; }
    public List<Sorcerer> getSorcerers() { return sorcerers; }
    public void setSorcerers(List<Sorcerer> sorcerers) { this.sorcerers = sorcerers; }
    public List<Technique> getTechniques() { return techniques; }
    public void setTechniques(List<Technique> techniques) { this.techniques = techniques; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
