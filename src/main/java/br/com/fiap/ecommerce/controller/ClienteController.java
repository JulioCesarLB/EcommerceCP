package br.com.fiap.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.ecommerce.dtos.ClienteRequestCreateDto;
import br.com.fiap.ecommerce.dtos.ClienteRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.ClienteResponseDto;
import br.com.fiap.ecommerce.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> list() {
    	List<ClienteResponseDto> dtos = service.list()
                .stream()
                .map(e -> new ClienteResponseDto().toDto(e))
                .toList();
            
            return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> create(@RequestBody ClienteRequestCreateDto dto) {        
    	 return ResponseEntity
         		.status(HttpStatus.CREATED)
         		.body(
         			new ClienteResponseDto().toDto(
         					service.save(dto.toModel()))
         			);
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteResponseDto> update(
                        @PathVariable Long id, 
                        @RequestBody ClienteRequestUpdateDto dto) {
    	  if (! service.existsById(id)){
              throw new RuntimeException("Id inexistente");
          }                
          return ResponseEntity.ok()
          		.body(
          			new ClienteResponseDto().toDto(
          					service.save(dto.toModel(id)))
          		);
    }
    
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
    	if (! service.existsById(id)){
        	throw new RuntimeException("Id inexistente");
        }

    	service.delete(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDto> findById(@PathVariable Long id) {    	
    	return ResponseEntity.ok()
    			.body(
    					service
    					.findById(id)
    					.map(e -> new ClienteResponseDto().toDto(e))
    					.orElseThrow(() -> new RuntimeException("Id inexistente"))
    			);
	  		     
    }
	
}
