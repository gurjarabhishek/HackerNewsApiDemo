package com.example.demo.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String AllUser;
    private Integer childCommentsCount;

    @ManyToOne
    private Story story;

    @Transient
    private List<Long> kids;

    public Comment() {
    }

    public Comment(Long id, String text, String alluser, Integer childCommentsCount, Story story) {
        this.id = id;
        this.text = text;
        this.AllUser = alluser;
        this.childCommentsCount = childCommentsCount;
        this.story = story;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return AllUser;
    }

    public void setUser(String alluser) {
        this.AllUser = alluser;
    }
    
    public List<Long> getKids() {
        return kids;
    }
    
    public void setKids(List<Long> kids) {
        this.kids = kids;
    }
    public Integer getChildCommentsCount() {
        return childCommentsCount;
    }

    public void setChildCommentsCount(Integer childCommentsCount) {
        this.childCommentsCount = childCommentsCount;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", AllUser=" + AllUser + ", childCommentsCount="
				+ childCommentsCount + ", story=" + story + ", kids=" + kids + "]";
	}
    
}
