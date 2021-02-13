package ml.zihbot.housing_monitor.repository;

import org.springframework.data.repository.CrudRepository;
import ml.zihbot.housing_monitor.entity.Property;

public interface PropertyRepository extends CrudRepository<Property, Long> {

}
