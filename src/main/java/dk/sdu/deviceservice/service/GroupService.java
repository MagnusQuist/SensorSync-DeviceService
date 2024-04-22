package dk.sdu.deviceservice.service;

import dk.sdu.deviceservice.Entity.Group;
import dk.sdu.deviceservice.repository.IGroupRepository;
import dk.sdu.deviceservice.response.GroupDTO;
import dk.sdu.deviceservice.util.Utility;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {
    @Autowired
    private IGroupRepository repository;

    @Autowired
    private ObservationRegistry registry;

    public List<GroupDTO> groups() {
        return Observation.createNotStarted("getGroups", registry)
                .observe(() -> repository.findAll().stream().map(Utility::mapToGroupDTO).toList());
    }

    public GroupDTO group(UUID uuid) {
        return Observation.createNotStarted("getGroup", registry).observe(() -> Utility
                .mapToGroupDTO(repository.findById(uuid).orElseThrow(() -> Utility.notFound(uuid))));
    }

    public GroupDTO save(GroupDTO request) {
        Group group = Group.builder()
                .uuid(request.getUuid())
                .name(request.getName())
                .location(request.getLocation())
                .build();

        return Observation.createNotStarted("saveGroup", registry)
                    .observe(() -> Utility.mapToGroupDTO(repository.save(group)));
    }

    public String delete(UUID uuid) {
        Group group = repository.findById(uuid).orElseThrow(() -> Utility.notFound(uuid));
        repository.delete(group);
        return "Group with id=" + uuid + " removed";
    }

    public String deleteAll() {
        List<Group> groups = repository.findAll();
        if (groups.isEmpty())
            return "No groups available";
        repository.deleteAll();
        return "All groups are removed.";
    }

    public GroupDTO update(GroupDTO request) {
        Group group = repository.findById(request.getUuid()).orElseThrow(() -> Utility.notFound(request.getUuid()));

        group.setName(request.getName());
        group.setLocation(request.getLocation());

        return Observation.createNotStarted("updateGroup", registry)
                .observe(() -> Utility.mapToGroupDTO(repository.save(group)));
    }
}
