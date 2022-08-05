package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Roles;
import com.tein.overcatchbackend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    //Roles findByRoleCode(String roleCode);
    Optional<Roles> findByRoleCode(String roleCode);

    Roles findByRoleDescription(String roleDescription);

//    @Transactional
//    @Modifying(flushAutomatically=true,clearAutomatically=true)
//    @Query("DELETE FROM Roles std WHERE std.= ?1 ")
//    public int deleteByStockTransaction(Long transactionId);

    @Query("DELETE FROM Roles std WHERE std.users IN  ?1")
    public int deleteRolesByUsers(List<User> users);



}
