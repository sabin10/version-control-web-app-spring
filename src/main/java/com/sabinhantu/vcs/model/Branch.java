package com.sabinhantu.vcs.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @CreationTimestamp
    private Date createdAt;

    @ManyToMany
    @JoinTable(name = "branch_commit",
        joinColumns = {@JoinColumn(name = "branch_id")},
        inverseJoinColumns = {@JoinColumn(name = "commit_id")})
    private Set<Commit> commits;

    //todo: constructor la care fiecare branch nou creat, copiaza branchul "master"
    public Branch() {
        commits = new HashSet<>();
    }

    public Branch(@NotNull String name) {
        this();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Commit> getCommits() {
        return commits;
    }

    public void setCommits(Set<Commit> commits) {
        this.commits = commits;
    }

    public void addCommit(Commit commit) {
        this.commits.add(commit);
        commit.getBranches().add(this);
    }

    public void removeCommit(Commit commit) {
        this.commits.remove(commit);
        commit.getBranches().remove(this);
    }

}
