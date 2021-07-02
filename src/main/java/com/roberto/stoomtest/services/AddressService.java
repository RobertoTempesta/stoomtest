package com.roberto.stoomtest.services;

import java.io.IOException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.roberto.stoomtest.entities.Address;
import com.roberto.stoomtest.repositories.AddressRepository;
import com.roberto.stoomtest.services.exceptions.EntityNotFound;
import com.roberto.stoomtest.services.exceptions.GeoAPIException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private static final String NOT_FOUND_ID = "Id not found: ";

    @Autowired
    private AddressRepository repository;

    @Autowired
    private GeoApiContext geoApiContext;

    @Transactional(readOnly = true)
    public Page<Address> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
        Optional<Address> entity = repository.findById(id);
        entity.orElseThrow(() -> new EntityNotFound("Entity not found!"));
        return entity.get();
    }

    @Transactional
    public Address insert(Address address) {
        getCoordinates(address);
        return repository.save(address);
    }

    @Transactional
    public Address update(Long id, Address address) {
        try {
            getCoordinates(address);
            Address entity = repository.getOne(id);
            entity.setStreetName(address.getStreetName());
            entity.setNumber(address.getNumber());
            entity.setComplement(address.getComplement());
            entity.setNeighbourhood(address.getNeighbourhood());
            entity.setCity(address.getCity());
            entity.setState(address.getState());
            entity.setCountry(address.getCountry());
            entity.setZipcode(address.getZipcode());
            entity.setLatitude(address.getLatitude());
            entity.setLongitude(address.getLongitude());
            entity = repository.save(entity);
            return entity;
        } catch (EntityNotFoundException err) {
            throw new EntityNotFound(NOT_FOUND_ID + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException err) {
            throw new EntityNotFound(NOT_FOUND_ID + id);
        }
    }

	public void getCoordinates(Address address) {

        if ((address.getLatitude() == null || address.getLatitude().isEmpty()) ||
                (address.getLongitude() == null || address.getLongitude().isEmpty())) {

            StringBuilder builder = new StringBuilder();
            builder
                .append(address.getStreetName() + " ")
                .append(address.getNumber() + " ")
                .append(address.getNeighbourhood() + " ")
                .append(address.getCity() + " ")
                .append(address.getState() + " ")
                .append(address.getCountry() + " ")
                .append(address.getZipcode());

            try {
                GeocodingResult[] results = GeocodingApi
                    .geocode(
                        geoApiContext,
                        builder.toString()
                    )
                    .await();

                if (results.length > 0) {
                    address.setLatitude(String.valueOf(results[0].geometry.location.lat));
                    address.setLongitude(String.valueOf(results[0].geometry.location.lng));
                }
                
            } catch (ApiException | InterruptedException | IOException | IllegalStateException ex) {
                throw new GeoAPIException(ex.getMessage());
            }
	    }   
    }
    
}
