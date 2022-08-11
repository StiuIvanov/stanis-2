package com.datingapp.controller;

import com.datingapp.model.dto.AddPostDTO;
import com.datingapp.util.LoggedUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/posts")
public interface PostController {

    @GetMapping("add-post")
    String addPost();

    @PostMapping("/add-post")
    String addPost(@Valid AddPostDTO addPostDTO, BindingResult result, RedirectAttributes redirectAttributes);

    @GetMapping("like-post/{id}")
    String likePost(@PathVariable Long id);

    @GetMapping("/remove/{id}")
    String removePost(@PathVariable Long id);
}

