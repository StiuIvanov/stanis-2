package com.datingapp.repo;

import com.datingapp.model.entity.Post;
import com.datingapp.model.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    Set<Post> findByMood(Mood mood);

    Set<Post> findAllByUserId(Long user_id);

    Set<Post> findPostsByUserIdNot(Long user_id);

    //    Optional<Post> findByPerformer(String performer);

}
