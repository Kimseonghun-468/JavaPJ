package com.skhkim.instaclone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "roleSet")
public class ClubMember extends BaseEntity{
    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

//    @OneToMany(mappedBy = "clubMemberUser")
//    private List<FriendShip> friendshipList = new ArrayList<>();

    @OneToOne(mappedBy = "clubMember")
    private ProfileImage profileImage;

    public void addMemberRole(ClubMemberRole clubMemberRole){
        roleSet.add(clubMemberRole);
    }

    public void changePassword(String password){
        this.password = password;
    }
}
