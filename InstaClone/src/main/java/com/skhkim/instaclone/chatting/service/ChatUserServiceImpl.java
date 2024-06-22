package com.skhkim.instaclone.chatting.service;


import com.skhkim.instaclone.chatting.entity.ChatRoom;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.dto.ProfileImageDTO;
import com.skhkim.instaclone.dto.ProfilePageRequestDTO;
import com.skhkim.instaclone.dto.ProfilePageResultDTO;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.entity.ProfileImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatUserServiceImpl implements ChatUserService {

    private final ChatUserRepository chatUserRepository;
    @Override
    public void register(String userEmail, Long roomId){
        ChatUser chatUser = ChatUser.builder()
                .chatRoom(ChatRoom.builder().roomId(roomId).build())
                .member(ClubMember.builder().email(userEmail).build())
                .disConnect(LocalDateTime.now())
                .build();
        chatUserRepository.save(chatUser);
    }
    @Override
    public void updateDisConnect(Long roomId, String loginEmail){
        ChatUser chatUser = chatUserRepository.getChatUsersByRoomIdAndEmail(roomId, loginEmail);
        chatUser.setDisConnect(LocalDateTime.now());
        chatUserRepository.save(chatUser);
    }

    @Override
    public List<Object[]> getEmailAndName(Long roomId){
        return chatUserRepository.getEmailAndNmaeByRoomId(roomId);
    }

    @Override
    public ProfilePageResultDTO<Map<String, Object>, Object[]>
    getProfileAndUseByLoginNamePage(ProfilePageRequestDTO profilePageRequestDTO, String loginEmail){
        Pageable pageable = profilePageRequestDTO.getPageable();
        Page<Object[]> result = chatUserRepository.getChatroomAndProfileImage(pageable, loginEmail);

        Function<Object[], Map<String, Object>> fn = (arr ->{
            List<Object[]> emailAndProfileList = chatUserRepository.getUserEmailByEmailAndRoomId2(loginEmail,(Long) arr[2]);
            List<ProfileImageDTO> profileImageList = emailAndProfileList.stream().map(emailAndProfile -> {
                if (emailAndProfile[1] != null)
                    return entityToDTOByProfileImage((ProfileImage) emailAndProfile[1]);
                else
                    return null;
            }).toList();
            List<String> nameList = emailAndProfileList.stream().
                    map(emailAndProfile -> (String) emailAndProfile[0]).toList();

            String combinedName = String.join(", ", nameList);
            Map<String, Object> userAndProfileMap = new HashMap<>();
            userAndProfileMap.put("friendName", combinedName);
            userAndProfileMap.put("lastChat", (arr[0]));
            userAndProfileMap.put("lastChatTime", (arr[1]));
            userAndProfileMap.put("roomId", (arr[2]));
            userAndProfileMap.put("profileImage", profileImageList);
            return userAndProfileMap;
        });
        return new ProfilePageResultDTO<>(result, fn);

    }
    @Override
    public void insertChatUser(List<String> userEmails, Long roomId){
        userEmails.forEach(userEmail -> {
            ChatUser chatUser = ChatUser.builder()
                    .member(ClubMember.builder().email(userEmail).build())
                    .chatRoom(ChatRoom.builder().roomId(roomId).build())
                    .disConnect(LocalDateTime.now())
                    .build();
            chatUserRepository.save(chatUser);
        });
    }
    @Override
    public LocalDateTime getDisConnectTime(Long roomId, String loginEmail){
        return chatUserRepository.getDisConnectTimeByRoomIdAndEmail(roomId, loginEmail);
    }
}
