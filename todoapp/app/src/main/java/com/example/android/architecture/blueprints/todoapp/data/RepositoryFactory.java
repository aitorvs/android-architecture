package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import com.example.android.architecture.blueprints.todoapp.AppExecutors;

public class RepositoryFactory {

    private RepositoryFactory() {
        throw new AssertionError("no instances");
    }

    public static TaskRepository createTaskRepository(@NonNull Context context) {
        TodoDatabase db = Room
            .databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "Tasks.db").build();
        return new TaskRepository(new AppExecutors(), new LocalTaskDataSource(db));
    }
}
