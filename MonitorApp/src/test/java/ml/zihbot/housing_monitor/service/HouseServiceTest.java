package ml.zihbot.housing_monitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.repository.HouseRepository;

@SpringBootTest
public class HouseServiceTest {
    
    @Mock
    private HouseRepository houseRepository;
    
    @InjectMocks
    private HouseService houseService;

    private static List<House> houses;

    @BeforeAll
    public static void setup() {
        houses = new ArrayList<>();
        houses.add(new House("https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/32084485"));
        houses.add(new House("https://ingatlan.com/xi-ker/elado+lakas/tegla-epitesu-lakas/32123745"));
    }

    @Test
    public void isHouseExists_HouseExists_True() {
        String url = "https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/32084485";
        Mockito.when(houseRepository.findByUrl(url)).thenReturn(Collections.singletonList(new House(url)));

        boolean response = houseService.isHouseExists(url);

        assertTrue(response);
    }

    @Test
    public void isHouseExists_HouseNotExists_False() {
        String url = "https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/32084485";
        Mockito.when(houseRepository.findByUrl(url)).thenReturn(Collections.emptyList());

        boolean response = houseService.isHouseExists(houses.get(0).getUrl());

        assertFalse(response);
    }

    @Test
    public void getUrl_Exists_String() {
        Long id = 1L;
        String url = "https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/32084485";
        House house = new House(url);
        house.setId(id);
        Mockito.when(houseRepository.findById(id)).thenReturn(Optional.of(house));

        String response = houseService.getUrl(id);

        assertEquals(url, response);
    }
}
