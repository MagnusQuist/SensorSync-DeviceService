package dk.sdu.deviceservice.controller;

import dk.sdu.deviceservice.response.GroupDTO;
import dk.sdu.deviceservice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class GroupController {
    @Autowired
    GroupService groupService;

    // GET ALL
    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public List<GroupDTO> getGroups() { return groupService.groups(); }

    // GET
    @GetMapping("/groups/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDTO getGroup(@PathVariable UUID uuid) {
        return groupService.group(uuid);
    }

    // CREATE
    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO save(@RequestBody GroupDTO group) {
        return groupService.save(group);
    }

    // UPDATE
    @PutMapping("/groups/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public GroupDTO update(@RequestBody GroupDTO group) {
        return groupService.update(group);
    }

    // DELETE
    @DeleteMapping("/groups/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable UUID uuid) {
        return groupService.delete(uuid);
    }

    // DELETE ALL
    @DeleteMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAll() {
        return groupService.deleteAll();
    }
}
