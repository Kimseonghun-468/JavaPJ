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
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProfileImageRepository profileImageRepository;
    @Override
    public Long register(ReplyDTO replyDTO){

        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);

        return reply.getRno();
    }
//    @Override
//    public List<ReplyDTO> getListOfPost(Long pno){
//        Post post = Post.builder().pno(pno).build();
//        List<Reply> result = replyRepository.findByPost(post);
//        return result.stream().map(postReview -> entityToDTO(postReview)).collect(Collectors.toList());
//    }

    @Override
    public PageResultDTO<ReplyDTO, Reply> getListOfPostPage(PageRequestDTO pageRequestDTO, Long pno){
        Pageable pageable = pageRequestDTO.getPageable();
        Function<Reply, ReplyDTO> fn = (arr -> {
           ReplyDTO replyDTO = entityToDTO(arr);
           Optional<ProfileImage> profileImage = profileImageRepository.getProfileImageByUserEmail(replyDTO.getEmail());
           profileImage.ifPresent(image -> replyDTO.setProfileImageUrl(image.getImageURL()));
           return replyDTO;
        });

        Page<Reply> result = replyRepository.findByPost(pageable, pno);


        return new PageResultDTO<>(result, fn);

    }

    @Override
    public void remove(Long replynum){
        replyRepository.deleteById(replynum);
    }
}
