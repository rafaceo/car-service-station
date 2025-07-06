package kaspi.kz.car_service_station.kafka.consumer;

import kaspi.kz.car_service_station.dto.requests.UpdateRequestDto;
import kaspi.kz.car_service_station.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestStatusConsumer {

    private final RequestRepository requestRepository;

    @KafkaListener(topics = "request-status-updates", groupId = "request-service")
    public void consume(UpdateRequestDto dto) {
            requestRepository.updateStatus(dto.getUuid(), dto.getStatus(), dto.getDescription(), dto.getUsername());
    }
}
