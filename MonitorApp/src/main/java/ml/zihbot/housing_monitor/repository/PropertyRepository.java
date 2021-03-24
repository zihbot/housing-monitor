package ml.zihbot.housing_monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ml.zihbot.housing_monitor.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
