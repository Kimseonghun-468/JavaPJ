package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO replyDTO);
    void remove(Long bno);

    default Reply dtoToEntity(ReplyDTO replyDTO){
        Board board = Board.builder().bno(replyDTO.getBno()).build();
        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replayer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplayer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }
}
