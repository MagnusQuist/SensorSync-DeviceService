package dk.sdu.deviceservice.repository;

import dk.sdu.deviceservice.Entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDeviceRepository extends JpaRepository<Device, UUID> {
}
