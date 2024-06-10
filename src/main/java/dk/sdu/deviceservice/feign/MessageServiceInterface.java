package dk.sdu.deviceservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@FeignClient("MESSAGE-SERVICE")
public interface MessageServiceInterface {
    @GetMapping("api/v1/mqtt/device/identify")
    @ResponseStatus(HttpStatus.OK)
    void indetifyDevice(@PathVariable UUID uuid);
}
