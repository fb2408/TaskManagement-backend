package com.example.mobileappserver.service;

import com.example.mobileappserver.model.Tasktype;
import com.example.mobileappserver.repository.TaskTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTypeService {

    private final TaskTypeRepository taskTypeRepository;

    public TaskTypeService(TaskTypeRepository taskTypeRepository) {
        this.taskTypeRepository = taskTypeRepository;
    }

    public List<Tasktype> getAllTaskTypes() {
        return taskTypeRepository.findAll();
    }
}
