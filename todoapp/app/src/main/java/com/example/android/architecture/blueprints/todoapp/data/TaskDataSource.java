package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

public interface TaskDataSource {
    Observable<List<Task>> getTasks();
    Observable<List<Task>> getCompletedTasks();
    Observable<List<Task>> getActiveTasks();
    void newTask(@NonNull String title, @Nullable String description);
    void deleteCompletedTasks();
    void deleteAll();
    void deleteTask(@NonNull Task task);
    void completeTask(@NonNull Task task);
    void activateTask(@NonNull Task task);
    void updateTask(@NonNull Task task);
    Maybe<Task> getTaskById(@NonNull String id);
}
