package com.skhkim.instaclone.repository.querydsl;

import com.skhkim.instaclone.entity.ClubMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface ClubMemberCustom {

    Optional<ClubMember> selectByEmail(String email);

    Optional<ClubMember> selectByEmail(String email, boolean social);

    ClubMember selectByName(String userName);

    List<ClubMember> selectByIds(List<Long> ids);

    List<ClubMember> selectByNames(List<String> names);

    boolean signValidation(String name, String email);

    Slice<ClubMember> selectSearchUserList(Pageable pageable, String searchTerm, Long userId);

    void updateByName(String changeName, String name);

    void updateByPassward(String passward, String name);
}
