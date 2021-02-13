package ml.zihbot.housing_monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.zihbot.housing_monitor.data_loader.DataLoaderClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.repository.HouseRepository;

@RestController
@RequestMapping("rest")
public class MonitorController {
    @Autowired
    DataLoaderClient dataLoaderClient;

    @Autowired
    HouseRepository houseRepository;

    @GetMapping("pairs")
    public List<KeyValuePair> getPairs() {
        String url = "https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133";
        List<House> houses = houseRepository.findByUrl(url);
        House house = houses.isEmpty() ? new House(url) : houses.get(0);
        houseRepository.save(house);
        return  dataLoaderClient.getPairs(url);
    }
}