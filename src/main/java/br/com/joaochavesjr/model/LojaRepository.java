package br.com.joaochavesjr.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LojaRepository extends CrudRepository<Loja, Long> {

}