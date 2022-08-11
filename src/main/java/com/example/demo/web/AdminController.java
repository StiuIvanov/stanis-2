package com.example.demo.web;

import com.example.demo.model.dto.ActivityDTO;
import com.example.demo.model.dto.ChildAndActivitiesDTO;
import com.example.demo.model.dto.ChildDTO;
import com.example.demo.model.dto.ParentDTO;
import com.example.demo.service.ActivityService;
import com.example.demo.service.ChildService;
import com.example.demo.service.ParentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("isAdmin()")
@RequestMapping("/admin")
public class AdminController {
    private final ActivityService activityService;
    private final ChildService childService;
    private final ParentService parentService;

    public AdminController(ActivityService activityService,
                           ChildService childService,
                           ParentService parentService) {
        this.activityService = activityService;
        this.childService = childService;
        this.parentService = parentService;
    }

    @GetMapping("/get-all")
    @ResponseBody
    public ResponseEntity<List<ChildAndActivitiesDTO>> getAllActivities() {
        List<ChildAndActivitiesDTO> allChildren = childService.getAllChildren();

        return ResponseEntity.ok(allChildren);
    }

    @GetMapping("/sort-activity/{name}")
    @ResponseBody
    public ResponseEntity<ActivityDTO> sortActivities(@PathVariable("name") String name) {
        ActivityDTO activityByName = activityService.getActivityByName(name);

        return ResponseEntity.ok(activityByName);
    }


    @GetMapping("/parents-info")
    public String parentsInfoAdminPanel(){
        return "parents-admin-panel";
    }

    @GetMapping("/get-parents")
    @ResponseBody
    public ResponseEntity<List<ParentDTO>> getAllParent(){
        List<ParentDTO> allParents = parentService.getAllParents();

        return ResponseEntity.ok(allParents);
    }

    @GetMapping("/children-info/{username}")
    public String childrenInfoAdminPanel(@PathVariable String username,
                                         Model model){
        List<ChildDTO> children = parentService.getChildren(username);

        model.addAttribute("children", children);
        return "children-admin-panel";
    }

    @DeleteMapping("/delete/child/{id}")
    public String deleteChild(@PathVariable Long id) {
        childService.deleteChildById(id);

        return "redirect:/admin/parents-info";
    }

    // TODO: 12/13/2021 not used
    @DeleteMapping("/activities/remove/parent/{id}")
    public void deleteParentById(@PathVariable String id){

        System.out.println("SUCCESS!");
    }
}
