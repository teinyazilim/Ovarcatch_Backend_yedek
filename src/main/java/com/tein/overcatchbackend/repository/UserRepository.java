package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findByUsername(String username);

   //User findByUsername(String username);



//    @Query("SELECT t FROM User t WHERE t. like %:username%")
//    List<User> findAllByUsername(@Param("username") String username);

    //User findRolesByUsername(String username);

    @Query(value = "select * from user u where (u.id in :userIds)",nativeQuery = true)
    List<User> findAllByWithoutListDirectorId(List<Integer> userIds);

    List<User> findAllByRoles(String roles);

    @Query(value = "select * from user u where u.user_type=:userType and u.is_deleted=0",nativeQuery = true)
    List<User> findAllByUserType(String userType);

    @Query(value = "select * from user u where u.is_deleted=0",nativeQuery = true)
    List<User> findAllActive();

    User findRolesByEmail(String email);

    @Query(value = "select * from user u where u.email=:email and u.is_deleted=0",nativeQuery = true)
    User findByEmail(String email);

    //Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    //Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value ="update User u set u.isDeleted=1 " +
            "where u.id= :userId ")
    void deleteUser(Long userId);

    @Modifying
    @Transactional
    @Query(value ="update User u set u.isActive=0 " +
            "where u.id= :userId ")
    void changeActive(Long userId);

    @Modifying
    @Transactional
    @Query(value ="update User u set u.isActive=1, u.isPassChanged = 0" +
            "where u.id= :userId ")
    void changeDeActive(Long userId);
}
