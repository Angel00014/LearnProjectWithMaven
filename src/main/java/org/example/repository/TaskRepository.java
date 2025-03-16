package org.example.repository;

import org.example.model.TaskModelList;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TaskModelList.TaskModelWithId> getAllTasksFromDb(Integer limit){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskModelList.TaskModelWithId> criteriaQuery = criteriaBuilder.createQuery(TaskModelList.TaskModelWithId.class);
        Root<TaskModelList.TaskModelWithId> root = criteriaQuery.from(TaskModelList.TaskModelWithId.class);
        criteriaQuery.select(root);

        TypedQuery<TaskModelList.TaskModelWithId> query = entityManager.createQuery(criteriaQuery);

        query.setMaxResults(limit);

        return query.getResultList();

    }

    public TaskModelList.TaskModelWithId getOneTaskFromDb(int id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskModelList.TaskModelWithId> criteriaQuery = criteriaBuilder.createQuery(TaskModelList.TaskModelWithId.class);
        Root<TaskModelList.TaskModelWithId> root = criteriaQuery.from(TaskModelList.TaskModelWithId.class);

        Predicate idPredicate = criteriaBuilder.equal(root.get("id"), id);

        criteriaQuery.select(root).where(idPredicate);

        TypedQuery<TaskModelList.TaskModelWithId> query = entityManager.createQuery(criteriaQuery);

        return query.getSingleResult();
    }

    @Transactional
    public TaskModelList.TaskModelWithId createTaskFromDb(String name, LocalDateTime datetime, String timeBefore){
        TaskModelList.TaskModelWithId newTask = new TaskModelList.TaskModelWithId();

        newTask.setName(name);
        newTask.setDateTime(datetime);
        newTask.setTimeBefore(timeBefore);

        entityManager.persist(newTask);
        entityManager.flush();

        return newTask;
    }

}
