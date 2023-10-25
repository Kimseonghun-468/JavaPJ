package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import java.security.PublicKey;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

//    @Test
//    public void testRegister() {
//        BoardDTO dto = BoardDTO.builder()
//                .title("Test.")
//                .content("Test...")
//                .writerEmail("user55@aaa.com")
//                .build();
//        Long bno = boardService.register(dto);
//    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO(3, 3, null, null);
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

}
