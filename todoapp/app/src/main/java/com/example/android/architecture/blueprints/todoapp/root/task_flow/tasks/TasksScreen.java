package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.view.View;
import android.view.ViewGroup;
import com.uber.rib.core.screenstack.ViewProvider;
import javax.annotation.Nullable;

public class TasksScreen extends ViewProvider {
    private final TasksBuilder builder;
    private TasksInteractor.Filter taskFilter = TasksInteractor.Filter.ALL;
    @Nullable private TasksRouter router;

    public TasksScreen(TasksBuilder tasksBuilder) {
        this.builder = tasksBuilder;
    }

    public void setTaskFilter(TasksInteractor.Filter filter) {
        taskFilter = filter;
    }

    @Override
    public View buildView(ViewGroup parentView) {
        router = builder.build(parentView, taskFilter);
        return router.getView();
    }

    @Override
    protected void doOnViewRemoved() {
        router = null;
    }

    @Nullable
    public TasksRouter getRouter() {
        return router;
    }
}
