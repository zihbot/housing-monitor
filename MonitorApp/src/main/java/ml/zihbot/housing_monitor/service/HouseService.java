package ml.zihbot.housing_monitor.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.zihbot.housing_monitor.dto.HouseListing;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.dto.mapper.HouseMapper;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.entity.Property;
import ml.zihbot.housing_monitor.repository.HouseRepository;

@Service
public class HouseService {
    @Autowired
    private HouseRepository houseRepository;

    public List<HouseListing> getAllHouses() {
        return houseRepository.findAll().stream()
            .map(HouseMapper::houseToHouseListingDto)
            .collect(Collectors.toList());
    }

    public List<Long> getAllHouseIds() {
        return houseRepository.findAll().stream()
            .map(House::getId)
            .collect(Collectors.toList());
    }

    public List<KeyValuePair> getProperties(Long houseId) {
        House house = houseRepository.findById(houseId).get();

        return house.getProperties().stream()
            .map(HouseMapper::propertyToKeyValuePair)
            .collect(Collectors.toList());
    }

    public void setProperties(Long houseId, List<KeyValuePair> props) {
        House house = houseRepository.findById(houseId).get();

        for (KeyValuePair kvp: props) {
            String key = kvp.getKey();
            Property prop = house.getProperties().stream().filter(p -> key.equals(p.getKey())).findAny().orElse(null);
            if (prop == null) {
                prop = new Property(house, key);
                house.getProperties().add(prop);
            }
            prop.setValue(kvp.getValue());
        }

        houseRepository.save(house);
    }

    public Long createHouse(String url) {
        if (isHouseExists(url)) throw new EntityExistsException("House with url already exists!");
        House house = new House(url);
        house = houseRepository.saveAndFlush(house);
        return house.getId();
    }

    public boolean isHouseExists(String url) {
        return houseRepository.findByUrl(url).size() != 0;
    }

    public String getUrl(Long houseId) {
        return houseRepository.findById(houseId).get().getUrl();
    }
}
