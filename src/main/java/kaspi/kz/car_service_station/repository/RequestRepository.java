package kaspi.kz.car_service_station.repository;

import kaspi.kz.car_service_station.entity.Request;
import kaspi.kz.car_service_station.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {
    List<Request> getRequestsByUsername(String username);
    List<Request> findByStatus(Status status);
}
