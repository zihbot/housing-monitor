package ml.zihbot.housing_monitor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ml.zihbot.housing_monitor.entity.House;

public interface HouseRepository extends JpaRepository<House, Long> {
    @Override
    List<House> findAll();
    
    List<House> findByUrl(String url);
}
