package kaspi.kz.car_service_station.dto.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class RequestCreateDto {
    private String username;
    private String password;
}
