package dk.sdu.deviceservice.util;

import dk.sdu.deviceservice.Entity.Device;
import dk.sdu.deviceservice.Entity.Group;
import dk.sdu.deviceservice.response.DeviceDTO;
import dk.sdu.deviceservice.response.GroupDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
public class Utility {
    private Utility() {}

    public static NoSuchElementException notFound(UUID uuid) {
        log.error("Entity with uuid=" + uuid + " not found.");
        return new NoSuchElementException("Entity with uuid=" + uuid + " not found.");
    }

    public static DeviceDTO mapToDeviceDTO(Device device) {
        UUID group_uuid = null;
        if (device.getGroup() != null) {
            group_uuid = device.getGroup().getUuid();
        }
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .uuid(device.getUuid())
                .name(device.getName())
                .athena_version(device.getAthena_version())
                .toit_firmware_version(device.getToit_firmware_version())
                .ip_address(device.getIp_address())
                .jaguar_port(device.getJaguar_port())
                .last_ping(device.getLast_ping())
                .online(device.isOnline())
                .date_created(device.getDate_created())
                .group_uuid(group_uuid)
                .build();
        log.info("Device details : {}", deviceDTO);
        return deviceDTO;
    }

    public static GroupDTO mapToGroupDTO(Group group) {
        GroupDTO groupDTO = GroupDTO.builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .location(group.getLocation())
                .build();
        log.info("Group details : {}", groupDTO);
        return groupDTO;
    }
}
