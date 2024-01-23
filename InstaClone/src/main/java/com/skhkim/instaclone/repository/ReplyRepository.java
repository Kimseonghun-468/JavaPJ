package com.skhkim.instaclone.repository;

import com.skhkim.instaclone.entity.Post;
import com.skhkim.instaclone.entity.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @EntityGraph(attributePaths = {"clubMember"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Reply> findByPost(Post post);
}
