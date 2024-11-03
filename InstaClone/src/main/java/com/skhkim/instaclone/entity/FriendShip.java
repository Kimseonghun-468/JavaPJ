//package com.skhkim.instaclone.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@ToString(exclude = {"clubMemberUser", "clubMemberFriend"})
//public class FriendShip extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_info")
//    private ClubMember clubMemberUser;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "friend_info")
//    private ClubMember clubMemberFriend;
//    private FriendShipStatus status;
//    private boolean isFrom;
//
//    public void acceptFriendshipRequest() {
//        status = FriendShipStatus.ACCEPT;
//    }
//
//}
