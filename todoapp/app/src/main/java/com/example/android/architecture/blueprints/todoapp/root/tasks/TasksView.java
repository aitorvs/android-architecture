package com.example.android.architecture.blueprints.todoapp.root.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Top level view for {@link TasksBuilder.TasksScope}.
 */
class TasksView extends LinearLayout implements TasksInteractor.TasksPresenter {

    public TasksView(Context context) {
        this(context, null);
    }

    public TasksView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TasksView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
