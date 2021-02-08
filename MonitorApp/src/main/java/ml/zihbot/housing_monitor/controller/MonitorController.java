package ml.zihbot.housing_monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.zihbot.housing_monitor.config.DatabaseConfig;
import ml.zihbot.housing_monitor.data_loader.DataLoaderClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;

@RestController
@RequestMapping("rest")
public class MonitorController {
    @Autowired
    DataLoaderClient dataLoaderClient;

    @Autowired
    DatabaseConfig databaseConfig;

    @GetMapping("pairs")
    public List<KeyValuePair> getPairs() {
        System.out.println(databaseConfig.getPassword());
        return  dataLoaderClient.getPairs("https://ingatlan.com/ix-ker/elado+lakas/tegla-epitesu-lakas/31119133");
    }
}