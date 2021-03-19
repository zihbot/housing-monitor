package ml.zihbot.housing_monitor.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ml.zihbot.housing_monitor.service.HouseService;
import ml.zihbot.housing_monitor.service.facade.DataDownloaderFacade;

@Service
public class PropertyUpdate {
    @Autowired
    private HouseService houseService;
    @Autowired
    private DataDownloaderFacade dataDownloaderFacade;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledPropertyUpdate() {
        logger.info("scheduledPropertyUpdate() started");
        for (Long houseId : houseService.getAllHouseIds()) {
            try {
                dataDownloaderFacade.updateHouseProperties(houseId);                
            } catch (Exception e) {
                logger.warn("scheduledPropertyUpdate() houseId={}\n{}", houseId, e);
            }
        }
        logger.info("scheduledPropertyUpdate() finished");
    }
}
