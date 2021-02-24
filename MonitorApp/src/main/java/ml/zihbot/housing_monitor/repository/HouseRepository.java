package ml.zihbot.housing_monitor.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ml.zihbot.housing_monitor.entity.House;

public interface HouseRepository extends CrudRepository<House, Long> {
    List<House> findByUrl(String url);
}
