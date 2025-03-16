package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.ResponseModel;
import org.example.model.TaskModelList;
import org.example.service.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final ResponseModel response = new ResponseModel();

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Operation(summary = "Получение всего списка задач")
    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(@RequestParam(required = false) Integer limit) throws Exception{

        if (limit == null){
            limit = 200;
        }
        else{
            if (limit > 200){
                response.setMessage("Параметр limit > 200");
                response.setContent(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            } else if (limit < 1) {
                response.setMessage("Параметр limit < 1");
                response.setContent(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        return ResponseEntity.ok(Task.getAllTasks(limit));
    }

    @Operation(summary = "Получение одной задачи по id")
    @GetMapping("/task/{id}")
    public TaskModelList.TaskModelWithId getOneTask(int id) throws Exception{
        return Task.getOneTask(id);
    }

    @Operation(summary = "Создание задачи")
    @PostMapping("/task")
    public ResponseModel createTask(@RequestBody TaskModelList.TaskModel taskModel) {
        ResponseModel response = new ResponseModel();
        try {
            TaskModelList.TaskModelWithId newTask = Task.createTask(taskModel.getName(), taskModel.getDateTime(), taskModel.getTimeBefore());
            response.setContent(newTask);
            return response;
        }catch (Exception e){
            response.setContent("Ошибка выполнения запроса: " + e);
            return response;
        }

    }

    @Operation(summary = "Полное обновление задачи")
    @PutMapping("/task")
    public TaskModelList putUpdateTask(){

        return new TaskModelList();
    }

    @Operation(summary = "Частичное обновление задачи")
    @PatchMapping("/task")
    public TaskModelList patchUpdateTask(String id){

        return new TaskModelList();
    }

    @Operation(summary = "Удаление задачи")
    @DeleteMapping("/task")
    public TaskModelList deleteUpdateTask(String id){

        return new TaskModelList();
    }
}
