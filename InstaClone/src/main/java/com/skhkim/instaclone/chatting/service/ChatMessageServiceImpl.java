package com.skhkim.instaclone.chatting.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.chatting.entity.ChatUser;
import com.skhkim.instaclone.chatting.repository.ChatMessageRepository;
import com.skhkim.instaclone.chatting.repository.ChatUserRepository;
import com.skhkim.instaclone.chatting.request.MessageRequest;
import com.skhkim.instaclone.chatting.response.ChatMessageResponse;
import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.entity.ClubMember;
import com.skhkim.instaclone.repository.ClubMemberRepository;
import com.skhkim.instaclone.request.MessagePageRequest;
import com.skhkim.instaclone.service.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatUserRepository chatUserRepository;
    private final ClubMemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public Long register(ChatMessageDTO chatMessageDTO){
        ChatMessage chatMessage = EntityMapper.dtoToEntity(chatMessageDTO);
        chatMessage.setSendUser(ClubMember.builder().id(LoginContext.getClubMember().getUserId()).build());
        chatMessageRepository.save(chatMessage);

        return chatMessage.getCid();
    }
    @Override
    public void updateReadStatus(MessageRequest request){
        Long userId = LoginContext.getClubMember().getUserId();
        ChatUser chatUser = chatUserRepository.select(request.getRoomId(), userId);
        request.setUserId(userId);
        request.setJoinCid(chatUser.getJoinCid());
        request.setLastCid(chatUser.getLastCid());
        chatMessageRepository.updateReadNum(request);
        List<ChatMessage> result = chatMessageRepository.selectChatMessages(request);
        result.forEach(chatMessage -> chatMessage.setReadStatus(chatMessage.getReadStatus() -1));
        chatMessageRepository.saveAll(result);
    }

    @Override
    public ChatMessageResponse selectChatMessages(MessagePageRequest request){
        Long userId = LoginContext.getClubMember().getUserId();
        ChatUser chatUser = chatUserRepository.select(request.getRoomId(), userId);
        request.setLastCid(chatUser.getLastCid());
        request.setJoinCid(chatUser.getJoinCid());
        request.setChatId(chatUser.getChatId());
        Slice<ChatMessage> result = chatMessageRepository.selectChatMessagesPage(request);

        List<ChatMessageDTO> chatMessageDTOS = result.stream().map(chatMessage -> {
            ChatMessageDTO chatMessageDTO = EntityMapper.entityToDTO(chatMessage);

            if (chatMessage.getInvitedUser() != null) {
                String userIds = chatMessage.getInvitedUser().replaceAll("[\\[\\]]", "");
                List<Long> userIdList = Arrays.stream(userIds.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());

                List<ClubMember> clubMembers = memberRepository.selectByIds(userIdList);
                String userNames = clubMembers.stream()
                        .map(ClubMember::getName)
                        .collect(Collectors.joining(", "));

                chatMessageDTO.setInviteNames(userNames);
                chatMessageDTO.setInviterName(chatMessage.getSendUser().getName());
            }

            return chatMessageDTO;
        }).toList();
        return new ChatMessageResponse(chatMessageDTOS, result.hasNext());
    }
    @Override
    public Long getNotReadNum(MessageRequest request){
        Long userId = LoginContext.getClubMember().getUserId();
        ChatUser chatUser = chatUserRepository.select(request.getRoomId(), userId);
        request.setLastCid(chatUser.getLastCid());
        request.setJoinCid(chatUser.getJoinCid());
        return chatMessageRepository.getNotReadNum(request);
    }

}
