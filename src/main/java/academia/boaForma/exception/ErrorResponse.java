package academia.boaForma.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDetail> fieldErros
){

    public ErrorResponse(int status, String error, String message, String path, List<FieldErrorDetail> fieldErros) {
        this(LocalDateTime.now().toString(), status, error, message, path, fieldErros);
    }

}
