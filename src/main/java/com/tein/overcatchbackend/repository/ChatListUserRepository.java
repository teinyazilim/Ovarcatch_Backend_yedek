package com.tein.overcatchbackend.repository;

import com.tein.overcatchbackend.domain.ChatListResult;
import com.tein.overcatchbackend.domain.TaskListResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatListUserRepository extends JpaRepository<ChatListResult, Long> {

   @Query(value ="select chat_list_id, " +
            "GROUP_CONCAT(user_id SEPARATOR ', ') as users "+
            "FROM user_chat_list "+
            "GROUP BY chat_list_id ",
            nativeQuery = true)
   List<ChatListResult> findAllUserChatList();

    @Query(value ="select chat_list_id, " +
        "GROUP_CONCAT(user_id SEPARATOR ',') as users "+
        "FROM user_chat_list "+
        "GROUP BY chat_list_id " +
        //"having GROUP_CONCAT(user_id SEPARATOR ',') in ('1005,1759','1759,1005');" +
        "having GROUP_CONCAT(user_id SEPARATOR ',') in (:user1,:user2)",
        nativeQuery = true)
    Optional<ChatListResult> findChatListByUser(@Param("user1") String user1, @Param("user2") String user2);
}
