package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByPost(Post post);
    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.post.pno = :pno")
    void deleteByPostPno(Long pno);

    @Query("DELETE FROM FriendShip fs WHERE fs.userName =:userName AND fs.friendName =:friendName")
    void deleteByUserNameAndFriendName(String userName, String friendName);

}
