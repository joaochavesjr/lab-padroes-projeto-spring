package br.com.joaochavesjr.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaochavesjr.model.Loja;
import br.com.joaochavesjr.model.LojaRepository;
import br.com.joaochavesjr.model.Endereco;
import br.com.joaochavesjr.model.EnderecoRepository;
import br.com.joaochavesjr.service.LojaService;
import br.com.joaochavesjr.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link LojaService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class LojaServiceImpl implements LojaService {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private LojaRepository lojaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	@Override
	public Iterable<Loja> buscarTodos() {
		// Buscar todas as Lojas.
		return lojaRepository.findAll();
	}

	@Override
	public Loja buscarPorId(Long id) {
		// Buscar Loja por ID.
		Optional<Loja> loja = lojaRepository.findById(id);
		return loja.get();
	}

	@Override
	public void inserir(Loja loja) {
		salvarLojaComCep(loja);
	}

	@Override
	public void atualizar(Long id, Loja loja) {
		// Buscar Loja por ID, caso exista:
		Optional<Loja> lojaBd = lojaRepository.findById(id);
		if (lojaBd.isPresent()) {
			salvarLojaComCep(loja);
		}
	}

	@Override
	public void deletar(Long id) {
		// Deletar Loja por ID.
		lojaRepository.deleteById(id);
	}

	private void salvarLojaComCep(Loja loja) {
		// Verificar se o Endereco da Loja já existe (pelo CEP).
		String cep = loja.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		loja.setEndereco(endereco);
		// Inserir Loja, vinculando o Endereco (novo ou existente).
		lojaRepository.save(loja);
	}

}
