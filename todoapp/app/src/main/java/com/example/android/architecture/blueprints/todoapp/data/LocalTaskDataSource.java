package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

final class LocalTaskDataSource implements TaskDataSource {
    private final TaskDao taskDao;

    LocalTaskDataSource(TodoDatabase todoDatabase) {
        taskDao = todoDatabase.taskDao();
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return taskDao.getTasks().onBackpressureBuffer().toObservable();
    }

    @Override
    public Observable<List<Task>> getCompletedTasks() {
        return taskDao.getCompletedTasks().onBackpressureBuffer().toObservable();
    }

    @Override
    public Observable<List<Task>> getActiveTasks() {
        return taskDao.getActiveTasks().onBackpressureBuffer().toObservable();
    }

    @Override
    public void newTask(@NonNull String title, @Nullable String description) {
        Task task = Task.create(title, description);
        // add the task to the DAO
        taskDao.insert(task);
    }

    @Override
    public void deleteCompletedTasks() {
        taskDao.deleteCompletedTasks();
    }

    @Override
    public void deleteAll() {
        taskDao.deleteAllTasks();
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        taskDao.deleteTask(task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        taskDao.completeTask(task.getId(), true);
    }

    @Override
    public void activateTask(@NonNull Task task) {
        taskDao.completeTask(task.getId(), false);
    }

    @Override
    public void updateTask(@NonNull Task task) {
        // insert should replace
        taskDao.insert(task);
    }

    @Override
    public Maybe<Task> getTaskById(@NonNull String id) {
        return taskDao.findById(id);
    }
}
