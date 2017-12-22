package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.UUID;

@Entity(tableName = "tasks")
public final class Task {
    // This can be used to use in place of null tasks
    public static final Task EMPTY = new Task("", "", "", false);

    @PrimaryKey @NonNull private final String id;
    @NonNull private final String title;
    @Nullable private final String description;
    private final boolean done;

    @Ignore
    public static Task create(@NonNull String title, @Nullable String description) {
        return new Task(UUID.randomUUID().toString(), title, description, false);
    }

    @Ignore
    public Task update(@NonNull String title, @Nullable String description) {
        return new Task(id, title, description, done);
    }

    Task(@NonNull String id, @NonNull String title, @Nullable String description, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    @Ignore
    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (done != task.done) return false;
        if (!id.equals(task.id)) return false;
        if (!title.equals(task.title)) return false;
        return description != null ? description.equals(task.description) : task.description == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (done ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{"
            + "id=" + id + ", "
            + "title=" + title + ", "
            + "description=" + description + ", "
            + "done=" + done
            + "}";
    }
}
