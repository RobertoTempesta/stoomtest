package com.roberto.stoomtest.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_address")
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter	@Setter
    private Long id;
    
    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String streetName;

    @NotNull(message = "Field is not null")
    @Getter	@Setter
    private Integer number;

    @Getter	@Setter
    private String complement;

    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String neighbourhood;
    
    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String city;

    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String state;
    
    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String country;
    
    @NotBlank(message = "Field is required")
    @Getter	@Setter
    private String zipcode;
    
    @Getter	@Setter
    private String latitude;
    
    @Getter	@Setter
    private String longitude;

    public Address(Long id, String streetName, Integer number, String complement, String neighbourhood, String city,
            String state, String country, String zipcode, String latitude, String longitude) {
        this.id = id;
        this.streetName = streetName;
        this.number = number;
        this.complement = complement;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
