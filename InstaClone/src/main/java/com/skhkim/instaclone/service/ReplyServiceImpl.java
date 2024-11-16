package com.skhkim.instaclone.service;

import com.skhkim.instaclone.chatting.dto.ChatMessageDTO;
import com.skhkim.instaclone.chatting.dto.PageRequestDTO;
import com.skhkim.instaclone.chatting.dto.PageResultDTO;
import com.skhkim.instaclone.chatting.entity.ChatMessage;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.ProfileImage;
import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.repository.ProfileImageRepository;
import com.skhkim.instaclone.repository.ReplyRepository;
import com.skhkim.instaclone.response.ReplyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
//    @Override
//    public Long register(ReplyDTO replyDTO){
//
//        Reply reply = dtoToEntity(replyDTO);
//        replyRepository.save(reply);
//
//        return reply.getRno();
//    }


    @Override
    public ReplyResponse selectReplyList(PageRequestDTO pageRequestDTO, Long pno){
        Pageable pageable = pageRequestDTO.getPageable();
        Slice<Reply> result = replyRepository.selectReplyList(pageable, pno);
        List<ReplyDTO> replyDTOS = result.stream().map(reply -> EntityMapper.entityToDTO(reply)).toList();
        return new ReplyResponse(replyDTOS, result.hasNext());

    }
    @Override
    public void remove(Long replynum){
        replyRepository.deleteById(replynum);
    }
}
