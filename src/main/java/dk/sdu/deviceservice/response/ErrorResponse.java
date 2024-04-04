package dk.sdu.deviceservice.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private Integer errorCode;
}
