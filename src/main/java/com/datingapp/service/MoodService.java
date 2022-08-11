package com.datingapp.service;

import com.datingapp.model.entity.Mood;
import com.datingapp.model.entity.MoodsEnum;

public interface MoodService {

    void initMoods();

    Mood findMood(MoodsEnum style);

    Mood findStyleByStyleName(MoodsEnum styleName);
}
