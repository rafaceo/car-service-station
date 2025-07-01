package kaspi.kz.car_service_station.repository;

import kaspi.kz.car_service_station.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {
}
