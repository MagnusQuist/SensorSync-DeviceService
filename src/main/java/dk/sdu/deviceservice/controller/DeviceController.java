package dk.sdu.deviceservice.controller;

import dk.sdu.deviceservice.RequestTypes.SetGroupRequest;
import dk.sdu.deviceservice.feign.MessageServiceInterface;
import dk.sdu.deviceservice.response.DeviceDTO;
import dk.sdu.deviceservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @Autowired
    MessageServiceInterface messageServiceInterface;

    // GET ALL
    @GetMapping("/devices")
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceDTO> getDevices() {
        return deviceService.devices();
    }

    // GET
    @GetMapping("/devices/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO getDevice(@PathVariable UUID uuid) {
        return deviceService.device(uuid);
    }

    // IDENTIFY DEVICE
    @GetMapping("/devices/{uuid}/identify")
    @ResponseStatus(HttpStatus.OK)
    public void identifyDevice(@PathVariable UUID uuid) {
        messageServiceInterface.indetifyDevice(uuid);
    }

    // CREATE
    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDTO save(@RequestBody DeviceDTO device) {
        return deviceService.save(device);
    }

    // UPDATE
    @PutMapping("/devices/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO update(@RequestBody DeviceDTO device) {
        return deviceService.update(device);
    }

    // UPDATE GROUP
    @PutMapping("/devices/{uuid}/group")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO updateGroup(@PathVariable UUID uuid, @RequestBody SetGroupRequest group_uuid) {
        return deviceService.updateGroup(uuid, group_uuid);
    }

    // UPDATE lastPing
    @PutMapping("/devices/{uuid}/lastping")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDTO updateLastPing(@RequestBody DeviceDTO device) {
        return deviceService.updateLastPing(device);
    }

    // DELETE
    @DeleteMapping("/devices/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable UUID uuid) {
        return deviceService.delete(uuid);
    }

    // DELETE ALL
    @DeleteMapping("/devices")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAll() {
        return deviceService.deleteAll();
    }
}
