package ml.zihbot.housing_monitor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseListing {
    private Long id;
    private String url;

    public HouseListing() {
    }

    public HouseListing(Long id, String url) {
        this.id = id;
        this.url = url;
    }
}
