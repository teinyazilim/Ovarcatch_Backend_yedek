package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.ChatListResult;
import com.tein.overcatchbackend.domain.dto.ChatDTO;
import com.tein.overcatchbackend.domain.dto.ChatListDTO;
import com.tein.overcatchbackend.domain.dto.ChatMessage;
import com.tein.overcatchbackend.domain.dto.UserDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.Chat;
import com.tein.overcatchbackend.domain.model.ChatList;
import com.tein.overcatchbackend.domain.model.Roles;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.domain.vm.ApiResponse;
import com.tein.overcatchbackend.domain.vm.OnlineUser;
import com.tein.overcatchbackend.mapper.ChatListMapper;
import com.tein.overcatchbackend.mapper.ChatMapper;
import com.tein.overcatchbackend.mapper.UserMapper;
import com.tein.overcatchbackend.repository.*;
import com.tein.overcatchbackend.security.SecurityUtils;
import com.tein.overcatchbackend.service.CurrentUserService;
import com.tein.overcatchbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatResource {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final ChatListRepository chatListRepository;
    private final ChatListMapper chatListMapper;
    private final ChatMapper chatMapper;
    private final CurrentUserService currentUserService;
    private final ChatRepository chatRepository;
    private final RedisRepository redisRepository;
    private final ChatListUserRepository chatListUserRepository;

    //@Qualifier("sessionRegistry")
    private final SessionRegistry sessionRegistry;

   /* @RequestMapping(value="/list", method = RequestMethod.GET)
    public  List<ChatListDTO>  getUserChatList (){
        List<ChatList> chatList= chatListRepository.chatList(currentUserService.getCurrentUser().getId());
        return chatListMapper.toDto(chatList);
    }

    @RequestMapping(value="/list1", method = RequestMethod.GET)
    public  List<ChatListDTO>  getUserChatList1 (){
        List<ChatList> chatList= chatListRepository.chatListByUser(currentUserService.getCurrentUser().getId());
        return chatListMapper.toDto(chatList);
    }
*/
    @RequestMapping(value="/get-chat", method = RequestMethod.GET)
    public  ChatListDTO getChatListByUserContact (@RequestParam("userId") Long userId,@RequestParam("contactId") Long contactId){

        String strUser1=userId.toString()+","+contactId.toString();
        String strUser2=contactId.toString()+","+userId.toString();

        ChatListResult chatListResult= chatListUserRepository.findChatListByUser(strUser1,strUser2).orElse(new ChatListResult());
        ChatList chatList= chatListRepository.chatListById(chatListResult.getChat_list_id()).orElse(new ChatList());

        return chatListMapper.toDto(chatList);
    }

  /*  @RequestMapping(value="/detail", method = RequestMethod.GET)
    public  ChatListDTO getChatDetailByUserAndChatId (@RequestParam("chatId") Long chatId){
        ChatList chatList= chatListRepository.chatDetailByChatID(currentUserService.getCurrentUser().getId(),chatId);
        return chatListMapper.toDto(chatList);
    }*/

    @RequestMapping(value="/send-message", method = RequestMethod.POST)
    public ChatDTO sendMessage (@RequestBody ChatMessage chatMessage){
        User user= currentUserService.getCurrentUser();
        Chat chat=new Chat();
        ChatList chatList=new ChatList();
        chat.setUserInfo(user);
        chat.setMessage(chatMessage.getMessageText());
        chat.setTime(LocalDateTime.now());

        if(chatMessage.getChatId()!=null) {
            chatList=chatListRepository.findById(chatMessage.getChatId()).get();
            chat.setChatList(chatList);
            chatList.getChat().add(chat);

            chatListRepository.save(chatList);
        }else
        {
            User contact= userRepository.findById(chatMessage.getContactId()).get();
            chatList.setUser(user);
            chatList.setContact(contact);
            chatList.setChat(Arrays.asList(chat));
            chatList.setUsers(Arrays.asList(user,contact));
            chatListRepository.saveAndFlush(chatList);

            //TODO:
//             Set<ChatList> chatLists=chatListFromStrings(chatList.getId());
//            contact.setChatList1(chatLists);
//            userRepository.saveAndFlush(contact);
//
//            user.setChatList1(chatLists);
//            userRepository.saveAndFlush(user);

        }



        return chatMapper.toDto(chat);

    }


    @RequestMapping(value="/user", method = RequestMethod.GET)
    public UserDTO getCurrentUser (){
       String email= SecurityUtils.getCurrentUserLogin().get();
       return userMapper.toDto(userService.getCurrrentUser(email));
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value="/contacts", method = RequestMethod.GET)
    public List<UserDTO> getContacts(){
        List<User> user=userRepository.findAll();
        List<UserDTO> userDTO=userMapper.toDto(user);

        for(int i=0;i<user.size();i++){
            userDTO.get(i).setId(user.get(i).getId());
            OnlineUser onlineUser=redisRepository.findByName(user.get(i).getEmail());
            userDTO.get(i).setOnline(onlineUser==null?"offline":onlineUser.getStatus());
        }
        return userDTO;
    }


    public Set<ChatList> chatListFromStrings(Long id) {
        Set<ChatList> rolesSet = new HashSet<>();
            ChatList chatList = chatListRepository.findById(id).orElseThrow(() -> new AppException("Chat not."));
            ChatList role = null;
            if (chatList != null) {
                role = chatList;
            } else {
                new Exception("Chat Not Found");
            }
            rolesSet.add(role);

        return rolesSet;
    }

}
