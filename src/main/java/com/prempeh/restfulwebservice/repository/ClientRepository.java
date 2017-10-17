
package com.prempeh.restfulwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prempeh.restfulwebservice.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT c FROM Client c WHERE c.clientId = :clientId")
	Client findByClientId(@Param("clientId") String clientId);
}