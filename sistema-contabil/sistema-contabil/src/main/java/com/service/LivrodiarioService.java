package com.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Lancamentos;
import com.repository.LivrodiarioRepository;


@Service
public class LivrodiarioService {
    private LivrodiarioRepository livrodiarioRepository;

    @Autowired
    public LivrodiarioService(LivrodiarioRepository repository){
        this.livrodiarioRepository = repository;
    }

     public List<Lancamentos> filtrarLancamentos(){

        LocalDate hoje = LocalDate.now();

        return livrodiarioRepository.findByCdata(hoje);
     }



     public List<Lancamentos> buscarPorData(LocalDate cdata){

        return livrodiarioRepository.findByCdataEquals(cdata);

     }

     

}
