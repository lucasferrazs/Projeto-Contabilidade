package com.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.*;

@Repository
public interface LivrorazaoRepository extends JpaRepository<Lancamentos,Integer> {
    List<Lancamentos> findBydescricao(String descricao);
   // List<Lancamentos> findByContadebitoandDatacLancamentosBetween(LocalDate inicio, LocalDate fim);
    /*
     filtrar os planos de contas por um determinado periodo 
     */

   // List<Lancamentos> findByContadebitoAndCdataBetween(PlanoContas contadebito, LocalDate startDate, LocalDate endDate);

     // Supondo que a entidade PlanoContas tenha um campo 'codigo' do tipo String
    //List<Lancamentos> findByContadebito_CodigoAndCdataBetween(String codigoConta, LocalDate startDate, LocalDate endDate);

   /*   List<Lancamentos> findByCdataBetweenAndContadebitoOrContacredito(
       LocalDate dataInicio,
        LocalDate dataFim ,  
        PlanoContas contaDebito,
        PlanoContas contaCredito
      
    );
*/

     @Query("SELECT l FROM Lancamentos l " +
           "WHERE l.cdata BETWEEN :dataInicio AND :dataFim " +
           "AND (l.contadebito = :conta OR l.contacredito = :conta)")
    List<Lancamentos> buscarExtratoCompleto(
        @Param("conta") PlanoContas conta,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
}
