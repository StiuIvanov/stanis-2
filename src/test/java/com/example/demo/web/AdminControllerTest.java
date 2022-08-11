package com.example.demo.web;

import com.example.demo.model.entity.ActivityEntity;
import com.example.demo.model.entity.ChildEntity;
import com.example.demo.model.entity.enums.ActivityEnum;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.ChildRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    private ChildEntity child1;
    private ChildEntity child2;
    private ChildEntity child3;
    private ChildEntity child4;
    private ChildEntity child5;
    private static final ActivityEnum FOOTBALL_ACTIVITY_ENUM = ActivityEnum.Football;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        childRepository.deleteAll();
        activityRepository.deleteAll();
        initChildren();


    }

    private void initChildren() {
        ActivityEntity footballActivity = new ActivityEntity().setName(FOOTBALL_ACTIVITY_ENUM);
        activityRepository.save(footballActivity);


        child1 = new ChildEntity()
                .setName("Test1")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child1);

        child2 = new ChildEntity()
                .setName("Test2")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child2);

        child3 = new ChildEntity()
                .setName("Test3")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child3);

        child4 = new ChildEntity()
                .setName("Test4")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child4);

        child5 = new ChildEntity()
                .setName("Test5")
                .setAge(1)
                .setBirthdate(LocalDate.now())
                .setActivities(Set.of(footballActivity));
        childRepository.save(child5);

    }

    @AfterEach
    void tearDown() {
        childRepository.deleteAll();
        activityRepository.deleteAll();
    }

    @Test
    void getAllActivitiesShouldRedirectStatus302() throws Exception {
        mockMvc.perform(get("/admin/get-all"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllActivitiesOnlyAdminCanViewSoShouldReturnStatus403() throws Exception {
        mockMvc.perform(get("/admin/get-all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void getAllActivitiesShouldReturnCorrectData() throws Exception {
        mockMvc.perform(get("/admin/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0].name", is(child1.getName())))
                .andExpect(jsonPath("$.[1].name", is(child2.getName())))
                .andExpect(jsonPath("$.[2].name", is(child3.getName())))
                .andExpect(jsonPath("$.[3].name", is(child4.getName())))
                .andExpect(jsonPath("$.[4].name", is(child5.getName())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    void sortActivities() throws Exception {
        mockMvc.perform(get("/admin/sort-activity/" + FOOTBALL_ACTIVITY_ENUM.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(FOOTBALL_ACTIVITY_ENUM.name())))
                .andExpect(jsonPath("$.students[0].name", is(child1.getName())))
                .andExpect(jsonPath("$.students[1].name", is(child2.getName())))
                .andExpect(jsonPath("$.students[2].name", is(child3.getName())))
                .andExpect(jsonPath("$.students[3].name", is(child4.getName())))
                .andExpect(jsonPath("$.students[4].name", is(child5.getName())));
    }
}