package com.rht.santander.api.v1;

import java.util.List;

import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.model.BankingEntityList;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;
import com.rht.santander.utils.DataUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Consulta Entidades Registradas", description = "Endpoint de consulta del listado de entidades registradas")
@RestController
@RequestMapping(value = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BankingEntityQueryController {

	private final BankingEntityService service;
	private final BankingEntityApiMapper mapper;
	
	public BankingEntityQueryController(BankingEntityService service,
			BankingEntityApiMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	/**
	 * Listado de entidades registradas. 
	 *  
	 * @return Devuelve la lista de entidades registradas
	 * 
	 * @author Luciano Prol
	 */
	@Operation(summary = "Listado de entidades registradas")
	@GetMapping(value = "/entities", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BankingEntityList> getAll() {
		long startTime = System.currentTimeMillis();
		log.debug(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER);
		try {
			List<BankingEntity> entities = service.getAll();
			BankingEntityList entityList = mapper.toBankingEntityList(entities);
			log.debug(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER);
			return ResponseEntity.ok(entityList);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
	
	/**
	 * Consulta de entidades registradas por codigo de entidad.
	 * 
	 * @param code
	 * 			el codigo de la entidad buscada
	 * @return Devuelve los datos de la entidad registrada
	 */
	@GetMapping("/entities/{code}")
	public ResponseEntity<BankingEntityRequest> getByCode(@PathVariable String code) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_CONTROLLER + code);
		try {
			BankingEntity entity = service.getByCode(code);
			BankingEntityRequest response = mapper.toBankingEntityRequest(entity);
			log.debug(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_CONTROLLER + response);
			return ResponseEntity.ok(response);
		} finally {
			log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.EXECUTION_TIME
					+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
			MDC.clear();
		}
	}
	
}
