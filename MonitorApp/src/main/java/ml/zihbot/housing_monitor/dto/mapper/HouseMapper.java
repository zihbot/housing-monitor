package ml.zihbot.housing_monitor.dto.mapper;

import ml.zihbot.housing_monitor.dto.HouseListing;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.entity.House;
import ml.zihbot.housing_monitor.entity.Property;

public class HouseMapper {
    public static HouseListing houseToHouseListingDto(House house) {
        HouseListing houseListing = new HouseListing();
        houseListing.setId(house.getId());
        houseListing.setUrl(house.getUrl());
        return houseListing;
    }

    public static KeyValuePair propertyToKeyValuePair(Property property) {
        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setKey(property.getKey());
        keyValuePair.setValue(property.getValue());
        return keyValuePair;
    }
}
