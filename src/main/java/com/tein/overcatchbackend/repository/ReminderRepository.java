package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Reminder;
import org.apache.catalina.Manager;
import org.apache.poi.hpsf.Decimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query(value ="SELECT * FROM overcatch.reminder where id = (SELECT max(id) FROM overcatch.reminder)",nativeQuery = true)
    Reminder findReminderDates();

    @Query(value ="SELECT * from client c left join customer_client cc on c.id=cc.client_id " +
            "left join customer cs on cc.customer_id=cs.id " +
            "left join user u on u.id=cs.user_id  ",nativeQuery = true)
    List<Client> findByEndYear(LocalDate date);

    @Query(value ="SELECT * from client c left join customer_client cc on c.id=cc.client_id " +
            "left join customer cs on cc.customer_id=cs.id " +
            "left join user u on u.id=cs.user_id ",nativeQuery = true)
    List<Client> findByYear(LocalDate date);

    @Query(value ="SELECT * from client c left join customer_client cc on c.id=cc.client_id " +
            "left join customer cs on cc.customer_id=cs.id " +
            "left join user u on u.id=cs.user_id  ",nativeQuery = true)
    List<Client> findAllClient(LocalDate date);

    @Query(value="SELECT next_of_email from director_detail where id IN " +
            "(SELECT director_detail_id from director_company where client_id=:director)",nativeQuery = true)
    List<String>findAllManager(long director);

    @Query(value="SELECT next_of_email from director_detail where id IN " +
            "(SELECT director_detail_id from director_company where client_id=:director)",nativeQuery = true)
    List<String>findAllVat(long director);

    @Query(value="left join DirectorCompany on DirectorDetail.id=DirectorCompany.director_detail_id where DirectorCompany.client+_id=:string",nativeQuery = true)
    List<Manager>findAllManager(String string);
}
