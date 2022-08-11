package com.datingapp.controller;

import com.datingapp.model.dto.PostsWithUsernamesDTO;
import com.datingapp.model.dto.SongsByGenreDTO;
import com.datingapp.model.entity.Post;
import com.datingapp.model.entity.User;
import com.datingapp.service.PostServiceImpl;
import com.datingapp.service.UserServiceImpl;
import com.datingapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Set;

@Controller
public class HomeControllerImpl implements HomeController {

    private final LoggedUser loggedUser;
    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    public HomeControllerImpl(LoggedUser loggedUser,
                              PostServiceImpl postService,
                              UserServiceImpl userService) {
        this.loggedUser = loggedUser;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @Override
    public String home(Model model) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }

        User user = userService.findUserById(loggedUser.getId()).orElse(null);

        model.addAttribute("currentUserInfo", user);
        Set<Post> postsFromCurrentUser = this.postService.getPostsFromCurrentUser(this.loggedUser.getId());
        model.addAttribute("userPosts", postsFromCurrentUser);
        Set<PostsWithUsernamesDTO> postsFromOtherUsers = this.postService.getPostsFromOtherUsers(this.loggedUser.getId());
        model.addAttribute("otherUserPosts", postsFromOtherUsers);
        model.addAttribute("user", user);

        return "home";
    }


    @ModelAttribute
    public SongsByGenreDTO songs() {
        return new SongsByGenreDTO();
    }
}
