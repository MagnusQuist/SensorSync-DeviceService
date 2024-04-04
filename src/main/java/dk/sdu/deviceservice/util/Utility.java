package dk.sdu.deviceservice.util;

import dk.sdu.deviceservice.Entity.Device;
import dk.sdu.deviceservice.response.DeviceDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
public class Utility {
    private Utility() {}

    public static NoSuchElementException notFound(UUID uuid) {
        log.error("Device with uuid=" + uuid + " not found.");
        return new NoSuchElementException("Device with uuid=" + uuid + " not found.");
    }

    public static DeviceDTO mapToDeviceDTO(Device device) {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .uuid(device.getUuid())
                .name(device.getName())
                .athena_version(device.getAthena_version())
                .toit_firmware_version(device.getToit_firmware_version())
                .date_created(device.getDate_created())
                .build();
        log.info("Device details : {}", deviceDTO);
        return deviceDTO;
    }
}
