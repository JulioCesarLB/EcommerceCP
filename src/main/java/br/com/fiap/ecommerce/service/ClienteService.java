package br.com.fiap.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.ecommerce.model.Cliente;
import br.com.fiap.ecommerce.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	 public List<Cliente> list() {
	        return repository.findAll();
	    }

	    public Cliente save(Cliente cliente) {        
	        return repository.save(cliente);
	    }

	    public boolean existsById(Long id) {        
	        return repository.existsById(id);
	    }

	    public void delete(Long id) {
	    	repository.deleteById(id);
	    }

	    public Optional<Cliente> findById(Long id) {
	        return repository.findById(id);
	    }
}
