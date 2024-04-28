package com.skhkim.instaclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "clubMember")
public class FriendShip extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ClubMember clubMember;

    private String userEmail;
    private String friendEmail;
    private String userName;
    private String friendName;
    private FriendShipStatus status;
    private boolean isFrom;

    private Long counterpartId;

    public void acceptFriendshipRequest() {
        status = FriendShipStatus.ACCEPT;
    }
    public void setCounterpartId(Long id) {
        counterpartId = id;
    }

}
