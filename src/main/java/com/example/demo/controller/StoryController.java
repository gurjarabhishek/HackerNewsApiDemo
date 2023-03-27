package com.example.demo.controller;

import com.example.demo.model.Story;
import com.example.demo.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoryController {

    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/top-stories")
    public List<Story> getTopStories() {
        return storyService.getTopStories();
    }

    @GetMapping("/past-stories")
    public List<Story> getPastStories() {
        return storyService.getPastStories();
    }
}
