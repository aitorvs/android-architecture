package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.AppExecutors;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class TaskRepository implements TaskDataSource {
    private final LocalTaskDataSource localSource;
    private final AppExecutors executors;

    public TaskRepository(AppExecutors appExecutors, @NonNull LocalTaskDataSource localTaskDataSource) {
        this.localSource = localTaskDataSource;
        this.executors = appExecutors;
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return localSource.getTasks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Task>> getCompletedTasks() {
        return localSource.getCompletedTasks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Task>> getActiveTasks() {
        return localSource.getActiveTasks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void newTask(@NonNull String title, @Nullable String description) {
        executors.diskIO().execute(() -> localSource.newTask(title, description));
    }

    @Override
    public void deleteCompletedTasks() {
        executors.diskIO().execute(localSource::deleteCompletedTasks);
    }

    @Override
    public void deleteAll() {
        localSource.deleteAll();
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.deleteTask(task));
    }

    @Override
    public void completeTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.completeTask(task));
    }

    @Override
    public void activateTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.activateTask(task));
    }

    @Override
    public void updateTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.updateTask(task));
    }

    @Override
    public Maybe<Task> getTaskById(@NonNull String id) {
        return localSource.getTaskById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
