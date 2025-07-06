package kaspi.kz.car_service_station.kafka.producer;

import kaspi.kz.car_service_station.dto.requests.UpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestStatusProducer {
    private final KafkaTemplate<String, UpdateRequestDto> kafkaTemplate;

    public void sendStatusUpdate(UpdateRequestDto dto) {
            kafkaTemplate.send("request-status-updates", dto);
    }
}

