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

import br.com.fiap.ecommerce.dtos.PedidoRequestCreateDto;
import br.com.fiap.ecommerce.dtos.PedidoRequestUpdateDto;
import br.com.fiap.ecommerce.dtos.PedidoResponseDto;
import br.com.fiap.ecommerce.service.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> list() {
    	 List<PedidoResponseDto> dtos = service.list()
    	            .stream()
    	            .map(e -> new PedidoResponseDto().toDto(e))
    	            .toList();
    	        
    	        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> create(@RequestBody PedidoRequestCreateDto dto) {        
    	return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(
        			new PedidoResponseDto().toDto(
        					service.save(dto.toModel()))
        			);
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoResponseDto> update(
                        @PathVariable Long id, 
                        @RequestBody PedidoRequestUpdateDto dto) {
    	 if (! service.existsById(id)){
             throw new RuntimeException("Id inexistente");
         }                
         return ResponseEntity.ok()
         		.body(
         			new PedidoResponseDto().toDto(
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
    public ResponseEntity<PedidoResponseDto> findById(@PathVariable Long id) {    	
    	return ResponseEntity.ok()
    			.body(
    					service
    					.findById(id)
    					.map(e -> new PedidoResponseDto().toDto(e))
    					.orElseThrow(() -> new RuntimeException("Id inexistente"))
    			);
    	  		    
    	  		     
    }

}
