package com.rht.santander.api.v1.mapper;

import java.util.List;

import com.rht.santander.model.BankingEntityList;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.service.model.BankingEntity;

public interface BankingEntityApiMapper {

	/**
	 * Mapeo de lista de entidades a Model de BankingEntityList.
	 * 
	 * @param request
	 * 			la lista de entidades a mapear
	 * @return el Model de BankingEntityList mapeado
	 */
	public BankingEntityList toBankingEntityList(List<BankingEntity> entities);
	
	/**
	 * Mapeo de Model de BankingEntity a BankingEntityRequest
	 * 
	 * @param entity
	 * 				el model del BankingEntity a mapear
	 * @return el BankingEntityRequest mapeado
	 */
	public BankingEntityRequest toBankingEntityRequest(BankingEntity entity);
	
	/**
	 * Mapeo de BankingEntityRequest a Model de BankingEntity.
	 * 
	 * @param request
	 * 			el BankingEntityRequest a mapear
	 * @return el Model de BankingEntity mapeado
	 */
	public BankingEntity toBankingEntity(BankingEntityRequest request);
	
}
