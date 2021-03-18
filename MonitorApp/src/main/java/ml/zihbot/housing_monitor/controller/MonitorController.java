package ml.zihbot.housing_monitor.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.zihbot.housing_monitor.dto.HouseListing;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.dto.Url;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.repository.HouseRepository;
import ml.zihbot.housing_monitor.repository.PropertyRepository;
import ml.zihbot.housing_monitor.service.DataDownloaderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("rest")
public class MonitorController {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    DataDownloaderService dataDownloaderService;

    @GetMapping("properties/{houseId}")
    public List<KeyValuePair> getProperties(@PathVariable Long houseId) {
        House house = houseRepository.findById(houseId).get();

        return house.getProperties().stream().map(prop -> new KeyValuePair(prop.getKey(), prop.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping(value="house")
    public List<HouseListing> getHouses() {
        return houseRepository.findAll().stream().map(house -> new HouseListing(house.getId(), house.getUrl()))
                .collect(Collectors.toList());
    }

    @PostMapping(value="house")
    public ResponseEntity<?> postHouse(@RequestBody Url url) {
        List<House> houses = houseRepository.findByUrl(url.getUrl());
        House house = houses.isEmpty() ? new House(url.getUrl()) : houses.get(0);
        house = houseRepository.saveAndFlush(house);

        dataDownloaderService.updateProperties(house.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}