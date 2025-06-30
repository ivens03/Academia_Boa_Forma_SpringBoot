package academia.boaForma.gestor.models;

import academia.boaForma.gestor.dtos.DadosCadastroGestor;
import academia.boaForma.usuarios.models.UsuarioModel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

@Entity
@Table(name = "gestor")
@DiscriminatorValue("GESTOR")
public class GestorModel extends UsuarioModel {

    public GestorModel() {}

    public GestorModel(@Valid DadosCadastroGestor dadosCadastroGestor) {
        this.id = dadosCadastroGestor.id();
        this.nome = dadosCadastroGestor.nome();
        this.cpf = dadosCadastroGestor.cpf();
        this.telefone = dadosCadastroGestor.telefone();
        this.email = dadosCadastroGestor.email();
        this.senha = dadosCadastroGestor.senha();
        this.genero = dadosCadastroGestor.genero();
        this.dataNascimento = dadosCadastroGestor.dataNascimento();
        this.criadoEm = dadosCadastroGestor.criadoEm();
        this.ativo = dadosCadastroGestor.ativo();
        this.acessoSistema = dadosCadastroGestor.acessoSistema();
    }


}
