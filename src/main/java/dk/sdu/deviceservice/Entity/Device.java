package dk.sdu.deviceservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "name")
    private String name;
    @Column(name = "online")
    private boolean online;
    @Column(name = "athena_version")
    private String athena_version;
    @Column(name = "toit_firmware_version")
    private String toit_firmware_version;
    @Column(name = "device_group")
    private String device_group;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_ping")
    private Date last_ping;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date date_created;
}
