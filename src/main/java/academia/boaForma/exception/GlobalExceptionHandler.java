package academia.boaForma.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        String mensagem = "Ocorreu um erro no servidor";
        if (ex instanceof DataIntegrityViolationException) {
            // Captura violações de constraint do banco de dados (como duplicidade)
            mensagem = "Erro de integridade de dados. Verifique se há informações duplicadas.";
            body.put("status", HttpStatus.BAD_REQUEST.value());
        }

        body.put("message", mensagem);
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.valueOf((Integer) body.get("status")));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode().value());
        body.put("message", ex.getReason());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
