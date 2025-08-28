package com.rht.santander.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rht.santander.service.model.BankingEntity;

@Repository
public interface BankingEntityRepository extends CrudRepository<BankingEntity, Long> {

	Optional<BankingEntity> findByCode(String code);
	
}
