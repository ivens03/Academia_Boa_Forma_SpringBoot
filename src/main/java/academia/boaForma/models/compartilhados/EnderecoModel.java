package academia.boaForma.models.compartilhados;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnderecoModel {

    private String bairro;
    private String cep;
    private String numero;
    private String complemento;

    //CONSTRUTOR

    public EnderecoModel() {}

    public EnderecoModel(String bairro, String cep, String numero, String complemento) {
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }

    //GETTERS E SETTERS

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
