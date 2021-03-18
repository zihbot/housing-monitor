package ml.zihbot.housing_monitor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ml.zihbot.housing_monitor.data_loader.DataLoaderClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.entity.Property;
import ml.zihbot.housing_monitor.repository.HouseRepository;
import ml.zihbot.housing_monitor.repository.PropertyRepository;

@Service
public class DataDownloaderService {
    @Autowired
    DataLoaderClient dataLoaderClient;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    PropertyRepository propertyRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void updateProperties(Long houseId) {
        House house = houseRepository.findById(houseId).get();
        List<KeyValuePair> pairs = dataLoaderClient.getPairs(house.getUrl());
        logger.info("updateProperties() pairs.size()={}", pairs.size());

        List<Property> properties = house.getProperties();
        if (properties == null) properties = new ArrayList<>();
        for (KeyValuePair pair : pairs) {
            Optional<Property> prop = properties.stream()
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

            if (!properties.contains(property)) {
                properties.add(property);
            }
        }
        propertyRepository.saveAll(properties);
        propertyRepository.flush();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledPropertyUpdate() {
        for (House house : houseRepository.findAll()) {
            updateProperties(house.getId());
        }
    }
}
