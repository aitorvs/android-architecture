package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import java.util.List;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

final class TasksViewModel {
    @Nullable private final List<Task> tasks;
    @Nullable private final Throwable error;
    private final boolean isLoading;

    private TasksViewModel(@Nullable List<Task> tasks, @Nullable Throwable error, boolean isLoading) {
        this.tasks = tasks;
        this.error = error;
        this.isLoading = isLoading;
    }

    boolean isLoading() {
        return isLoading;
    }

    boolean isSuccess() {
        return error == null && tasks != null;
    }

    boolean isError() {
        return error != null;
    }

    List<Task> getTasks() {
        return tasks;
    }

    Throwable getError() {
        return error;
    }

    public static TasksViewModel loading() {
        return new TasksViewModel(null, null, true);
    }

    public static TasksViewModel success(@NonNull List<Task> tasks) {
        return new TasksViewModel(checkNotNull(tasks), null, false);
    }

    public static TasksViewModel error(Throwable e) {
        return new TasksViewModel(null, e, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksViewModel that = (TasksViewModel) o;
        if (isLoading != that.isLoading) return false;
        if (tasks != null ? !tasks.equals(that.tasks) : that.tasks != null) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result = tasks != null ? tasks.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (isLoading ? 1 : 0);
        return result;
    }
}
