package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.ResponseModel;
import org.example.model.TaskModelList;
import org.example.service.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
        else {
            if (limit > 200) {
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
    public ResponseEntity<?> getOneTask(@PathVariable int id) throws Exception{
        try {
            TaskModelList.TaskModelWithId task = Task.getOneTask(id);
            if (task!=null){
                return ResponseEntity.status(HttpStatus.OK).body(task);
            }else {
                response.setMessage("Задача не найдена");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e){
            response.setMessage("Ошибка выполнения запроса: " + e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
        }
    }

    @Operation(summary = "Создание задачи")
    @PostMapping(value = "/task", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody TaskModelList.TaskModel taskModel) {
        try {
            TaskModelList.TaskModelWithId newTask = Task.createTask(taskModel.getName(), taskModel.getDateTime(), taskModel.getTimeBefore());
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        }catch (Exception e){
            response.setMessage("Ошибка выполнения запроса: " + e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
        }

    }

    @Operation(summary = "Полное обновление задачи")
    @PutMapping("/task")
    public ResponseEntity<?> putUpdateTask(@RequestBody TaskModelList.TaskModelWithId task){
        try {
            TaskModelList.TaskModelWithId updateTask = Task.fullUpdateTask(task);
            return ResponseEntity.status(HttpStatus.OK).body(updateTask);
        } catch (Exception e){
            response.setMessage("Ошибка выполнения запроса: " + e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
        }
    }

    @Operation(summary = "Частичное обновление задачи")
    @PatchMapping("/task")
    public TaskModelList patchUpdateTask(String id){

        return new TaskModelList();
    }

    @Operation(summary = "Удаление задачи")
    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteUpdateTask(@PathVariable int id){
        try {
            TaskModelList.TaskModelWithId task = Task.getOneTask(id);
            if (task==null){
                response.setMessage("Задача не найдена");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                Task.deleteTask(task);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }catch (Exception e){
            response.setMessage("Ошибка выполнения запроса: " + e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
        }
    }
}
