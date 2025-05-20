package academia.boaForma.usuarios.models;

import academia.boaForma.alunos.models.informacoes.AlunosModel;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuariosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_usuario;

    @Column(nullable = false)
    private LocalDate criado_em;

    @Column(nullable = false)
    private Boolean usuario_ativo;

    @Column(nullable = false)
    private Boolean acesso_sistema;

    @Enumerated(EnumType.STRING)
    private TipoUsuarioEnum tipoUsuario;

    // Para conexão com a tabela alunos

    @OneToMany(mappedBy = "id_usuario", cascade = CascadeType.ALL)
    private List<AlunosModel> usuario_id;

    @OneToMany(mappedBy = "acesso_sistema", cascade = CascadeType.ALL)
    private List<AlunosModel> prmissao_sistema;
}
