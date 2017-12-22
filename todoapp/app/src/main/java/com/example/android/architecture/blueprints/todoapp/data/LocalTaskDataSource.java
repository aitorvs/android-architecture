package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

public final class LocalTaskDataSource implements TaskDataSource {
    private final TaskDao taskDao;

    public LocalTaskDataSource(TodoDatabase todoDatabase) {
        taskDao = todoDatabase.taskDao();
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return taskDao.getTasks().onBackpressureBuffer().toObservable();
    }

    @Override
    public void newTask(@NonNull String title, @Nullable String description) {
        Task task = Task.create(title, description);
        // add the task to the DAO
        taskDao.insert(task);
    }

    @Override public void deleteTask(@NonNull String title) {
        taskDao.deleteTaskByTitle(title);
    }

    @Override
    public void deleteAll() {
        taskDao.deleteAllTasks();
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
