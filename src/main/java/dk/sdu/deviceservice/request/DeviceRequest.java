package dk.sdu.deviceservice.request;

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
public class DeviceRequest {
    private UUID uuid;
    private String name;
    private String athena_version;
    private String toit_firmware_version;
}

