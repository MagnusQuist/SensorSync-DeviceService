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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;
    @Column(name = "name")
    private String name;
    @Column(name = "athena_version")
    private String athena_version;
    @Column(name = "toit_firmware_version")
    private String toit_firmware_version;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date date_created;
}
