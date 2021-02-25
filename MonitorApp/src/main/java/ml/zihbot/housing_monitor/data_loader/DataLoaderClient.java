package ml.zihbot.housing_monitor.data_loader;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ml.zihbot.housing_monitor.config.DataDownloaderConfig;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import reactor.core.publisher.Mono;

@Service
public class DataLoaderClient {
    @Autowired
    private DataDownloaderConfig dataDownloaderConfig;

    WebClient client;

    @PostConstruct
    public void init() {
        this.client = WebClient.create(dataDownloaderConfig.getUrl());
    }
    
    public List<KeyValuePair> getPairs(String url) {
        Mono<KeyValuePair[]> result = client.get().uri("/pairs")
            .attribute("url", url).retrieve().bodyToMono(KeyValuePair[].class);
        return Arrays.asList(result.block());
    }
}
