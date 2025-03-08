package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskModelList {
    private List<TaskModel> taskList;

    public static class TaskModel {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("DateTme")
        private Date dateTme;
        @JsonProperty("TimeBefore")
        private String timeBefore;
    }

    public static class TaskModelWithId extends TaskModel{
        @JsonProperty("id")
        private int id;

    }


}




