package com.rht.santander.model;

import com.rht.santander.utils.DataUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model del Request de la API entity
 * 
 * @author Luciano Prol
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankingEntityRequest {

	@Schema(description = "Id de la transaccion (uso interno).", example = "WLH20250708230528096")
	private String traceId;
	
	@Schema(description = "Codgido de entidad (requerido).", example = "150")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String code;
	
	@Schema(description = "Razon social de la entidad (requerido).", example = "HSBC Bank Argentina S.A.")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String name;
	
	@Schema(description = "Tipo de entidad (requerido).", example = "Banco")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String type;
	
	@Schema(description = "Calle del domicilio de la entidad (requerido).", example = "Alsina")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String streetName;
	
	@Schema(description = "Numero de la calle del domicilio de la entidad (requerido).", example = "2258")
	@NotNull(message = DataUtils.NOT_NULL_MSG)
	private int buildingNumber;
	
	@Schema(description = "Localidad del domicilio de la entidad (requerido).", example = "CABA")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String city;
	
	@Schema(description = "Provincia del domicilio de la entidad (requerido).", example = "CABA")
	@NotEmpty(message = DataUtils.NOT_EMPTY_MSG)
	private String province;
	
	@Schema(description = "Codigo postal de la entidad (requerido).", example = "1234")
	@NotNull(message = DataUtils.NOT_NULL_MSG)
	private int zipCode;
	
	@Schema(description = "Telefono de la entidad  (opcional).", example = "4567-7654")
	private String phoneNumber;
	
}
