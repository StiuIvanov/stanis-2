package com.example.demo.web;

import com.example.demo.model.binding.PictureBindingModel;
import com.example.demo.model.entity.PictureEntity;
import com.example.demo.model.view.PictureViewModel;
import com.example.demo.service.CloudinaryService;
import com.example.demo.service.ParentService;
import com.example.demo.service.PictureService;
import com.example.demo.service.impl.CloudinaryImage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Controller
public class PicturesController {

    private final CloudinaryService cloudinaryService;
    private final PictureService pictureService;
    private final ParentService parentService;

    public PicturesController(CloudinaryService cloudinaryService,
                              PictureService pictureService,
                              ParentService parentService) {
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
        this.parentService = parentService;
    }

    @GetMapping("/pictures/add")
    public String addPictures() {
        return "add";
    }



    @Transactional
    @PostMapping("pictures/add")
    public String addPicture(PictureBindingModel bindingModel) throws IOException {

        var picture =
                createPictureEntity(bindingModel.getPicture(), bindingModel.getTitle());

        pictureService.saveToDB(picture);
        return "redirect:/pictures/all";
    }

    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);

        return new PictureEntity()
                .setPublicId(uploaded.getPublicId())
                .setTitle(title)
                .setUrl(uploaded.getUrl());
    }

    @GetMapping("/pictures/all")
    public String all(Model model) {
        List<PictureViewModel> pictures = pictureService.findAllPictures();

        model.addAttribute("pictures", pictures);

        return "all";
    }

    @Transactional
    @DeleteMapping("/pictures/delete")
    public String delete(@RequestParam("public_id") String publicId) {
        if (cloudinaryService.delete(publicId)) {
            pictureService.deleteByPublicId(publicId);
        }
        return "redirect:/pictures/all";
    }


    @DeleteMapping("/pictures/info/{id}")
    public void deleteParent(@PathVariable("id") String publicId) {
        parentService.deleteParentById(publicId);
    }


    @PreAuthorize("isAdmin()")
    @GetMapping("/gallery")
    public String gallery(){
//        model.addAttribute("gallery");
        return "activities-info-admin-panel";
    }


}
