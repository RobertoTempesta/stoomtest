package com.roberto.stoomtest.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.roberto.stoomtest.entities.Address;
import com.roberto.stoomtest.services.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/address")
public class AddressController {

    private static final String URL_ID = "/{id}";

    @Autowired
    private AddressService service;
    
    @GetMapping
    public ResponseEntity<Page<Address>> findAll(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = URL_ID)
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Address> insert(@RequestBody @Valid Address address) {
        Address entity = service.insert(address);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(URL_ID)
            .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @PutMapping(value = URL_ID)
    public ResponseEntity<Address> update(
            @PathVariable Long id, 
            @RequestBody @Valid Address address
        ) {        
        return ResponseEntity.ok().body(service.update(id, address));
    }

    @DeleteMapping(value = URL_ID)
    public ResponseEntity<Address> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
