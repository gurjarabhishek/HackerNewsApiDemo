package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Story implements Serializable {
	
	private static final long serialVersionUID = -416508168830792184L;
	@Id
    private Long id;
    private String title;
    private String url;
    private Integer score;

    @Column(name = "submission_time")
    private LocalDateTime submissionTime;

    @Column(name = "user_name")
    private String userName;

    @Transient
    private List<Long> kids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getKids() {
        return kids;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

	@Override
	public String toString() {
		return "Story [id=" + id + ", title=" + title + ", url=" + url + ", score=" + score + ", submissionTime="
				+ submissionTime + ", userName=" + userName + ", kids=" + kids + "]";
	}
    
    
}
