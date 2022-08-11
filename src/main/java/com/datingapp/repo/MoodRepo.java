package com.datingapp.repo;

import com.datingapp.model.entity.Mood;
import com.datingapp.model.entity.MoodsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepo extends JpaRepository<Mood, Long> {

    Optional<Mood> findByMoodName(MoodsEnum moodsEnum);

}
