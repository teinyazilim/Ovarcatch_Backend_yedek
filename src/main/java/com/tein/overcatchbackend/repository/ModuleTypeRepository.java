package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleTypeRepository extends JpaRepository<ModuleType, Long>{

//    ModuleType findModuleTypeByModuleTypeEnum(ModuleTypeEnum moduleTypeEnum);

    @Query(value ="select * from module_type mt "+
            "where mt.module_type_enum=:moduleType ",nativeQuery = true)
    ModuleType findByModuleTypeEnum1(String moduleType);

    @Query( value =  "select * from module_type " +
            " where module_type_id = (select id from module_type where module_type_enum = :moduleType) " +
            " and id != (select id from module_type where module_type_enum = :moduleType )",nativeQuery = true)
    List<ModuleType> getTaskModuleTypeList(String moduleType);

    @Query( value =  "select * from module_type " +
            " where module_type_id IN (select id from module_type where module_type_enum IN :moduleType) " +
            " and id NOT IN (select id from module_type where module_type_enum IN :moduleType and module_type_enum != 'HELP_MODULE' )",nativeQuery = true)
    List<ModuleType> getTaskModuleTypeListAdminSupport(List<String> moduleType);

//    @Query(value ="select ModuleType from ModuleType mt "+
//            "where mt.moduleTypeEnum=:moduleType ")
//    ModuleType test(String moduleType);

//    List<ModuleType> findModuleTypesByModuleTypeEnum(ModuleTypeEnum moduleTypeEnum);
}