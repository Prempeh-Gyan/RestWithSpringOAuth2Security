
package com.prempeh.restfulwebservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prempeh.restfulwebservice.model.Client;
import com.prempeh.restfulwebservice.model.ClientAndUser;
import com.prempeh.restfulwebservice.model.User;

@Repository
public interface ClientAndUserRepository extends JpaRepository<ClientAndUser, Long> {

	@Query("SELECT g FROM ClientAndUser g WHERE g.user = :user")
	List<ClientAndUser> findByUser(@Param("user") User user);

	@Query("SELECT g FROM ClientAndUser g WHERE g.client = :client")
	List<ClientAndUser> findByClient(@Param("client") Client client);

	@Query("SELECT g FROM ClientAndUser g WHERE g.client = :client AND g.user = :user")
	List<ClientAndUser> findByClientAndUser(@Param("client") Client client, @Param("user") User user);
}
