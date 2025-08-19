package com.service;

import com.model.PlanoContas;
import com.model.enums.GrupoEnum;
import com.model.enums.NaturezaEnum;
import com.model.enums.SubgrupoEnum;
import com.repository.PlanoContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Camada de serviço para as operações relacionadas ao Plano de Contas.
 * @Service: Marca como um componente de serviço do Spring.
 * A lógica de negócio (como buscar e combinar resultados) fica aqui,
 * mantendo o controller limpo.
 */
@Service
public class PlanoContasService {

    private final PlanoContasRepository repository;

    @Autowired
    public PlanoContasService(PlanoContasRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna uma lista com todos os planos de contas.
     */
    public List<PlanoContas> listarTodos() {
        return repository.findAll();
    }

    /**
     * Busca contas por um termo de pesquisa, procurando tanto no código quanto na descrição.
     * @param termo O texto a ser procurado.
     * @return Uma lista de contas que correspondem ao termo, sem duplicatas.
     */
    public List<PlanoContas> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }

        // Buscar campos
        List<PlanoContas> porCodigo = repository.findByCodigoContainingIgnoreCase(termo);
        List<PlanoContas> porDescricao = repository.findByDescricaoContainingIgnoreCase(termo);
        List<PlanoContas> porSaldo = new ArrayList<>();
        
          // --- NOVA LÓGICA PARA BUSCA POR GRUPO (ENUM) ---
        List<PlanoContas> porGrupo = new ArrayList<>();
        try {
            // Converte o texto para o Enum correspondente (ignorando maiúsculas/minúsculas)
            GrupoEnum grupoPesquisa = GrupoEnum.valueOf(termo.toUpperCase());
            porGrupo = repository.findByGrupo(grupoPesquisa);
        } catch (IllegalArgumentException e) {
            // Se o texto não corresponder a nenhum valor do Enum (ex: "teste"), ignora a busca.
        }
        // -------------------------------------------------

        
          // --- NOVA LÓGICA PARA BUSCA POR SUBGRUPO(ENUM) ---
        List<PlanoContas> porSubgrupo = new ArrayList<>();
        try {
            // Converte o texto para o Enum correspondente (ignorando maiúsculas/minúsculas)
            SubgrupoEnum grupoPesquisa = SubgrupoEnum.valueOf(termo.toUpperCase());
            porGrupo = repository.findBySubgrupo(grupoPesquisa);
        } catch (IllegalArgumentException e) {
            // Se o texto não corresponder a nenhum valor do Enum (ex: "teste"), ignora a busca.
        }
        // -------------------------------------------------
        
          // --- NOVA LÓGICA PARA BUSCA POR Natureza(ENUM) ---
        List<PlanoContas> porNatureza = new ArrayList<>();
        try {
            // Converte o texto para o Enum correspondente (ignorando maiúsculas/minúsculas)
            NaturezaEnum grupoPesquisa = NaturezaEnum.valueOf(termo.toUpperCase());
            porGrupo = repository.findByNatureza(grupoPesquisa);
        } catch (IllegalArgumentException e) {
            // Se o texto não corresponder a nenhum valor do Enum (ex: "teste"), ignora a busca.
        }
        // -------------------------------------------------

        // Tenta converter o termo para Double.
        try {
            Double saldoPesquisa = Double.valueOf(termo.replace(",", ".")); // Trata vírgula e ponto
            porSaldo = repository.findBySaldo(saldoPesquisa);
        } catch (NumberFormatException e) {
            // Se o termo não for um número válido (ex: "Aluguel"), ignora a busca por saldo.
            // O bloco catch pode ficar vazio, pois a falha é esperada.
        }
        
        // Une as duas listas e remove duplicatas usando a API de Streams do Java.
        // É uma forma moderna e eficiente de combinar os resultados.
    // Une as três listas e remove duplicatas usando a API de Streams do Java.
// Esta abordagem é moderna, eficiente e facilmente escalável para mais listas.
return Stream.of(porCodigo, porDescricao, porSaldo,porGrupo,porSubgrupo,porNatureza) // Cria um stream de listas
             .flatMap(Collection::stream)          // Achata o stream de listas em um único stream de PlanoContas
             .distinct()                           // Garante que cada conta apareça apenas uma vez
             .collect(Collectors.toList());        // Coleta o resultado em uma nova lista
    }
}
