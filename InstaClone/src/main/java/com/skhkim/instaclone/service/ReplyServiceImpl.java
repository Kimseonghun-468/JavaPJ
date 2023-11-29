package com.skhkim.instaclone.service;

import com.skhkim.instaclone.dto.ReplyDTO;
import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import com.skhkim.instaclone.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    @Override
    public Long register(ReplyDTO replyDTO){

        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);

        return reply.getRno();
    }
    @Override
    public List<ReplyDTO> getListOfPost(Long pno){
        Post post = Post.builder().pno(pno).build();
        List<Reply> result = replyRepository.findByPost(post);
        return result.stream().map(postReview -> entityToDTO(postReview)).collect(Collectors.toList());
    }


}
