package academia.boaForma.alunos.models.endereco;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String logradouro; // rua, avenida, etc.
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    protected Endereco() {}

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
    }

    // GET

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    // Verificar se é melhor fazer em outro arquivo isso.

    public Endereco comNovoLogradouro (String novoLogradouro) {
        return new Endereco(
            novoLogradouro, this.numero, this.complemento, this.bairro, this.cep
        );
    }

    public Endereco comNovoNumero (String novoNumero) {
        return new Endereco(
            this.logradouro, novoNumero, this.complemento, this.bairro, this.cep
        );
    }
}
