package kaspi.kz.car_service_station.controllers;

import kaspi.kz.car_service_station.dto.requests.RequestCreateDto;
import kaspi.kz.car_service_station.dto.requests.RequestGetByStatusDto;
import kaspi.kz.car_service_station.dto.requests.RequestGetByUserNameDto;
import kaspi.kz.car_service_station.dto.requests.UpdateRequestDto;
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

    @PostMapping("/findByName")
    public ResponseEntity<Object> findByName(@RequestBody RequestGetByUserNameDto requestGetByUserNameDto) {
        return requestService.getRequestsByName(requestGetByUserNameDto);
    }

    @PostMapping("/findByStatus")
    public ResponseEntity<Object> findByStatus(@RequestBody RequestGetByStatusDto requestGetByStatusDto) {
        return requestService.getRequestsByStatus(requestGetByStatusDto);
    }

    @PostMapping("/updateRequest")
    public ResponseEntity<Object> updateRequest(@RequestBody UpdateRequestDto dto) {
        return requestService.updateRequest(dto);
    }

}
