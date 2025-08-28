package com.rht.santander.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model de Response de la API list entities
 * 
 * @author Luciano Prol
 */
@Data
@NoArgsConstructor
public class BankingEntityList {

	@Schema(description = "Cantidad total Listado de Entidades Registradas")
	int totalBankingEntities;
	
	@Schema(description = "Listado de Entidades Registradas")
	List<BankingEntityRequest> entityRequestList;
}
