package ml.zihbot.housing_monitor.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.zihbot.housing_monitor.data_loader.DataLoaderClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;

@Service
public class DataDownloaderService {
    @Autowired
    private DataLoaderClient dataLoaderClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<KeyValuePair> getProperties(String url) {
        logger.info("getProperties()");
        return dataLoaderClient.getPairs(url);
    }
}
