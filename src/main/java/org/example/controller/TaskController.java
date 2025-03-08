package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.TaskModelList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Operation(summary = "Получение всего списка задач", description = "Возвращает весь список задач ")
    @GetMapping("/tasks")
    public TaskModelList getAllTasks(){

        return new TaskModelList();
    }

}
