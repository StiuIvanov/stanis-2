package com.datingapp.service;

import com.datingapp.model.entity.Mood;
import com.datingapp.model.entity.MoodsEnum;
import com.datingapp.repo.MoodRepo;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MoodServiceImpl implements MoodService {

    private final MoodRepo repo;

    public MoodServiceImpl(MoodRepo repo) {
        this.repo = repo;
    }

    @Override
    public void initMoods() {
        if (this.repo.count() != 0) {
            return;
        }

        Arrays.stream(MoodsEnum.values())
                .forEach(s -> {
                    Mood mood = new Mood();
                    mood.setMoodName(s);
                    mood.setDescription("...");

                    this.repo.save(mood);
                });

    }

    @Override
    public Mood findMood(MoodsEnum moodsEnum) {
        return this.repo.findByMoodName(moodsEnum).orElseThrow();
    }

    @Override
    public Mood findStyleByStyleName(MoodsEnum styleName) {
        return this.repo.findByMoodName(styleName).orElseThrow();
    }
}
