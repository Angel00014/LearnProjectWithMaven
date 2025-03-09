package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.ResponseModel;
import org.example.model.TaskModelList;
import org.example.service.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Operation(summary = "Получение всего списка задач")
    @GetMapping("/tasks")
    public TaskModelList getAllTasks(Optional<Integer> count) throws Exception{

        if (!count.isPresent()){
            count = Optional.of(200);
        }
        else{
            if (count.get() > 200){
                throw new Exception("Нельзя запросить больше 200 записей");
            }
        }
        return Task.getAllTasks(count);
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
            response.setStatusCode(200);
            response.setContent(newTask);
            return response;
        }catch (Exception e){
            response.setStatusCode(500);
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
