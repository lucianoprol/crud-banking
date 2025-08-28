package com.rht.santander.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model del Response de la API process entity
 * 
 * @author Luciano Prol
 */
@Data
@NoArgsConstructor
public class BankingEntityResponse {

	@Schema(description = "Id de la transaccion.", example = "WLH20250708230528096")
	private String traceId;
	
	@Schema(description = "Resultado. OK o ERROR", example = "OK")
	private String result;

	@Schema(description = "Codigo de la respuesta.", example = "0000")
	private String cod;

	@Schema(description = "Mensaje del resultado.", example = "Se proceso el pedido correctamente")
	private String msj;

}
