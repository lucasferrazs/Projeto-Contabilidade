package com.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Lancamentos;
import com.model.PlanoContas;
import com.repository.LancamentosRepository;
import com.repository.PlanoContasRepository;

import jakarta.transaction.Transactional;

@Service
public class LancamentoService {
    
   
    private LancamentosRepository lancamentosRepository;
    private PlanoContasRepository planoContasRepository; 
    @Autowired
    public LancamentoService(LancamentosRepository repository){
        this.lancamentosRepository = repository;

    }
    // Garante que a ação seja atómica
    @Transactional

    public Lancamentos criarNovoLancamento(String descricao,LocalDate cdata ,BigDecimal valor, PlanoContas contaDebito,PlanoContas contaCredito  ){

            // --- LÓGICA DE NEGÓCIO ---
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do lançamento deve ser positivo.");
        }
        if (contaDebito.getId().equals(contaCredito.getId())) {
            throw new IllegalArgumentException("A conta de débito não pode ser igual à de crédito.");
        }
        // ... outras validações ...

        // preparando a entidade
        Lancamentos novoLancamento = new Lancamentos();

        novoLancamento.setDescricao(descricao);
        novoLancamento.setCdata(cdata);
        novoLancamento.setValor(valor);
        novoLancamento.setContadebito(contaDebito);
        novoLancamento.setContacredito(contaCredito);

        // chamando o metodo herdado de jpaRepository
        return lancamentosRepository.save(novoLancamento);
    }
/*
 * @param conta
 * 
 */
     
   public List<PlanoContas> listarContas(){
        return planoContasRepository.findAll();
   }

    
}
