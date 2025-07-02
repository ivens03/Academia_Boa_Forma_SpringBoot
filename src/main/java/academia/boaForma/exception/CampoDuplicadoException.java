package academia.boaForma.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.CONFLICT)
public class CampoDuplicadoException extends RuntimeException {

    private final List<String> campo;

    public CampoDuplicadoException(List<String> campo) {
        super("Dados já existente: " + String.join(", ", campo));
        this.campo = campo;
    }

    public List<String> getCampo() {
        return campo;
    }

}