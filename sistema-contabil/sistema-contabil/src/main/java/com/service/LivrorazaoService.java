package com.service;


import com.model.Lancamentos;
import com.model.PlanoContas;
import com.repository.LivrorazaoRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LivrorazaoService {
  
private LivrorazaoRepository livrorazaoRepository;



@Autowired
public LivrorazaoService(LivrorazaoRepository repository){
        this.livrorazaoRepository = repository;
   }


   /*public List<Lancamentos> buscarPorData( LocalDate dataInicio, LocalDate dataFim, PlanoContas contadebito, PlanoContas contacredito){

        return livrorazaoRepository.findByCdataBetweenAndContadebitoOrContacredito(dataInicio,dataFim,contadebito,contacredito);

     }

*/

public List<Lancamentos> buscarExtratoDaContaPorPeriodo(PlanoContas conta, LocalDate dataInicio, LocalDate dataFim) {
    if (conta == null || dataInicio == null || dataFim == null) {
        return Collections.emptyList();
    }

    // Chama o novo método, mais robusto, do repositório
    return livrorazaoRepository.buscarExtratoCompleto(conta, dataInicio, dataFim);
}
   }