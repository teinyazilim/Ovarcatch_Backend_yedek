package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.BankMaster;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface BankMasterRepository extends JpaRepository<BankMaster, Long> {


        @Query(value ="select * from bank_master i " +
                "where i.is_active =1",
                nativeQuery = true)
        List<BankMaster> findAllActive();

        @Modifying
        @Transactional
        @Query(value = "update BankMaster b set b.isActive =0 " +
                "where b.id= :id ")
        void deleteBankMaster(Long id);

        @Query(value = "select count(b.bank_name) from overcatch.bank b where b.bank_name=:bank_name",
        nativeQuery = true)
        Integer controlBankMasterForDelete(String bank_name);

}

