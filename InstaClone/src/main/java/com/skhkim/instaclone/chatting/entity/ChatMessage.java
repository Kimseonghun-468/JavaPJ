package com.skhkim.instaclone.chatting.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@ToString


@Data
@Document(collection = "chat_message") // MongoDB에서의 컬렉션 이름
@Builder
@Getter
@ToString
public class ChatMessage{

    @Id
    private String id; // MongoDB에서는 ObjectID가 주로 사용되므로 String으로 매핑
    private Long roomId;
    @Field("sender_email") // 필드명을 명시적으로 지정 (snake_case)
    private String senderEmail;
    private String inviteNames;
    private String inviterName;

    private String content;
    @Field("read_status")
    private Long readStatus;
    @Field("regdate")
    private LocalDateTime regDate;



//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cid;
//    private Long roomId;
//    private String senderEmail;
//    private String content;
//    private Long readStatus;
//    @CreatedDate
//    @Column(name = "regdate", updatable = false)
//    private LocalDateTime regDate;


}