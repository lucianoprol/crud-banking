package com.rht.santander.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.rht.santander.exception.NotValidException;
import com.rht.santander.exception.TechnicalException;
import com.rht.santander.repository.BankingEntityRepository;
import com.rht.santander.service.BankingEntityService;
import com.rht.santander.service.model.BankingEntity;
import com.rht.santander.utils.DataUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BankingEntityServiceImpl implements BankingEntityService {

	private final BankingEntityRepository entityRepository;
	
	public BankingEntityServiceImpl(BankingEntityRepository entityRepository) {
		this.entityRepository = entityRepository;
	}
	
	@Override
	public List<BankingEntity> getAll() {
		return (List<BankingEntity>) entityRepository.findAll();
	}
	
	@Override
	public BankingEntity getByCode(String code) {
		return entityRepository.findByCode(code).orElseThrow(
				() -> new NoSuchElementException(
						String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
								+ DataUtils.EXCEPTION_VALIDATION_ENTITY, code)));
	}

	@Override
	public boolean save(BankingEntity entity) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_SERVICE);
		
		if(entity != null) {
			if(entityExists(entity)) {
				throw new NotValidException(String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
								+ DataUtils.EXCEPTION_VALIDATION_EXIST_ENTITY, entity.getCode()));
			}
			
			entityRepository.save(entity);
		} else {
			throw new TechnicalException(DataUtils.EXCEPTION_STORAGE_MSG + DataUtils.EXCEPTION_STORAGE_NULL_MSG);
		}
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_SERVICE_OK
				+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
		
		return true;
	}

	@Override
	public boolean update(BankingEntity entity) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_SERVICE);
		
		if(entity != null) {
			try {
				BankingEntity existing = getByCode(entity.getCode());
				updateExisting(existing, entity);
				entityRepository.save(existing);
			} catch(NoSuchElementException e) {
				throw new NotValidException(String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
								+ DataUtils.EXCEPTION_VALIDATION_ENTITY, entity.getCode()));
			}
		} else {
			throw new TechnicalException(DataUtils.EXCEPTION_STORAGE_MSG + DataUtils.EXCEPTION_STORAGE_NULL_MSG);
		}
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_SERVICE_OK
				+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
		
		return true;
	}
	
	@Override
	public boolean delete(String code) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_SERVICE);
		try {
			BankingEntity entity =  getByCode(code);
			entityRepository.delete(entity);
		} catch(NoSuchElementException e) {
			throw new NotValidException(String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
							+ DataUtils.EXCEPTION_VALIDATION_ENTITY, code));
		}
		
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_SERVICE_OK
				+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
		
		return true;
	}
	
	@Override
	public boolean delete(BankingEntity entity) {
		long startTime = System.currentTimeMillis();
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_ENTRY_SERVICE);
		
		try {
			BankingEntity existing =  getByCode(entity.getCode());
			entityRepository.delete(existing);
		} catch(NoSuchElementException e) {
			throw new NotValidException(String.format(DataUtils.EXCEPTION_VALIDATION_MSG 
							+ DataUtils.EXCEPTION_VALIDATION_ENTITY, entity.getCode()));
		}
		log.info(new Object(){}.getClass().getEnclosingMethod().getName() + DataUtils.LOG_EXIT_SERVICE_OK
				+ (System.currentTimeMillis() - startTime) + DataUtils.EXECUTION_TIME_MS);
		
		return true;
	}
	
	private boolean entityExists(BankingEntity entity) {
		return entityRepository.findByCode(entity.getCode()).isPresent();
	}
	
	private void updateExisting(BankingEntity existing, BankingEntity entity) {
		existing.setName(entity.getName());
		existing.setType(entity.getType());
		existing.setStreetName(entity.getStreetName());
		existing.setBuildingNumber(entity.getBuildingNumber());
		existing.setCity(entity.getCity());
		existing.setProvince(entity.getProvince());
		existing.setZipCode(entity.getZipCode());
		existing.setPhoneNumber(entity.getPhoneNumber());
	}
}
