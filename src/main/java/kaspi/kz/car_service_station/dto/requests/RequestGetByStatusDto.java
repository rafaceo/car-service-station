package kaspi.kz.car_service_station.dto.requests;

import kaspi.kz.car_service_station.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class RequestGetByStatusDto {
    private String username;
    private String password;
    private Status status;
}
