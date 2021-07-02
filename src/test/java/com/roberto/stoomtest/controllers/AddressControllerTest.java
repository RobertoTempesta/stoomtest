package com.roberto.stoomtest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberto.stoomtest.entities.Address;
import com.roberto.stoomtest.services.AddressService;
import com.roberto.stoomtest.services.exceptions.EntityNotFound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Address address;

    @BeforeEach
    void setUp() throws Exception {
        address = new Address(
            1L,
            "Av. Pedro Álvares Cabral", 
            123, 
            null, 
            "Vila Mariana", 
            "São Paulo", 
            "SP", 
            "Brasil", 
            "04094-050", 
            "-23.583387", 
            "-46.6813977,12.56"
        );

        PageImpl<Address> page = new PageImpl<>(List.of(address));
        
        existingId = 1L;
        nonExistingId = 2L;

        when(addressService.findAll(any())).thenReturn(page);
        when(addressService.findById(existingId)).thenReturn(address);
        when(addressService.findById(nonExistingId)).thenThrow(EntityNotFound.class);

        when(addressService.insert(any())).thenReturn(address);
        when(addressService.update(eq(existingId), any())).thenReturn(address);

        doNothing().when(addressService).delete(existingId);
        //doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        //doThrow(DataIntegrityViolationException.class).when(service).delete(dependentId);
    }

    @Test
    public void deleteShouldReturnNoContentWhenExistingId() throws Exception {
        ResultActions result = mockMvc.perform(delete("/address/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));
        
        result.andExpect(status().isNoContent());
    }

    @Test
    public void updateShouldReturnProductDTOWhenExistinfId() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(address);

        ResultActions result = mockMvc.perform(put("/address/{id}", existingId)
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
        
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.streetName").exists());
    }

    @Test
    public void insertAddressShouldReturnAddress() throws Exception {
        ResultActions result = mockMvc.perform(
            delete("/address/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNoContent());
    }

    @Test
    public void findByIdShouldReturnAddress() throws Exception {
        ResultActions result = mockMvc.perform(
            get("/address/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.streetName").exists());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/address")
            .accept(MediaType.APPLICATION_JSON));
        
        result.andExpect(status().isOk());
    }
}
