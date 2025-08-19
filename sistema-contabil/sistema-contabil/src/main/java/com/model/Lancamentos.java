package com.model;
// Importações dos tipos que a entidade vai usar

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentocontabeis")
@Data
public class Lancamentos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idlancamento")
    private Integer id;

    @Column(name="descricao")
    private String descricao;

    @Column(name="cdata")
    private LocalDate cdata;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    // --- MAPEAMENTO DA CHAVE ESTRANGEIRA (Débito) ---

    @ManyToOne // Muitos lançamentos para UMA conta
    @JoinColumn(name = "contadebito") // Nome da coluna FK na tabela 'lancamentocontabeis'
    private PlanoContas contadebito;


    // --- MAPEAMENTO DA CHAVE ESTRANGEIRA (Crédito) ---

    @ManyToOne // Muitos lançamentos para UMA conta
    @JoinColumn(name = "contacredito") // Nome da coluna FK na tabela 'lancamentocontabeis'
    private PlanoContas contacredito;


    /*
O que o Código Acima Faz?
private PlanoContas contaDebito;: Em vez de private Integer contaDebito;,
agora temos um objeto PlanoContas completo. Isso é muito mais poderoso, 
pois você pode acessar diretamente os dados da conta debitada a partir de um objeto de lançamento 
(ex: meuLancamento.getContaDebito().getDescricao()).

@ManyToOne: Informa ao JPA sobre o tipo de relação.
@JoinColumn(name = "contaDebito"): Diz ao JPA: "Na tabela lancamentocontabeis, a coluna que guarda 
o ID da PlanoContas de débito se chama contaDebito". O JPA usará essa coluna para fazer o JOIN no 
banco de dados quando você precisar carregar os dados.
O mesmo processo é repetido para contaCredito.
     */
}