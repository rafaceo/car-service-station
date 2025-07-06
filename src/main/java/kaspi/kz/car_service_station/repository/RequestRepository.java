package kaspi.kz.car_service_station.repository;

import jakarta.transaction.Transactional;
import kaspi.kz.car_service_station.entity.Request;
import kaspi.kz.car_service_station.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {
    List<Request> getRequestsByUsername(String username);
    List<Request> findByStatus(Status status);
    @Modifying
    @Transactional
    @Query("UPDATE Request r SET r.status = :status, r.description = :description, r.updatedBy = :updatedBy WHERE r.id = :uuid")
    void updateStatus(UUID uuid, Status status, String description, String updatedBy);
    boolean existsById(UUID uuid);
}
