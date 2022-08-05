package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.Bank;
import com.tein.overcatchbackend.domain.model.NoticeLog;
import com.tein.overcatchbackend.domain.model.Company;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface NoticeLogRepository extends JpaRepository<NoticeLog, Long> {

    @Query(value = "select * from NoticeLog n where n.client_id=:clientId", nativeQuery = true)
    List<NoticeLog> findAllByClientId(Long clientId);


    @Query(value = "select * from notice_log n "
            + "join user u on u.id = n.user_id "
            + "join client c on c.id = n.client_id "
            + "left join company co on co.client_id = c.id "
            + "left join founder_owner f on f.client_id = c.id "
            + "where (:notificationType is null or n.noti_type like concat('%',:notificationType,'%')) "
            + "and(:clientType is null or c.client_type like concat('%',:clientType,'%')) "
            + "and ( (co.name like concat('%',:search,'%'))" +
            "  or ( f.name like concat('%',:search,'%')) " +
            "  or ( (n.message like concat('%',:search ,'%')) or ( n.subject like concat('%', :search ,'%')) )" +
            "  or ( f.trade_as_name like concat('%',:search,'%'))" +
            "  or ( (u.name like concat('%',:search,'%')) or (u.surname like concat('%',:search,'%')) )" +
            "  )"
            + " order by n.insert_time desc "
            , nativeQuery = true)
    Page<NoticeLog> findAll(String notificationType, String clientType, String search, Pageable pageable);

    @Query(value = "SELECT * FROM overcatch.notice_log nlog group by nlog.message",nativeQuery = true)
    Page<NoticeLog> getNotificationLogs(Pageable pageable);

    @Query(value =      "SELECT * FROM overcatch.notice_log nlog " +
                        "where (:startDate is null or nlog.insert_time >= :startDate) " +
                        "and (:endDate is null or nlog.insert_time <= :endDate) " +
                        "and (:notificationType is null or nlog.noti_type like concat('%',:notificationType,'%')) " +
                        "and (:search  is null or lower(nlog.subject) like concat('%',:search, '%')) " +
                        "group by nlog.message",nativeQuery = true)
    Page<NoticeLog> getNotificationFilterLogs(String search , String notificationType , LocalDate startDate , LocalDate endDate , Pageable pageable);

}
