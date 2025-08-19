package com.model.enums;

/**
 * Representa a coluna 'grupo' da tabela 'planocontas'.
 * Os nomes das constantes (ativo, passivo, etc.) são idênticos
 * aos valores de texto no banco de dados, o que facilita o mapeamento direto pelo JPA.
 */
public enum GrupoEnum {
    ativo,
    passivo,
    patrimonio,
    receita,
    despesa
}
