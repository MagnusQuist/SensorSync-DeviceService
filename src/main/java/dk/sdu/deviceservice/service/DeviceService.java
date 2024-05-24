package dk.sdu.deviceservice.service;

import dk.sdu.deviceservice.Entity.Device;
import dk.sdu.deviceservice.Entity.Group;
import dk.sdu.deviceservice.RequestTypes.SetGroupRequest;
import dk.sdu.deviceservice.repository.IDeviceRepository;
import dk.sdu.deviceservice.repository.IGroupRepository;
import dk.sdu.deviceservice.response.DeviceDTO;
import dk.sdu.deviceservice.util.Utility;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private IDeviceRepository repository;

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private ObservationRegistry registry;

    public List<DeviceDTO> devices() {
        return Observation.createNotStarted("getDevices", registry)
                .observe(() -> repository.findAll().stream().map(Utility::mapToDeviceDTO).toList());
    }

    public DeviceDTO device(UUID uuid) {
        return Observation.createNotStarted("getDevice", registry).observe(() -> Utility
                .mapToDeviceDTO(repository.findById(uuid).orElseThrow(() -> Utility.notFound(uuid))));
    }

    public DeviceDTO save(DeviceDTO request) {
        System.out.println("Request:  " + request);
        Group group = null;
        if (request.getGroup_uuid() != null) {
            group = groupRepository.findById(request.getGroup_uuid()).orElseThrow(() -> Utility.notFound(request.getGroup_uuid()));
        }

        String name = request.getName();
        if (name == null || name.trim().isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            TimeZone danishTimeZone = TimeZone.getTimeZone("Europe/Copenhagen");
            formatter.setTimeZone(danishTimeZone);
            name = "New Device " + formatter.format(new Date());
        }

        Device device = Device.builder()
                .uuid(request.getUuid())
                .name(name)
                .online(true)
                .athena_version(request.getAthena_version())
                .toit_firmware_version(request.getToit_firmware_version())
                .ip_address(request.getIp_address())
                .jaguar_port(request.getJaguar_port())
                .group(group)
                .last_ping(new Date())
                .date_created(new Date())
                .build();

        if (!repository.existsById(device.getUuid())) {
            // Create device in database if not exists
            return Observation.createNotStarted("saveDevice", registry)
                    .observe(() -> Utility.mapToDeviceDTO(repository.save(device)));
        } else {
            // If device already exists update lastPing
            return this.updateLastPing(request);
        }
    }

    public DeviceDTO updateGroup(UUID uuid, SetGroupRequest group_uuid) {
        Device device = repository.findById(uuid).orElseThrow(() -> Utility.notFound(uuid));
        Group group = groupRepository.findById(group_uuid.getGroupUUID()).orElseThrow(() -> Utility.notFound(group_uuid.getGroupUUID()));
        device.setGroup(group);

        return Observation.createNotStarted("updateDeviceGroup", registry)
                .observe(() -> Utility.mapToDeviceDTO(repository.save(device)));
    }

    public String delete(UUID uuid) {
        Device device = repository.findById(uuid).orElseThrow(() -> Utility.notFound(uuid));
        repository.delete(device);
        return "Device with id=" + uuid + " removed";
    }

    public String deleteAll() {
        List<Device> devices = repository.findAll();
        if (devices.isEmpty())
            return "No devices available";
        repository.deleteAll();
        return "All devices are removed.";
    }

    public DeviceDTO update(DeviceDTO request) {
        Device device = repository.findById(request.getUuid()).orElseThrow(() -> Utility.notFound(request.getUuid()));
        if (request.getGroup_uuid() != null) {
            Group group = groupRepository.findById(request.getGroup_uuid()).orElseThrow(() -> Utility.notFound(request.getGroup_uuid()));
            device.setGroup(group);
        }
        device.setName(request.getName());
        device.setAthena_version(request.getAthena_version());
        device.setToit_firmware_version(request.getToit_firmware_version());
        device.setIp_address(request.getIp_address());

        return Observation.createNotStarted("updateDevice", registry)
                .observe(() -> Utility.mapToDeviceDTO(repository.save(device)));
    }

    public DeviceDTO updateLastPing(DeviceDTO request) {
        Device device = repository.findById(request.getUuid()).orElseThrow(() -> Utility.notFound(request.getUuid()));

        device.setOnline(true);
        device.setLast_ping(new Date());
        device.setToit_firmware_version(request.getToit_firmware_version());
        device.setIp_address(request.getIp_address());

        return Observation.createNotStarted("updateDevice", registry)
                .observe(() -> Utility.mapToDeviceDTO(repository.save(device)));
    }
}
