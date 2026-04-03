package com.example.missions;

import com.example.missions.model.Mission;
import com.example.missions.service.MissionLoader;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MissionLoaderTest {
    private final MissionLoader loader = new MissionLoader();

    @Test
    void shouldLoadMissionAFromTxt() throws Exception {
        Mission mission = loadSample("samples/variant-1/mission-a.txt");
        assertEquals("M-2024-017", mission.getMissionId());
        assertEquals("Токио, район Сибуя", mission.getLocation());
        assertEquals(2, mission.getSorcerers().size());
        assertEquals(2, mission.getTechniques().size());
    }

    @Test
    void shouldLoadMissionBFromJsonWithComment() throws Exception {
        Mission mission = loadSample("samples/variant-2/mission-b.json");
        assertEquals("M-2024-042", mission.getMissionId());
        assertEquals(1, mission.getSorcerers().size());
        assertEquals("Рёмен Сукуна (фрагмент)", mission.getCurse().getName());
        assertNotNull(mission.getComment());
    }

    @Test
    void shouldLoadMissionBFromXml() throws Exception {
        Mission mission = loadSample("samples/variant-2/mission-b.xml");
        assertEquals(8500000L, mission.getDamageCost());
        assertEquals(2, mission.getTechniques().size());
    }

    private Mission loadSample(String resource) throws Exception {
        URL url = getClass().getClassLoader().getResource(resource);
        assertNotNull(url, "Sample file not found: " + resource);
        return loader.load(Path.of(url.toURI()));
    }
}
