package academia.boaForma.exception;

import java.util.List;

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