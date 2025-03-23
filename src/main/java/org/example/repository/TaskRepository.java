package org.example.repository;

import org.example.model.TaskModelList;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Transactional
    public TaskModelList.TaskModelWithId createTaskToDb(String name, LocalDateTime datetime, String timeBefore){
        TaskModelList.TaskModelWithId newTask = new TaskModelList.TaskModelWithId();

        newTask.setName(name);
        newTask.setDateTime(datetime);
        newTask.setTimeBefore(timeBefore);

        entityManager.persist(newTask);
        entityManager.flush();

        return newTask;
    }

    @Transactional
    public TaskModelList.TaskModelWithId fullUpdateTaskToDb(TaskModelList.TaskModelWithId task){
        TaskModelList.TaskModelWithId findTask = getOneTaskFromDb(task.getId());

        if (findTask.getId() == task.getId()){

            findTask.setName(task.getName());
            findTask.setDateTime(task.getDateTime());
            findTask.setTimeBefore(task.getTimeBefore());

            entityManager.persist(findTask);
            entityManager.flush();

            return findTask;

        }else {
            return null;
        }
    }

    @Transactional
    public void deleteTask(TaskModelList.TaskModelWithId task){
        try {
            entityManager.remove(task);
        }catch (Exception e){
            System.out.println(e);
        }
    }

}
