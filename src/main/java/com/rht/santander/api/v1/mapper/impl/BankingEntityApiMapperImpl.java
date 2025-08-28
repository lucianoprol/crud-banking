package com.rht.santander.api.v1.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rht.santander.api.v1.mapper.BankingEntityApiMapper;
import com.rht.santander.model.BankingEntityList;
import com.rht.santander.model.BankingEntityRequest;
import com.rht.santander.service.model.BankingEntity;
import com.rht.santander.utils.DataUtils;

@Component
public class BankingEntityApiMapperImpl implements BankingEntityApiMapper {

	@Override
	public BankingEntityList toBankingEntityList(List<BankingEntity> entities) {
		BankingEntityList entityList = new BankingEntityList();
		List<BankingEntityRequest> entityRequestList = new ArrayList<>();
		for (BankingEntity entity : entities) {
			BankingEntityRequest entityRequest = this.toBankingEntityRequest(entity);
			entityRequestList.add(entityRequest);
		}
		entityList.setEntityRequestList(entityRequestList);
		entityList.setTotalBankingEntities(entityRequestList.size());
		return entityList;
	}
	
	@Override
	public BankingEntityRequest toBankingEntityRequest(BankingEntity entity) {
		BankingEntityRequest entityRequest = new BankingEntityRequest();
		entityRequest.setCode(entity.getCode());
		entityRequest.setName(entity.getType());
		entityRequest.setStreetName(entity.getStreetName());
		entityRequest.setBuildingNumber(entity.getBuildingNumber());
		entityRequest.setCity(entity.getCity());
		entityRequest.setProvince(entity.getProvince());
		entityRequest.setZipCode(entity.getZipCode());
		entityRequest.setPhoneNumber(entity.getPhoneNumber());
		entityRequest.setTraceId(DataUtils.getTraceId());
		return entityRequest;
	}
	
	
	@Override
	public BankingEntity toBankingEntity(BankingEntityRequest request) {
		BankingEntity entity = new BankingEntity();
		entity.setCode(request.getCode());
		entity.setName(request.getName());
		entity.setType(request.getType());
		entity.setStreetName(request.getStreetName());
		entity.setBuildingNumber(request.getBuildingNumber());
		entity.setCity(request.getCity());
		entity.setProvince(request.getProvince());
		entity.setZipCode(request.getZipCode());
		entity.setPhoneNumber(request.getPhoneNumber());
		return entity;
	}
	
}
