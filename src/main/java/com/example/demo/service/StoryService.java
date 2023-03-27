package com.example.demo.service;

import com.example.demo.model.Story;
import com.example.demo.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private static final String HN_BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static final String HN_TOP_STORIES_ENDPOINT = HN_BASE_URL + "topstories.json";
    private static final String HN_ITEM_ENDPOINT = HN_BASE_URL + "item/";

    private final StoryRepository storyRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public StoryService(StoryRepository storyRepository, RestTemplateBuilder restTemplateBuilder) {
        this.storyRepository = storyRepository;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable(value = "topStories")
    public List<Story> getTopStories() {
        Integer[] topStoryIds = restTemplate.getForObject(HN_TOP_STORIES_ENDPOINT, Integer[].class);

        if (topStoryIds == null) {
            return new ArrayList<>();
        }

        return storyRepository.saveAll(fetchTopStories(topStoryIds));
    }

    private List<Story> fetchTopStories(Integer[] topStoryIds) {
        return List.of(topStoryIds).stream()
                .limit(10)
                .map(id -> {
                    String storyUrl = HN_ITEM_ENDPOINT + id + ".json";
                    Story story = restTemplate.getForObject(storyUrl, Story.class);
    
                    if (story != null && story.getSubmissionTime() != null) {
                        LocalDateTime localDateTime = story.getSubmissionTime();
                        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                        long epochSeconds = instant.getEpochSecond();
    
                        story.setSubmissionTime(LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC));
                    }
    
                    return story;
                })
                .collect(Collectors.toList());
    }

    public List<Story> getPastStories() {
        return storyRepository.findAll();
    }

    @CacheEvict(value = "topStories", allEntries = true)
    @Scheduled(fixedRate = 15 * 60 * 1000) // 15 minutes
    public void evictTopStoriesCache() {
        // This method will be executed every 15 minutes to evict the cache
    }
}
