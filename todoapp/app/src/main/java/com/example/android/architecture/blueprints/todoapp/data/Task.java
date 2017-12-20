package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName = "tasks")
public final class Task {
    @PrimaryKey private final int id;
    @NonNull private final String title;
    @Nullable private final String description;
    private final boolean done;

    public Task(int id, @NonNull String title, @Nullable String description, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public int getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (id != task.id) return false;
        if (done != task.done) return false;
        if (!title.equals(task.title)) return false;
        return description != null ? description.equals(task.description) : task.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
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
