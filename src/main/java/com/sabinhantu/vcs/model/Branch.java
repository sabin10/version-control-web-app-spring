package com.sabinhantu.vcs.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.SortedSet;
import java.util.TreeSet;

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
    @OrderBy("id")
    private SortedSet<Commit> commits;

    @ManyToMany
    @JoinTable(name = "branch_dbfile",
        joinColumns = {@JoinColumn(name = "branch_id")},
        inverseJoinColumns = {@JoinColumn(name = "dbfile_id")})
    @OrderBy("id")
    private SortedSet<DBFile> files;

    public Branch() {
        commits = new TreeSet<>();
        files = new TreeSet<>();
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

    public SortedSet<Commit> getCommits() {
        return commits;
    }

    public void setCommits(SortedSet<Commit> commits) {
        this.commits = commits;
    }

    public SortedSet<DBFile> getFiles() {
        return files;
    }

    public void setFiles(SortedSet<DBFile> files) {
        this.files = files;
    }

    public void addFile(DBFile file) {
        this.files.add(file);
//        file.setBranch(this);
        file.getBranches().add(this);
    }

    public void removeFile(DBFile file) {
        this.files.remove(file);
        file.getBranches().remove(this);
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
