package com.datingapp.init;

import com.datingapp.service.MoodService;
import com.datingapp.service.PostServiceImpl;
import com.datingapp.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StylesInit implements CommandLineRunner {

    private final MoodService moodService;
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    public StylesInit(MoodService moodService,
                      UserServiceImpl userService,
                      PostServiceImpl postService) {
        this.moodService = moodService;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void run(String... args) {
        this.userService.initAdmin();
        this.userService.initTest();
        this.moodService.initMoods();
        postService.addTestPosts();

    }
}
