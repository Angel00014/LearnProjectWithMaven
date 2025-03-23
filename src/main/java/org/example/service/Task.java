package org.example.service;

import org.example.model.TaskModelList;
import org.example.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Task {

    private static TaskRepository taskRepository = new TaskRepository();

    @Autowired
    public Task(TaskRepository taskRepository) {
        Task.taskRepository = taskRepository;
    }

    public static TaskModelList getAllTasks(Integer limit){

        List<TaskModelList.TaskModelWithId> taskListFromDb = taskRepository.getAllTasksFromDb(limit);

        TaskModelList taskModelList = new TaskModelList();

        taskModelList.setTaskList(taskListFromDb);

        return taskModelList;
    }

    public static TaskModelList.TaskModelWithId getOneTask(int id){
        return taskRepository.getOneTaskFromDb(id);
    }

    public static TaskModelList.TaskModelWithId createTask(String name, LocalDateTime datetime, String timeBefore){
        return taskRepository.createTaskToDb(name, datetime, timeBefore);
    }

    public static TaskModelList.TaskModelWithId fullUpdateTask(TaskModelList.TaskModelWithId task){
        return taskRepository.fullUpdateTaskToDb(task);
    }

    public static void deleteTask(TaskModelList.TaskModelWithId task){
        taskRepository.deleteTask(task);
    }

}
