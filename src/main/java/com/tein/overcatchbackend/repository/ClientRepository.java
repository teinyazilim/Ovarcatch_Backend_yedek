package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.model.AddressNew;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Company;
import com.tein.overcatchbackend.domain.model.Customer;
import com.tein.overcatchbackend.enums.AddressType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    @Query(value = "select * from client c " +
            "left join customer_client cc on c.id = cc.client_id " +
            "left join customer cu on cc.customer_id = cu.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company d on d.client_id = c.id " +
            "left join director_company dc on dc.client_id = c.id " +
            "left join director_detail dd on dc.director_detail_id = dd.id " +
            "where c.id = :clientId ",
            nativeQuery = true)
    Optional<Client> findByClientId(long clientId);


    /////////adresisiz aşağıda yukardakine göre


    @Query(value = "select * from client c " +
            "left join customer_client cc on c.id = cc.client_id " +
            "left join customer cu on cc.customer_id = cu.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company d on d.client_id = c.id " +
            "left join address a on a.client_id = c.id " +
            "left join director_company dc on dc.client_id = c.id " +
            "left join director_detail dd on dc.director_detail_id = dd.id " +
            "where c.id = :clientId  and a.address_type='HOME'",
            nativeQuery = true)
    Optional<Client> findByClientIdByAddress(long clientId);




    @Query(value =  "Select * from client ct" +
            " join customer_client cc on cc.client_id = ct.id" +
            " join company cy on cy.client_id = ct.id" +
            " join customer cu on cc.customer_id = cu.id" +
            " join user u on u.id = cu.user_id" +
            " where :clientType like concat('%', client_type ,'%') and :aggrementType like concat('%', aggrement_type ,'%') and u.is_active = true",nativeQuery = true)
    List<Client> getLimitedOfReminder(String clientType , String aggrementType);


