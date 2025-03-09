package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskModelList {
    private List<TaskModelWithId> taskList;

    public TaskModelList(List<TaskModelWithId> taskList) {
        this.taskList = taskList;
    }

    public TaskModelList() {
    }

    @Data
    @MappedSuperclass
    @NoArgsConstructor
    public static class TaskModel {
        @JsonProperty("name")
        protected String name;

        @JsonProperty("dateTime")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        protected LocalDateTime dateTime;

        @JsonProperty("timeBefore")
        protected String timeBefore;

        public TaskModel(String name, LocalDateTime dateTime, String timeBefore) {
            this.name = name;
            this.dateTime = dateTime;
            this.timeBefore = timeBefore;
        }
    }

    @Entity
    @Table(name = "TaskModelWithId")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskModelWithId extends TaskModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("id")
        private int id;

        public TaskModelWithId(int id, String name, LocalDateTime dateTime, String timeBefore) {
            super(name, dateTime, timeBefore);
            this.id = id;
        }
    }
}
