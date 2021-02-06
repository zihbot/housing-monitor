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
        this.client = WebClient.create("https://418fe6d5-a37a-4336-8552-b996d92180fe.mock.pstmn.io");
    }
    
    public List<KeyValuePair> getPairs(String url) {
        Mono<KeyValuePair[]> result = client.get().uri("/pairs")
            .attribute("url", url).retrieve().bodyToMono(KeyValuePair[].class);
        return Arrays.asList(result.block());
    }
}
