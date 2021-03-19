package ml.zihbot.housing_monitor.service.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ml.zihbot.housing_monitor.dto.KeyValuePair;
import ml.zihbot.housing_monitor.service.DataDownloaderService;
import ml.zihbot.housing_monitor.service.HouseService;

@Component
public class DataDownloaderFacade {
    @Autowired
    private HouseService houseService;
    @Autowired
    private DataDownloaderService dataDownloaderService;
    
    public void updateHouseProperties(Long houseId) {
        String url = houseService.getUrl(houseId);
        List<KeyValuePair> props = dataDownloaderService.getProperties(url);
        houseService.setProperties(houseId, props);
    }
}
