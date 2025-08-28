package com.rht.santander.api.v1;

import java.util.NoSuchElementException;

import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.exception.NotValidException;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.model.BankingEntityResponse;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;
import com.rht.santander.utils.DataUtils;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Proces Banking Entity", description = "Endpoint de registro de entidades bancarias")
@RestController
@RequestMapping(value = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BankingEntityUpdateController {

	private final RestTemplate restTemplate;
	private final BankingEntityService service;
	private final BankingEntityApiMapper mapper;
	
	public BankingEntityUpdateController(
			RestTemplate restTemplate,
			BankingEntityService service, 
			BankingEntityApiMapper mapper) {
		this.restTemplate = restTemplate;
		this.service = service;
		this.mapper = mapper;
	}

	/**
	 * API para el registro de entidades bancarias. 
	 * 
	 * @param request
	 * 				BankingEntityRequest con los datos de la entidad a registrar
	 * 
	 * @return BankingEntityResponse
	 * @throws Exception 
	 */
	@PostMapping("/entities")
	public ResponseEntity<BankingEntityResponse> processBankingEntity(@RequestBody @Valid BankingEntityRequest request) 
			throws NotValidException {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER + request);
		try {
			BankingEntity entity = mapper.toBankingEntity(request);
			service.save(entity);
			BankingEntityResponse response = DataUtils.createResponseOk();
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER + response);
			return ResponseEntity.ok(response);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME 
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
	
	/**
	 * API para actualizar los datos de una entidad registrada
	 * 
	 * @param request
	 * 			BankingEntityRequest con los datos actualizados de la entidad
	 * @return BankingEntityResponse
	 * @throws Exception 
	 */
	@PutMapping("/entities")
	public ResponseEntity<BankingEntityResponse> updateBankingEntity(@RequestBody @Valid BankingEntityRequest request) 
			throws NotValidException, NoSuchElementException {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER + request);
		try {
			BankingEntity entity = mapper.toBankingEntity(request);
			service.update(entity);
			BankingEntityResponse response = DataUtils.createResponseOk();
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER + response);
			return ResponseEntity.ok(response);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME 
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
	
	/**
	 * API para eliminar el registro de una entidad
	 * 
	 * @param code
	 * 			el codigo de la entidad que se desea eliminar
	 * @return BankingEntityResponse
	 */
	@DeleteMapping("/entities/{code}")
	public ResponseEntity<BankingEntityResponse> deleteBankingEntity(@PathVariable String code) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER + code);
		try {
			service.delete(code);
			BankingEntityResponse response = DataUtils.createResponseOk();
			log.debug(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER + response);
			return ResponseEntity.ok(response);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
	
	/**
	 * API para eliminar una entidad registrada utilizando el end point de 
	 * consulta, según se pide en el enunciado del ejercicio tecnico.
	 * 
	 * Nota: considero que no es ideal y en su lugar se pueden reutilizar 
	 * los metodos directamente.
	 * 
	 * @param code
	 * 			el codigo de la entidad que se desea eliminar
	 * @return BankingEntityResponse
	 */
	@DeleteMapping("/entities")
	public ResponseEntity<BankingEntityResponse> deleteWithQuery(@RequestParam(required = true) String code) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER + code);
		try {
			// Llamada HTTP al mismo servicio
			String url = "http://localhost:8088/banking/v1/entities/" + code;
			ResponseEntity<BankingEntityRequest> request = restTemplate.exchange(url, HttpMethod.GET, null, BankingEntityRequest.class);
			if(!request.getStatusCode().is2xxSuccessful()) {
				return ResponseEntity.ok(DataUtils.createResponseError(
						DataUtils.EXCEPTION_REMOVE_MSG + DataUtils.EXCEPTION_REMOVE_NULL_MSG));
			}
			BankingEntity entity = mapper.toBankingEntity(request.getBody());
			service.delete(entity);
			BankingEntityResponse response = DataUtils.createResponseOk();
			log.debug(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER + response);
			return ResponseEntity.ok(response);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
}
