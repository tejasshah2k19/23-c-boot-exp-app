package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	List<AccountEntity> findByUser(Integer userId);
	
	@Query(value = "select * from account where balanace <= :amount ",nativeQuery = true)
	List<AccountEntity> lowerBalanceAccount(Integer amount);

	//* from AccountEntity -> HQL  

	
}
