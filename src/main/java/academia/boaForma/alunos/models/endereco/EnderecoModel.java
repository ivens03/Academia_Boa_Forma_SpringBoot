package academia.boaForma.alunos.models.endereco;

import academia.boaForma.alunos.dtos.DadosEnderecoDTO;
import jakarta.persistence.Embeddable;

@Embeddable
public class EnderecoModel {

    private String bairro;
    private String cep;
    private String numero_residencia;
    private String complemento;

    //CONSTRUTOR

    public EnderecoModel() {}

    public EnderecoModel(DadosEnderecoDTO endereco) {
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.numero_residencia = endereco.numero_residencia();
        this.complemento = endereco.complemento();
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

    public String getNumero_residencia() {
        return numero_residencia;
    }

    public void setNumero_residencia(String numero_residencia) {
        this.numero_residencia = numero_residencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
