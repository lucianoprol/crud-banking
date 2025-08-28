package com.rht.santander.service;

import java.util.List;

import com.rht.santander.service.model.BankingEntity;

/**
 * Interface para manejo de entidades registradas
 * 
 * @author Luciano Prol
 */
public interface BankingEntityService {

	public List<BankingEntity> getAll();

	public BankingEntity getByCode(String code);
	
	public boolean save(BankingEntity entity);
	
	public boolean update(BankingEntity entity);
	
	public boolean delete(String code);
	
	public boolean delete(BankingEntity entity);
}
