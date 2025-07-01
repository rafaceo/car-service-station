package kaspi.kz.car_service_station.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@RequiredArgsConstructor
public class ResponseCreateDto {
    private String message;
}
