package dk.sdu.deviceservice.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private UUID uuid;
    private String name;
    private boolean online;
    private String athena_version;
    private String toit_firmware_version;
    private String ip_address;
    private int jaguar_port;
    private UUID group_uuid;
    private Date last_ping;
    private Date date_created;
}
