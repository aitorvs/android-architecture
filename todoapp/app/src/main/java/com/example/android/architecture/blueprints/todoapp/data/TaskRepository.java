package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.AppExecutors;
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
    public void newTask(@NonNull String title, @Nullable String description) {
        executors.diskIO().execute(() -> localSource.newTask(title, description));
    }

    @Override public void deleteTask(@NonNull String title) {
        localSource.deleteTask(title);
    }

    @Override
    public void deleteAll() {
        localSource.deleteAll();
    }

    @Override
    public void completeTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.completeTask(task));
    }

    @Override
    public void activateTask(@NonNull Task task) {
        executors.diskIO().execute(() -> localSource.activateTask(task));
    }
}
