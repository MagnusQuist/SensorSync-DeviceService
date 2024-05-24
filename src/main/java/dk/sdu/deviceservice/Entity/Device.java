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
    private String name = "New Device";
    @Column(name = "online")
    private boolean online = false;
    @Column(name = "athena_version")
    private String athena_version = "Unknown";
    @Column(name = "toit_firmware_version")
    private String toit_firmware_version = "Unknown";
    @Column(name = "ip_address")
    private String ip_address = "Unknown";
    @Column(name = "jaguar_port")
    private int jaguar_port = 9000;
    @ManyToOne
    @JoinColumn(name = "group_uuid")
    private Group group = null;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_ping")
    private Date last_ping;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date date_created;
}
