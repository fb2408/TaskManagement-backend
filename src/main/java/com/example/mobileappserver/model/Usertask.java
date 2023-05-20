package com.example.mobileappserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usertask")
@NoArgsConstructor
public class Usertask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usertask_id", nullable = false)
    private Integer id;

    @Column(name = "finished", nullable = false)
    private Boolean finished = false;

    @Column(name = "req_redirect", nullable = false)
    private Boolean reqRedirect = false;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_unit_id")
    private OrganizationUnit organizationUnit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Usertask(Boolean finished, Boolean reqRedirect, User user, OrganizationUnit organizationUnit, Task task) {
        this.finished = finished;
        this.reqRedirect = reqRedirect;
        this.user = user;
        this.organizationUnit = organizationUnit;
        this.task = task;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getReqRedirect() {
        return reqRedirect;
    }

    public void setReqRedirect(Boolean reqRedirect) {
        this.reqRedirect = reqRedirect;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}