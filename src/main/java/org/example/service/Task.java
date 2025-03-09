package org.example.service;

import org.example.model.TaskModelList;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class Task {

    private static TaskRepository taskRepository = new TaskRepository();

    @Autowired
    public Task(TaskRepository taskRepository) {
        Task.taskRepository = taskRepository;
    }

    public static TaskModelList getAllTasks(Optional<Integer> count){

        List<TaskModelList.TaskModelWithId> taskListFromDb = taskRepository.getAllTasksFromDb(count);

        TaskModelList taskModelList = new TaskModelList();

        taskModelList.setTaskList(taskListFromDb);

        return taskModelList;
    }

    public static TaskModelList.TaskModelWithId getOneTask(int id){

        return taskRepository.getOneTaskFromDb(id);
    }

    public static TaskModelList.TaskModelWithId createTask(String name, LocalDateTime datetime, String timeBefore){

        return taskRepository.createTaskFromDb(name, datetime, timeBefore);

    }

}
