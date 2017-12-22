package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Observable;
import java.util.List;

public interface TaskDataSource {
    Observable<List<Task>> getTasks();
    void newTask(@NonNull String title, @Nullable String description);
    void deleteTask(@NonNull String title);
    void deleteAll();
    void completeTask(@NonNull Task task);
    void activateTask(@NonNull Task task);
    void updateTask(@NonNull Task task);
}
