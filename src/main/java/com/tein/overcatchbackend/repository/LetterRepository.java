package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.Letter;
import com.tein.overcatchbackend.domain.model.LetterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    Letter findById(Letter letter);

    Letter findByTaskId(Long id);

    @Query(value = "select * from letters let " +
            "             join letter_type lt on lt.id = let.letter_type_id " +
            "             join client c on c.id = let.client_id " +
            "             join customer_client cc on c.id = cc.client_id " +
            "             join customer cu on cc.customer_id = cu.id " +
            "             join user u on u.id = cu.user_id " +
            "             left join document d on d.id = let.document_id " +
            "             where c.id = :id ", nativeQuery = true)
    Page<Letter> findAllByClientId(Long id, Pageable pageable);

    List<Letter> findAllByLetterTypeIsNull();

    @Query(value = "select * from letters c " +
            "            join letter_type cc on cc.id = c.letter_type_id " +
            "            join client u on u.id = c.client_id " +
            "            join customer_client cs on u.id = cs.client_id " +
            "            join customer ca on cs.customer_id = ca.id " +
            "            join user t on t.id = ca.user_id " +
            "            left join document d on d.id = c.document_id" +
            "            where (c.letter_type_id  IN :letterTypes ) " +
            "           and ( (:search is null or t.name like concat('%',:search,'%') )" +
            "or (:search is null or t.surname like concat('%',:search,'%') ) " +
            "or (:search is null or cc.letter_type_name like concat('%',:search,'%') ))" , nativeQuery = true)
    Page<Letter> findAllLettersByMultiFilter(List<String> letterTypes, String search, Pageable pageable);


   }
