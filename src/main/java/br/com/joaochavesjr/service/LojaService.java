package br.com.joaochavesjr.service;

import br.com.joaochavesjr.model.Loja;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de loja. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author falvojr
 */
public interface LojaService {

	Iterable<Loja> buscarTodos();

	Loja buscarPorId(Long id);

	void inserir(Loja loja);

	void atualizar(Long id, Loja loja);

	void deletar(Long id);

}
