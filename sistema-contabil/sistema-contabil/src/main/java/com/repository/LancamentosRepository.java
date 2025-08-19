package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Lancamentos;
import com.model.PlanoContas;


@Repository
public interface LancamentosRepository extends JpaRepository<Lancamentos,Integer>{


    List<Lancamentos> findByContacredito(PlanoContas contaCredito);
    List<Lancamentos> findByContadebito(PlanoContas contaCredito);
    
    // Para buscar pelo ID da conta de crédito (um número).
    List<Lancamentos> findByContacreditoId(Integer idConta);
    List<Lancamentos> findByContadebitoId(Integer idConta);


} 