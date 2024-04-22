package dk.sdu.deviceservice.repository;

import dk.sdu.deviceservice.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IGroupRepository extends JpaRepository<Group, UUID> {

}
