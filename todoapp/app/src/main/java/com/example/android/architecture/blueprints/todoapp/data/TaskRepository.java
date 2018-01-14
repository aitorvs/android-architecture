package com.example.android.architecture.blueprints.todoapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.AppExecutors;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class TaskRepository implements TaskDataSource {
    private final TaskDataSource localSource;
    private final AppExecutors executors;
    private final TaskDataSource remoteSource;
    private PublishRelay<RemoteUpdate> updateRemote = PublishRelay.create();

    TaskRepository(AppExecutors appExecutors, @NonNull TaskDataSource local, @NonNull TaskDataSource remote) {
        this.localSource = local;
        this.remoteSource = remote;
        this.executors = appExecutors;

        updateRemote
            .flatMap(type -> {
                if (type == RemoteUpdate.ALL) {
                    return remote.getTasks();
                } else if (type == RemoteUpdate.COMPLETED) {
                    return remote.getCompletedTasks();
                } else if (type == RemoteUpdate.ACTIVE) {
                    return remote.getActiveTasks();
                } else {
                    throw new IllegalStateException("Remote update state not valid");
                }
            })
            .subscribe(remoteTasks -> {
                for (Task task : remoteTasks) {
                    localSource.updateTask(task);
                }
            }, OnErrorNotImplementedException::new);
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return localSource.getTasks()
            .doOnNext(this::syncAllWithRemote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Task>> getCompletedTasks() {
        return localSource.getCompletedTasks()
            .doOnNext(this::syncCompletedWithRemote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Task>> getActiveTasks() {
        return localSource.getActiveTasks()
            .doOnNext(this::syncActiveWithRemote)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void newTask(@NonNull String title, @Nullable String description) {
        executors.diskIO().execute(() -> {
            localSource.newTask(title, description);
            remoteSource.newTask(title, description);
        });
    }

    @Override
    public void deleteCompletedTasks() {
        executors.diskIO().execute(() -> {
            localSource.deleteCompletedTasks();
            remoteSource.deleteCompletedTasks();
        });
    }

    @Override
    public void deleteAll() {
        executors.diskIO().execute(() -> {
            localSource.deleteAll();
            remoteSource.deleteAll();
        });
    }

    @Override
    public void deleteTask(@NonNull Task task) {
        executors.diskIO().execute(() -> {
            localSource.deleteTask(task);
            remoteSource.deleteTask(task);
        });
    }

    @Override
    public void completeTask(@NonNull Task task) {
        executors.diskIO().execute(() -> {
            localSource.completeTask(task);
            remoteSource.completeTask(task);
        });
    }

    @Override
    public void activateTask(@NonNull Task task) {
        executors.diskIO().execute(() -> {
            localSource.activateTask(task);
            remoteSource.activateTask(task);
        });
    }

    @Override
    public void updateTask(@NonNull Task task) {
        executors.diskIO().execute(() -> {
            localSource.updateTask(task);
            remoteSource.updateTask(task);
        });
    }

    @Override
    public Maybe<Task> getTaskById(@NonNull String id) {
        return localSource.getTaskById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void syncAllWithRemote(List<Task> tasks) {
        if (tasks.isEmpty()) {
            updateRemote.accept(RemoteUpdate.ALL);
        }
    }

    private void syncCompletedWithRemote(List<Task> tasks) {
        if (tasks.isEmpty()) {
            updateRemote.accept(RemoteUpdate.COMPLETED);
        }
    }

    private void syncActiveWithRemote(List<Task> tasks) {
        if (tasks.isEmpty()) {
            updateRemote.accept(RemoteUpdate.ACTIVE);
        }
    }

    enum RemoteUpdate {
        ALL, ACTIVE, COMPLETED
    }
}
