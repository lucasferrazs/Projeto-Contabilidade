package com.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Lancamentos;

public interface LivrodiarioRepository extends JpaRepository<Lancamentos,Integer>{
   List<Lancamentos> findByCdata(LocalDate cdata);
   List<Lancamentos> findByCdataEquals(LocalDate cdata);
}
