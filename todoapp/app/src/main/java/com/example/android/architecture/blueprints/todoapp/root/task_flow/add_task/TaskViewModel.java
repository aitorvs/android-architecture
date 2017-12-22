package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

final class TaskViewModel {
    @NonNull final String title;
    @Nullable final String description;

    TaskViewModel(@NonNull String title, @Nullable String description) {
        this.title = title;
        this.description = description;
    }

    String title() {
        return this.title;
    }

    @Nullable String description() {
        return this.description;
    }
}
