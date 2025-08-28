package com.rht.santander.service.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model de Entidad
 * 
 * @author Luciano Prol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String code;
	private String name;
	private String type;
	private String streetName;
	private int buildingNumber;
	private String city;
	private String province;
	private int zipCode;
	private String phoneNumber;
	
}
