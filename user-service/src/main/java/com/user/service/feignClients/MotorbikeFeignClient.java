package com.user.service.feignClients;

import com.user.service.integration.Motorbike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "motorbike-service")
public interface MotorbikeFeignClient {

    @PostMapping("/motorbikes")
    public Motorbike saveMotorbike(Motorbike motorbike);

    @GetMapping("/motorbikes/user/{userId}")
    public List<Motorbike> getMotorbikes(@PathVariable("userId") Long userId);

}