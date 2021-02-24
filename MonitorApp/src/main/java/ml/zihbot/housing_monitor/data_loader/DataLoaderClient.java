package ml.zihbot.housing_monitor.data_loader;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ml.zihbot.housing_monitor.dto.KeyValuePair;
import reactor.core.publisher.Mono;

@Service
public class DataLoaderClient {
    WebClient client;

    public DataLoaderClient() {
        this.client = WebClient.create("http://127.0.0.1:3001");
    }
    
    public List<KeyValuePair> getPairs(String url) {
        Mono<KeyValuePair[]> result = client.get().uri("/pairs")
            .attribute("url", url).retrieve().bodyToMono(KeyValuePair[].class);
        return Arrays.asList(result.block());
    }
}
