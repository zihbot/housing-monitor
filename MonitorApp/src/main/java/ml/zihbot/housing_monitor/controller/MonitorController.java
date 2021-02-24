package ml.zihbot.housing_monitor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.zihbot.housing_monitor.data_loader.DataLoaderClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.dto.Url;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.entity.Property;
import ml.zihbot.housing_monitor.repository.HouseRepository;
import ml.zihbot.housing_monitor.repository.PropertyRepository;
import ml.zihbot.housing_monitor.service.DataDownloaderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("rest")
public class MonitorController {
    @Autowired
    DataLoaderClient dataLoaderClient;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    DataDownloaderService dataDownloaderService;

    @GetMapping("pairs")
    public List<KeyValuePair> getPairs() {
        String url = "https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133";
        List<House> houses = houseRepository.findByUrl(url);
        House house = houses.isEmpty() ? new House(url) : houses.get(0);
        List<KeyValuePair> pairs = dataLoaderClient.getPairs(url);

        for (KeyValuePair pair : pairs) {
            Optional<Property> prop = house.getProperties().stream()
                    .filter(p -> p.getKey().equals(pair.getKey())).findAny();
            Property property = null;
            if (prop.isPresent()) {
                property = prop.get();
            } else {
                property = new Property();
                property.setHouse(house);
                property.setKey(pair.getKey());
            }
            property.setValue(pair.getValue());
            propertyRepository.save(property);
        }
        houseRepository.save(house);
        return pairs;
    }

    @PostMapping(value="saveHouse")
    public ResponseEntity<?> postMethodName(@RequestBody Url url) {
        List<House> houses = houseRepository.findByUrl(url.getUrl());
        House house = houses.isEmpty() ? new House(url.getUrl()) : houses.get(0);
        houseRepository.save(house);

        dataDownloaderService.saveProperties(house);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}