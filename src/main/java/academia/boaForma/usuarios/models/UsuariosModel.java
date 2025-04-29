package academia.boaForma.usuarios.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class UsuariosModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private LocalDate criado_em;

    @Column(nullable = false)
    private Boolean usuario_ativo;

    @Column(nullable = false)
    private Boolean acesso_sistema;

    @Embedded
    private TipoUsuarioEnum tipoUsuario;
}
