package com.skhkim.instaclone.service;

import com.skhkim.instaclone.context.LoginContext;
import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.repository.ReplyRepository;
import com.skhkim.instaclone.request.ReplyPageRequest;
import com.skhkim.instaclone.response.ReplyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    @Override
    public ReplyDTO register(ReplyDTO replyDTO){
        Reply reply = EntityMapper.dtoToEntity(replyDTO);
        replyRepository.save(reply);
        replyDTO.setRno(reply.getRno());

        return replyDTO;
    }
    @Override
    public boolean remove(Long rno) {

        if(replyRepository.checkValidation(rno, LoginContext.getUserInfo().getUserEmail())) {
            replyRepository.deleteById(rno);
            return true;
        }
        else
            return false;
    }


    @Override
    public ReplyResponse selectReplyList(ReplyPageRequest replyPageRequest, Long pno){
        Pageable pageable = replyPageRequest.getPageable();
        Slice<Reply> result = replyRepository.selectReplyList(pageable, pno);
        List<ReplyDTO> replyDTOS = result.stream().map(reply -> EntityMapper.entityToDTO(reply)).toList();
        return new ReplyResponse(replyDTOS, result.hasNext());
    }
}
