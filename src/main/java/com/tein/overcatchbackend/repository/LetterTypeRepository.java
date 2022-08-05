package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.LetterType;
import com.tein.overcatchbackend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterTypeRepository extends JpaRepository<LetterType, Long> {

    LetterType findById(LetterType letterType);

    @Query(value = "select * from letter_type where (is_active is null or is_active =1) and user_role like concat('%', :userRole, '%') ", nativeQuery = true)
    List<LetterType> findAllLetter(String userRole);

    @Query(value = "SELECT * FROM letter_type where client_type like concat(\"%\",:clientType,\"%\")" +
            " and user_role like concat(\"%\",:userRole,\"%\") and (is_active is null or is_active =1)", nativeQuery = true)
    List<LetterType> getLetterTypeByClientTypeAndAndUserRole(String clientType, String userRole);

    @Query(value = "SELECT * FROM overcatch.letter_type lt join letters l on l.letter_type_id = lt.id " +
            " group by lt.id order by lt.letter_type_name asc ", nativeQuery = true)
    List<LetterType> getAllUsed();
}
