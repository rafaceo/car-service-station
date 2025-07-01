package kaspi.kz.car_service_station.controllers;

import kaspi.kz.car_service_station.dto.RequestCreateDto;
import kaspi.kz.car_service_station.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/create")
    public ResponseEntity<Object> createRequest(@RequestBody RequestCreateDto requestCreateDto) {
        return requestService.createRequest(requestCreateDto);
    }
}
