package kaspi.kz.car_service_station.services;

import kaspi.kz.car_service_station.dto.requests.RequestCreateDto;
import kaspi.kz.car_service_station.entity.Request;
import kaspi.kz.car_service_station.entity.Status;
import kaspi.kz.car_service_station.logger.LoggerSlf4j;
import kaspi.kz.car_service_station.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;


    public ResponseEntity<Object> createRequest(RequestCreateDto requestCreateDto) {
        LoggerSlf4j logger = new LoggerSlf4j("createRequest", requestCreateDto.getUsername());
        Request request = new Request(requestCreateDto.getUsername(), Status.CREATED, LocalDateTime.now(),LocalDateTime.now());
        logger.info("Request created");
        requestRepository.save(request);
        logger.info("Request saved");
        return ResponseEntity.ok().body("New request succesfully created");
    }


}
