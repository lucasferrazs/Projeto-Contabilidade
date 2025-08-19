package com.repository;

import com.model.PlanoContas;
import com.model.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório para a entidade PlanoContas.
 * Estender JpaRepository<PlanoContas, Integer> nos dá métodos CRUD prontos.
 * O Spring cria as consultas SQL automaticamente a partir dos nomes dos métodos.
 * - findByCodigo... usa o atributo 'codigo'.
 * - findByDescricao... usa o atributo 'descricao' (que mapeia para 'descricaoCodigo').
 */
@Repository
public interface PlanoContasRepository extends JpaRepository<PlanoContas, Integer> {

    // Encontra contas cujo código contenha o texto pesquisado, sem diferenciar maiúsculas/minúsculas.
    List<PlanoContas> findByCodigoContainingIgnoreCase(String codigo);

    // Encontra contas cuja descrição contenha o texto pesquisado, sem diferenciar maiúsculas/minúsculas.
    List<PlanoContas> findByDescricaoContainingIgnoreCase(String descricao);
    List<PlanoContas>findBySaldo(Double saldo);
    List<PlanoContas>findByGrupo(GrupoEnum grupo);
    List<PlanoContas>findBySubgrupo(SubgrupoEnum subgrupo);
    List<PlanoContas>findByNatureza(NaturezaEnum natureza);
}
