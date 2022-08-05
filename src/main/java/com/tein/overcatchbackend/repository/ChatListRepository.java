package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.model.ChatList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatListRepository extends JpaRepository<ChatList, Long> {

   /* @Query(value ="select * from chat_list u " +
        "inner join chat c on c.chat_list_id=u.id " +
        "where u.user_id = ?1 and chat_list_id = ?2",nativeQuery = true)
    ChatList chatDetailByChatID(Long id1, Long id2);

    @Query(value ="select e from ChatList e where e.user.id = ?1")
    List<ChatList> chatList(Long id);

    @Query(value ="select * from chat_list u where u.user_id = ?1",nativeQuery = true)
    List<ChatList> chatListByUser(Long id);

*/
   @Query(value ="select * from chat_list u " +
       "inner join user_chat_list uc on uc.chat_list_id=u.id  " +
       "where uc.chat_list_id = ?1 ",nativeQuery = true)
   Optional<ChatList> chatListById(Long id1);


  /*  @Query(value ="select * from chat_list u where u.user_id = ?1 and u.contact_id = ?2",nativeQuery = true)
    List<ChatList> chatListByUserContact(Long id1,Long id2);*/
}


/*
select * from overcatch.chat_list u
    inner join overcatch.chat c on c.chat_list_id=u.id
    where u.user_id=17589;
*/

