package com.example.mobileappserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasktype")
@NoArgsConstructor
public class Tasktype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_type_id", nullable = false)
    private Integer id;

    public Tasktype(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    @Column(name = "task_type_name", length = Integer.MAX_VALUE)
    private String taskTypeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

}