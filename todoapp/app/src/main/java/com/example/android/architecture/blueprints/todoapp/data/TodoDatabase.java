package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Room database that contains the {@link Task} table. This shall be a singleton.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
