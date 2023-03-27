package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private static final String HN_BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static final String HN_ITEM_ENDPOINT = HN_BASE_URL + "/item/";

    private final StoryService storyService;
    private final RestTemplate restTemplate;

    @Autowired
    public CommentService(StoryService storyService, RestTemplateBuilder restTemplateBuilder) {
 
        this.storyService = storyService;
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Comment> getCommentsForStory(Long storyId) {
        String storyUrl = HN_ITEM_ENDPOINT + storyId + ".json";
        Story story = restTemplate.getForObject(storyUrl, Story.class);
        if (story == null) {
            return new ArrayList<>();
        }

        List<Comment> comments = fetchCommentsForStory(story);
        comments.forEach(comment -> comment.setStory(story));
        
        return comments;
    }
    private List<Comment> fetchCommentsForStory(Story story) {
        if (story.getKids() == null) {
            return new ArrayList<>();
        }

        return story.getKids().stream()
                .map(id -> {
                    String commentUrl = HN_ITEM_ENDPOINT + id + ".json";
                    Comment comment = restTemplate.getForObject(commentUrl, Comment.class);
                    if (comment != null) {
                        int childCommentsCount = comment.getKids() != null ? comment.getKids().size() : 0;
                        comment.setChildCommentsCount(childCommentsCount);
                    }
                    return comment;
                })
                .filter(Objects::nonNull)
                .sorted((c1, c2) -> c2.getChildCommentsCount() - c1.getChildCommentsCount())
                .limit(10)
                .collect(Collectors.toList());
    }

    
}
