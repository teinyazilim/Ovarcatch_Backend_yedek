package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.TaskListResult;
import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.domain.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskListRepository  extends JpaRepository<TaskListResult, Long> {

   @Query(value ="select t.id AS ID," +
           " mt.module_type_enum as module_type," +
           "t.process_date AS process_date, "+
           "CONCAT(u.name, ' ', u.surname) AS user_name, "+
           "(c.id) as client_id, " +
           "(u.user_folder) as user_folder, " +
           "c.client_type AS client_type, "+
           "c.aggrement_type AS aggrement_type, "+
           "CONCAT(u2.name, ' ', u2.surname) AS customer_name, "+
           "tc.process_date AS confirm_date," +
           " tc.confirm_type AS confirm_type," +
           " t.id AS task_id, "+
           "CONCAT(up.name, ' ', up.surname) AS personel_name," +
           "p.department AS department "+
           "from tasks t left join client c on c.id = t.client_id "+
           "left join module_type mt on mt.id=t.module_type "+
           "left join module_type mt2 on mt.id  = mt2.module_type_id "+
           "left join customer_client cct on cct.client_id = c.id "+
           "left join customer cc on cc.id  = cct.customer_id "+
           "left join user u on u.id = t.user_id "+
           "left join user u2 on u2.id = cc.user_id "+
           "left join (select max(id) max_id,task_id "+
           "from tasks_confirmation "+
           "group by task_id) t_max on (t_max.task_id=t.id) "+
           "left join tasks_confirmation tc ON (tc.id = t_max.max_id) "+
           "left join personel p on p.id = tc.personel_id "+
           "left join user up on up.id = p.user_id where mt.module_type_id= (Select id mtt from module_type mtt where mtt.module_type_enum=:moduleType and (confirm_type != \"DONE\" OR confirm_type is null )) "
           ,nativeQuery = true)
   List<TaskListResult> findAllTaskListWithConfirmation(String moduleType);


   //***************
   @Query(value ="select t.id AS ID," +
           " mt.module_type_enum as module_type,t.process_date AS process_date, "+
           "CONCAT(u.name, ' ', u.surname) AS user_name, "+
           "(c.id) as client_id, (u.user_folder) as user_folder," +
           " c.client_type AS client_type, "+
           "c.aggrement_type AS aggrement_type, "+
           "CONCAT(u2.name, ' ', u2.surname) AS customer_name, "+
           "tc.process_date AS confirm_date, " +
           "tc.confirm_type AS confirm_type," +
           " t.id AS task_id, "+
           "CONCAT(up.name, ' ', up.surname) AS personel_name ," +
           "p.department AS department"+
           "from tasks t left join client c on c.id = t.client_id "+
           "left join module_type mt on mt.id=t.module_type "+
           "left join module_type mt2 on mt.id  = mt2.module_type_id "+
           "left join customer_client cct on cct.client_id = c.id "+
           "left join customer cc on cc.id  = cct.customer_id "+
           "left join user u on u.id = t.user_id "+
           "left join user u2 on u2.id = cc.user_id "+
           "left join (select max(id) max_id,task_id "+
           "from tasks_confirmation "+
           "group by task_id) t_max on (t_max.task_id=t.id) "+
           "left join tasks_confirmation tc ON (tc.id = t_max.max_id) "+
           "left join personel p on p.id = tc.personel_id "+
           "left join user up on up.id = p.user_id where mt.module_type_id= (Select id mtt from module_type mtt where mtt.module_type_enum=:moduleType) "+
           "and( c.client_type like concat('%',:search ,'%') or c.aggrement_type like concat('%',:search,'%') " +
           "or ( tc.confirm_type like concat('%',:search ,'%') or tc.process_date like concat('%',:search,'%')) " +
           "or ( u.name like concat('%',:search ,'%') or u.surname like concat('%',:search,'%'))" +
           "or ( t.process_date like concat('%',:search,'%'))" +
           "or ( mt.module_type_enum like concat('%',:search,'%')))"
           ,nativeQuery = true)
   List<TaskListResult> searchTaskList(String moduleType , String search);

   @Query(value ="select t.id AS ID," +
           " mt.module_type_enum as module_type," +
           "t.process_date AS process_date, "+
           "CONCAT(u.name, ' ', u.surname) AS user_name, "+
           "(c.id) as client_id," +
           " (u.user_folder) as user_folder," +
           " c.client_type AS client_type, "+
           "c.aggrement_type AS aggrement_type, "+
           "CONCAT(u2.name, ' ', u2.surname) AS customer_name, "+
           "tc.process_date AS confirm_date," +
           " tc.confirm_type AS confirm_type," +
           " t.id AS task_id, "+
           "CONCAT(up.name, ' ', up.surname) AS personel_name," +
           "p.department AS department "+
           "from tasks t left join client c on c.id = t.client_id "+
           "left join module_type mt on mt.id=t.module_type "+
           "left join module_type mt2 on mt.id  = mt2.module_type_id "+
           "left join customer_client cct on cct.client_id = c.id "+
           "left join customer cc on cc.id  = cct.customer_id "+
           "left join user u on u.id = t.user_id "+
           "left join user u2 on u2.id = cc.user_id "+
           "left join (select max(id) max_id,task_id "+
           "from tasks_confirmation "+
           "group by task_id) t_max on (t_max.task_id=t.id) "+
           "left join tasks_confirmation tc ON (tc.id = t_max.max_id) "+
           "left join personel p on p.id = tc.personel_id "+
           "left join user up on up.id = p.user_id where mt.module_type_id= (Select id mtt from module_type mtt where mtt.module_type_enum=:moduleType) "+
           "and ( :confirmTypeClick is null or tc.confirm_type like concat('%',:confirmTypeClick ,'%') " +
           "and ( :moduleTypeClick is null or mt.module_type_enum like concat('%',:moduleTypeClick,'%')) )"
           ,nativeQuery = true)
   List<TaskListResult> searchModuleTaskList(String moduleType , String moduleTypeClick , String confirmTypeClick);

   //***************


   @Query(value = " select t.id AS ID, " +
           "           mt.module_type_enum as module_type," +
           "           t.process_date AS process_date, " +
           "           CONCAT(u.name, ' ', u.surname) AS user_name, " +
           "           (c.id) as client_id, " +
           "           (u.user_folder) as user_folder, " +
           "           c.client_type AS client_type, " +
           "           c.aggrement_type AS aggrement_type, " +
           "           CONCAT(u2.name, ' ', u2.surname) AS customer_name, " +
           "           tc.process_date AS confirm_date, " +
           "           tc.confirm_type AS confirm_type," +
           "           t.id AS task_id, " +
           "           CONCAT(up.name, ' ', up.surname) AS personel_name," +
           "           p.department AS department " +
           "           from tasks t left join client c on c.id = t.client_id " +
           "           left join module_type mt on mt.id=t.module_type " +
           "           left join module_type mt2 on mt.id  = mt2.module_type_id " +
           "           left join customer_client cct on cct.client_id = c.id " +
           "           left join customer cc on cc.id  = cct.customer_id " +
           "           left join user u on u.id = t.user_id " +
           "           left join user u2 on u2.id = cc.user_id " +
           "           left join (select max(id) max_id,task_id " +
           "           from tasks_confirmation " +
           "           group by task_id) t_max on (t_max.task_id=t.id) " +
           "           left join tasks_confirmation tc ON (tc.id = t_max.max_id) " +
           "           left join personel p on p.id = tc.personel_id " +
           "           left join user up on up.id = p.user_id where mt.module_type_id= (Select id mtt from module_type mtt where mtt.module_type_enum=:moduleType) " +
           "           and ( c.client_type IN :clientTypes) " +
           "           and (c.aggrement_type IN :aggrementTypes) " +
           "and (p.department is null or p.department in :departments)" +
           "and  (p.id is null or :personelId is null or p.id = :personelId)" +
           "           and ( (u.name like concat('%',:search ,'%') or u.surname like concat('%',:search,'%')) " +
           "           or (u2.name like concat('%',:search, '%') or u2.surname like concat('%',:search, '%')) " +
           "           )" +
           "            and (coalesce(tc.confirm_type, 'PENDING') IN :confirmType)" +
           "            and ( mt.module_type_enum IN :module)" +
           "            and (:orderStartDate is null or (t.process_date >= :orderStartDate)) " +
           "            and (:orderEndDate is null or (t.process_date <= :orderEndDate))" +
           "and (:startDate is null or (tc.process_date >= :startDate))" +
           "and (:endDate is null or (tc.process_date <= :endDate))", nativeQuery = true)
   List<TaskListResult> findByFilter(String moduleType, List<String> module, List<String> confirmType, String search, LocalDate startDate, LocalDate endDate, List<String> clientTypes, List<String> aggrementTypes, LocalDate orderStartDate, LocalDate orderEndDate, List<String> departments, Long personelId);



   @Query(value = " select t.id AS ID," +
           "           mt.module_type_enum as module_type," +
           "           t.process_date AS process_date, " +
           "           CONCAT(u.name, ' ', u.surname) AS user_name, " +
           "           (c.id) as client_id, (u.user_folder) as user_folder, " +
           "           c.client_type AS client_type, " +
           "           c.aggrement_type AS aggrement_type, " +
           "           CONCAT(u2.name, ' ', u2.surname) AS customer_name, " +
           "           tc.process_date AS confirm_date, " +
           "           tc.confirm_type AS confirm_type," +
           "           t.id AS task_id, " +
           "           CONCAT(up.name, ' ', up.surname) AS personel_name," +
           "           p.department AS department " +
           "           from tasks t left join client c on c.id = t.client_id " +
           "           left join module_type mt on mt.id=t.module_type " +
           "           left join module_type mt2 on mt.id  = mt2.module_type_id " +
           "           left join customer_client cct on cct.client_id = c.id " +
           "           left join customer cc on cc.id  = cct.customer_id " +
           "           left join user u on u.id = t.user_id " +
           "           left join user u2 on u2.id = cc.user_id " +
           "           left join (select max(id) max_id,task_id " +
           "           from tasks_confirmation " +
           "           group by task_id) t_max on (t_max.task_id=t.id) " +
           "           left join tasks_confirmation tc ON (tc.id = t_max.max_id) " +
           "           left join personel p on p.id = tc.personel_id " +
           "           left join user up on up.id = p.user_id where mt.module_type_id IN (Select id mtt from module_type mtt where mtt.module_type_enum IN :moduleTypeList) " +
           "           and ( c.client_type IN :clientTypes) " +
           "           and (c.aggrement_type IN :aggrementTypes) " +
           "and (p.department is null or p.department in :departments)" +
           "and  (p.id is null or :personelId is null or p.id = :personelId)" +
           "           and ( (u.name like concat('%',:search ,'%') or u.surname like concat('%',:search,'%')) " +
           "           or (u2.name like concat('%',:search, '%') or u2.surname like concat('%',:search, '%')))" +
           "            and (coalesce(tc.confirm_type, 'PENDING') IN :confirmType)" +
           "            and ( mt.module_type_enum IN :module) " +
           "            and (:startDate is null or (t.process_date > :startDate or t.process_date = :startDate)) " +
           "            and (:endDate is null or (t.process_date < :endDate or t.process_date = :endDate))" +
           "and (:orderStartDate is null or (tc.process_date >= :orderStartDate or tc.process_date =:orderStartDate))" +
           "and (:orderEndDate is null or (tc.process_date <= :orderEndDate or tc.process_date = :orderEndDate))", nativeQuery = true)
   List<TaskListResult> findByFilterAdminSupport(List<String> moduleTypeList, List<String> module, List<String> confirmType, String search, LocalDate startDate, LocalDate endDate, List<String> clientTypes, List<String> aggrementTypes, LocalDate orderStartDate, LocalDate orderEndDate, List<String> departments, Long personelId);

   @Query(value ="select t.id AS ID," +
           " mt.module_type_enum as module_type," +
           "t.process_date AS process_date, "+
           "CONCAT(u.name, ' ', u.surname) AS user_name, "+
           "(c.id) as client_id, " +
           "(u.user_folder) as user_folder, " +
           "c.client_type AS client_type, "+
           "c.aggrement_type AS aggrement_type, "+
           "CONCAT(u2.name, ' ', u2.surname) AS customer_name, "+
           "tc.process_date AS confirm_date, " +
           "tc.confirm_type AS confirm_type, "+
           "CONCAT(up.name, ' ', up.surname) AS personel_name," +
           "t.id AS task_id," +
           "p.department AS department "+
           "from tasks t left join client c on c.id = t.client_id "+
           "left join module_type mt on mt.id=t.module_type "+
           "left join module_type mt2 on mt.id  = mt2.module_type_id "+
           "left join customer_client cct on cct.client_id = c.id "+
           "left join customer cc on cc.id  = cct.customer_id "+
           "left join user u on u.id = t.user_id "+
           "left join user u2 on u2.id = cc.user_id "+
           "left join (select max(id) max_id,task_id "+
           "from tasks_confirmation "+
           "group by task_id) t_max on (t_max.task_id=t.id) "+
           "left join tasks_confirmation tc ON (tc.id = t_max.max_id) "+
           "left join personel p on p.id = tc.personel_id "+
           "left join user up on up.id = p.user_id where c.id=:id and tc.confirm_type='DONE'"
           ,nativeQuery = true)
   List<TaskListResult> findAllByClientId(Long id);


}
