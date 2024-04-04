package dk.sdu.deviceservice.service;

import dk.sdu.deviceservice.Entity.Device;
import dk.sdu.deviceservice.repository.IDeviceRepository;
import dk.sdu.deviceservice.request.DeviceRequest;
import dk.sdu.deviceservice.response.DeviceDTO;
import dk.sdu.deviceservice.util.Utility;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private IDeviceRepository repository;

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

    public DeviceDTO save(DeviceRequest request) {
        Device device = Device.builder()
                .name(request.getName())
                .athena_version(request.getAthena_version())
                .toit_firmware_version(request.getToit_firmware_version())
                .date_created(new Date())
                .build();
        return Observation.createNotStarted("saveDevice", registry)
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

    public DeviceDTO update(DeviceRequest request) {
        Device device = repository.findById(request.getUuid()).orElseThrow(() -> Utility.notFound(request.getUuid()));

        device.setUuid(request.getUuid());
        device.setName(request.getName());
        device.setAthena_version(request.getAthena_version());
        device.setToit_firmware_version(request.getToit_firmware_version());

        return Observation.createNotStarted("updateDevice", registry)
                .observe(() -> Utility.mapToDeviceDTO(repository.save(device)));
    }
}
