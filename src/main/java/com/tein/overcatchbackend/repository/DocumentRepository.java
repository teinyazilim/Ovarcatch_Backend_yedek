package com.tein.overcatchbackend.repository;
import com.tein.overcatchbackend.domain.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByUserID(Long userID);

    //veritabanında dosya silme yapılmayıp sadece aktifliği 0 çekildiği için
    //dökümanlardan default olarak null olanlar çekiliyor
    @Query(value="select d from Document d where d.client.id =:userID and d.isActive=:isActive")
    List<Document> findAllByClientIdAndIsActive(Long userID, Boolean isActive);
    Document findByFileName(String fileDescription);


    @Modifying
    @Transactional
    @Query(value ="select 'file_path' from Document i  " +
            "where i.id= :id ")
    void getPathFileById(Long id);

    @Modifying
    @Transactional
    @Query(value ="update Document i set i.isActive =0 " +
            "where i.id= :id ")
    void deleteFileById(Long id);

}