//    @Query(value = "select * from user u " +
//            "left join users_company uc on u.id = uc.user_id " +
//            "left join company c on uc.company_id = c.id " +
//            "where c.id = :companyId ",
//            nativeQuery = true)
//    Optional<Company> findByUserId(long userId);

    @Query(value = "select * from client c " +
            "left join customer_client cc on c.id = cc.client_id " +
            "left join user u on cc.customer_id = u.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company ic on ic.client_id = c.id " +
            "where c.id = :companyId ",
            nativeQuery = true)
    Page<Client> findAllCompany(Pageable pageable);

    @Query(value = "select * from client c left join customer_client cc on c.id = cc.client_id " +
            "left join user u on cc.customer_id = u.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company ic on ic.client_id = c.id where c.client_type=:clientTypeEnum",
            nativeQuery = true)
    List<Client> findAllByClientTypeEnum(String clientTypeEnum);

    @Query(value = "select * from client c left join customer_client cc on c.id = cc.client_id " +
            "left join user u on cc.customer_id = u.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company ic on ic.client_id = c.id where c.aggrement_type=:aggrement_type",
            nativeQuery = true)
    List<Client> fAllAggrementType(String aggrement_type);

    @Query(value = "select * from client c left join customer_client cc on c.id = cc.client_id " +
            "left join user u on cc.customer_id = u.id " +
            "left join founder_owner stc on stc.client_id = c.id " +
            "left join company ic on ic.client_id = c.id where c.is_vat_member=1",
            nativeQuery = true)
    List<Client> findAllByVat();
    // büşra aşağıya ekleme yaptı. Eski haline getirmek için join adres ve address_type kaldır.
    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cs.id=cc.customer_id " +
            "join address a on c.id = a.client_id " +
            "where cs.user_id = :userId and a.address_type='HOME'",
            nativeQuery = true)
    List<Client> findByUserId(long userId);

    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cs.id=cc.customer_id " +
            "where cs.user_id in :userIds ",
            nativeQuery = true)
    List<Client> findAllCompanyWithUsers(List<Integer> userIds);



    @Query(value="select * from client c where c.code in :codes", nativeQuery = true)
    List<Client> findAllCompanyWithDirectorList(List<String> codes);

    @Query(value =  "Select * from client ct"
                    +" join customer_client cc on cc.client_id = ct.id "
                    + " join customer cu on cc.customer_id = cu.id "
                    + " join user u on u.id = cu.user_id "
                    + "where :clientType like concat('%', client_type ,'%') and :aggrementType like concat('%', aggrement_type ,'%') "
                    + "and is_vat_member = :isVat "
                    + "and u.is_active = true and ct.state = 3",nativeQuery = true)
    List<Client> getClientByFilterWithVat(String clientType , String aggrementType , Boolean isVat);



    @Query( value = "Select * from client cl " +
                    "left join notice_log nlog on cl.id = nlog.client_id " +
                    "left join document doc on nlog.document_id = doc.id "+
                    "where nlog.subject = :subject and nlog.message=:message ",nativeQuery = true)
    List<Client> getClientOfNotifications(String subject , String message);


    //Gönderdiğim committe bu sayfada var ama yaptığım değişikliği hatırlamıyorum iki sayfa ile kıyaslama yaparsın abla
    //List<Client> getNoticeLogFiltersOfSearch(String search ,String subject , String message);


    @Query(value =  "Select * from client ct"
            +" join customer_client cc on cc.client_id = ct.id "
            + " join customer cu on cc.customer_id = cu.id "
            + " join user u on u.id = cu.user_id "
            + "where :clientType like concat('%', client_type ,'%') and :aggrementType like concat('%', aggrement_type ,'%') "
            + "and u.is_active = true and ct.state = 3",nativeQuery = true)
    List<Client> getClientByFilterWithOutVat(String clientType , String aggrementType);


    @Query(value =  "Select * from client ct"
            +" join customer_client cc on cc.client_id = ct.id "
            + " join customer cu on cc.customer_id = cu.id "
            + " join user u on u.id = cu.user_id "
            + "where :clientType like concat('%', client_type ,'%') and :aggrementType like concat('%', aggrement_type ,'%') "
            + "and u.is_active = true",nativeQuery = true)
    List<Client> getClientOfReminder(String clientType , String aggrementType);

    @Query(value =  "SELECT * FROM overcatch.client as cl where cl.client_type='LIMITED'" ,nativeQuery = true)
    List<Client> getLimitedList(String clientType , String aggrementType);

    //Reminder Sayfasın için hatırlatmalara cevap dönen(Status true) sole-trader kullanıcıları sayan
    @Query(value = "SELECT * FROM overcatch.client clt where clt.client_type = 'SOLETRADE' and clt.status = 1 and (clt.reminderdate <= :date1) ",nativeQuery = true)
    List<Client> getClientReminderFirstCount(LocalDate date1);

    @Query(value = "SELECT * FROM overcatch.client clt where clt.client_type = 'SOLETRADE' and clt.status = 1 and (clt.reminderdate <= :date2) and (clt.reminderdate > :date1) ",nativeQuery = true)
    List<Client> getClientReminderSecondCount(LocalDate date1 , LocalDate date2);

    @Query(value = "SELECT * FROM overcatch.client clt where clt.client_type = 'SOLETRADE' and clt.status = 1 and (clt.reminderdate <= :date3) and (clt.reminderdate > :date2) ",nativeQuery = true)
    List<Client> getClientReminderThirdCount(LocalDate date2, LocalDate date3);

    @Query(value = "SELECT * FROM overcatch.client clt where clt.client_type = 'SOLETRADE' and clt.status = 1 and (clt.reminderdate <= :date4) and (clt.reminderdate > :date3) ",nativeQuery = true)
    List<Client> getClientReminderFourthCount(LocalDate date3 , LocalDate date4);
    //Reminder Sayfasın için hatırlatmalara cevap dönen(Status true) sole-trader kullanıcıları sayan

    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cc.customer_id = cs.id " +
            "where cs.id = :userId and c.is_active=1 and c.state!=\"3\" and c.state!=\"2\" ",
            nativeQuery = true)
    List<Client> findAllApplyByUserId(long userId);

    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cc.customer_id = cs.id " +
            "join user u on u.id = cs.user_id " +
            "where c.is_active=1 and c.state!=\"3\" and c.state!=\"2\" and u.is_deleted==false",
            nativeQuery = true)
    List<Client> findAllApply();


    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cc.customer_id = cs.id " +
            "join user u on u.id = cs.user_id " +
            "where c.is_active=1 and c.state!='3' and c.state!='2' " +
            "and (:aggrementType is null or c.aggrement_type like concat('%',:aggrementType,'%')) " +
            "and (:clientType is null or c.client_type like concat('%',:clientType,'%')) " +
            "and (:exist is null or c.is_existing = :exist) " +
            "and (:state is null or c.state = :state) " +
            "and ( (:search is null or u.name like concat('%',:search,'%') ) or (:search is null or u.surname like concat('%',:search,'%') )) "
            , nativeQuery = true)
    List<Client> findAllApplyByFilter(String aggrementType, String clientType, String exist, String state, String search);

    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cc.customer_id = cs.id " +
            "join user u on u.id = cs.user_id " +
            " where cs.id = :userid and c.is_active=1 and c.state!='3' and c.state!='2' " +
            " and ( c.aggrement_type  IN :aggrementTypes) " +
            " and ( c.client_type  IN  :clientTypes) " +
            " and (:exist is null or c.is_existing = :exist) " +
            " and (:state is null or c.state = :state) " +
            " and ( (:search is null or u.name like concat('%',:search,'%') ) or (:search is null or u.surname like concat('%',:search,'%') )) "
            , nativeQuery = true)
    List<Client> findAllApplyByFilterByUserId(List<String> aggrementTypes, List<String> clientTypes, String exist, String state, String search, long userid);


    @Query(value = "select * from client c " +
            "left join company co on co.client_id = c.id " +
            "            join customer_client cc on c.id = cc.client_id " +
            "            join customer cs on cc.customer_id = cs.id " +
            "            join user u on u.id = cs.user_id " +
            "            left join company cy on cy.client_id = c.id "+
            "            where c.is_active=1 " +
            "            and (  c.aggrement_type  IN :aggrementTypes ) " +
            "            and (  c.client_type  IN  :clientTypes ) " +
            "            and (:state is null or c.state = :state) " +
            "            and ((:search is null or u.name like concat('%',:search,'%') ) or (:search is null or u.surname like concat('%',:search,'%') ) or (:search  is null or c.code like concat('%',:search ,'%')) or (:search  is null or cy.name like concat('%',:search ,'%')) )" +
            "            and (:exist is null or c.is_existing = :exist) " , nativeQuery = true)
    List<Client> findAllApplyByMultiFilter(List<String> aggrementTypes, List<String> clientTypes, String  exist, String state, String search);


    @Query(value = "select * from client c " +
            "join customer_client cc on c.id = cc.client_id " +
            "join customer cs on cc.customer_id = cs.id " +
            "where c.is_active=1 and c.state =3",
            nativeQuery = true)
    Page<Client> findAllApproved(Pageable pageable);


    @Query(value = "select * from Client c " +
            "left join company co on co.client_id = c.id " +
            "left join founder_owner fo on fo.client_id = c.id  " +
            "join customer_client cc on cc.client_id = c.id " +
            "join customer cu on cu.id = cc.customer_id " +
            "join user u on u.id = cu.user_id " +
            " where c.is_active =true and c.state ='3' " +
            "             and (  c.aggrement_type  IN :aggTypes ) " +
            "             and (  c.client_type  IN  :clientTypes ) " +
            "             and ( (co.name like concat('%',:searchStr,'%'))" +
            "                    or (fo.name like concat('%',:searchStr,'%')) " +
            "                    or (fo.trade_as_name like concat('%',:searchStr,'%'))" +
            "                    or ( (u.name like concat('%',:searchStr,'%')) or (u.surname like concat('%',:searchStr,'%')) )" +
            "                    or (  c.code like concat('%',:searchStr,'%'))" +
            "                   )"
            , nativeQuery = true)
    Page<Client> findAllApprovedByFilter(List<String> aggTypes, List<String> clientTypes, String searchStr, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "update Client c set c.state= :state " +
            "where c.id= :clientId ")
    void changeState(Long clientId, String state);


    Client findByCode(String code);
}

    