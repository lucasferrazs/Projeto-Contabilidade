package com.model;

// Importações dos tipos que a entidade vai usar
import com.model.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * **ESTA É A PEÇA CENTRAL.**
 * A classe que define a estrutura dos dados do "Plano de Contas" dentro da aplicação.
 * Cada anotação aqui garante que o Java e o banco de dados "falem a mesma língua".
 *
 * - @Entity: Marca esta classe como o molde para uma tabela.
 * - @Table(name = "planocontas"): Conecta esta classe diretamente à sua tabela `planocontas`.
 * - @Column(name = "..."): Mapeia cada atributo Java para a sua coluna exata no banco.
 *   Isso nos dá a liberdade de usar nomes mais limpos em Java (como 'descricao')
 *   enquanto ainda respeitamos a estrutura do banco ('descricaoCodigo').
 */
@Entity
@Table(name = "planocontas")
@Data
public class PlanoContas {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura o auto-incremento.
    @Column(name = "idcontas")
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    @Column(name = "descricaocodigo", nullable = false, length = 255)
    private String descricao;

    // Para os campos ENUM, a anotação @Enumerated(EnumType.STRING) é crucial.
    // Ela diz ao JPA para gravar o texto ('ativo', 'circulante') no banco,
    // o que previne os erros que tivemos antes.
    @Enumerated(EnumType.STRING)
    @Column(name = "grupo")
    private GrupoEnum grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "subgrupo")
    private SubgrupoEnum subgrupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "natureza")
    private NaturezaEnum natureza;

    @Column(name = "saldo", precision = 10, scale = 2)
    private BigDecimal saldo;

    public PlanoContas getContadebito() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getContadebito'");
    }
}
