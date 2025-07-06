package kaspi.kz.car_service_station.dto.requests;

import kaspi.kz.car_service_station.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class UpdateRequestDto {
    private String username;
    private String password;
    private UUID uuid;
    private Status status;
    private String description;
}
