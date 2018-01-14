package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.List;
import timber.log.Timber;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

class RemoteTaskDataSource implements TaskDataSource {
    private final TaskService service;
    Relay<Notification> getTasksEvent = BehaviorRelay.createDefault(Notification.INSTANCE).toSerialized();

    RemoteTaskDataSource(TaskService taskService) {
        this.service = checkNotNull(taskService);
    }

    @Override
    public Observable<List<Task>> getTasks() {
        // user a relay to convert this into a hot observable
        return getTasksEvent
            .switchMap(o -> service.getTasks()
                .filter(r -> !r.isError())
                .map(r -> r.response().body())
            );
    }

    @Override
    public Observable<List<Task>> getCompletedTasks() {
        return service.getCompletedTasks()
            .filter(r -> !r.isError())
            .map(r -> r.response().body());
    }

    @Override
    public Observable<List<Task>> getActiveTasks() {
        return service.getActiveTasks()
            .filter(r -> !r.isError())
            .map(r -> r.response().body());
    }

    @Override
    public void newTask(@NonNull String title, @Nullable String description) {
        // create new task
        try {
            service.updateTask(Task.create(title, description)).execute();
        } catch (IOException e) {
            Timber.e(e, "Error creating new task");
        }
    }

    @Override
    public void deleteCompletedTasks() {
        try {
            service.deleteCompletedTasks().execute();
        } catch (IOException e) {
            Timber.e(e, "Error deleting complete tasks");
        }
    }

    @Override
    public void deleteAll() {
        try {
            service.deleteAll().execute();
        } catch (IOException e) {
            Timber.e(e, "Error deleting ALL tasks");
        }
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        try {
            service.deleteTask(task.getId()).execute();
        } catch (IOException e) {
            Timber.e(e, "Error deleting task: %s", task);
        }
    }

    @Override
    public void completeTask(@NonNull Task task) {
        try {
            service.completeTask(task.getId()).execute();
        } catch (IOException e) {
            Timber.e(e, "Error marking task %s as complete", task);
        }
    }

    @Override
    public void activateTask(@NonNull Task task) {
        try {
            service.activateTask(task.getId()).execute();
        } catch (IOException e) {
            Timber.e(e, "Error marking task %s as active", task);
        }
    }

    @Override
    public void updateTask(@NonNull Task task) {
        try {
            service.updateTask(task).execute();
        } catch (IOException e) {
            Timber.e(e, "Error updating task %s", task);
        }
    }

    @Override
    public Maybe<Task> getTaskById(@NonNull String id) {
        return Maybe.empty();
    }

    enum Notification {
        INSTANCE
    }
}
