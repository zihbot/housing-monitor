package ml.zihbot.housing_monitor.controller;

import java.util.List;

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
import ml.zihbot.housing_monitor.service.HouseService;
import ml.zihbot.housing_monitor.service.facade.DataDownloaderFacade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("rest")
public class MonitorController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private DataDownloaderFacade dataDownloaderFacade;

    @GetMapping("properties/{houseId}")
    public List<KeyValuePair> getProperties(@PathVariable Long houseId) {
        return houseService.getProperties(houseId);
    }

    @GetMapping(value="house")
    public List<HouseListing> getHouses() {
        return houseService.getAllHouses();
    }

    @PostMapping(value="house")
    public ResponseEntity<?> postHouse(@RequestBody Url url) {
        Long houseId = houseService.createHouse(url.getUrl());
        dataDownloaderFacade.updateHouseProperties(houseId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}