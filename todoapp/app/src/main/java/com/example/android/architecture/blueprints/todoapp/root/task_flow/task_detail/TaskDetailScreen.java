package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import android.view.View;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksRouter;
import com.uber.rib.core.screenstack.ViewProvider;
import io.reactivex.annotations.NonNull;
import javax.annotation.Nullable;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

public class TaskDetailScreen extends ViewProvider {
    private final TaskDetailBuilder builder;
    @Nullable private TaskDetailRouter router;
    private Task task;

    public TaskDetailScreen(TaskDetailBuilder tasksBuilder) {
        this.builder = tasksBuilder;
    }

    @Override
    public View buildView(ViewGroup parentView) {
        router = builder.build(parentView, checkNotNull(task));
        return router.getView();
    }

    public void setTask(@NonNull Task task) {
        this.task = checkNotNull(task);
    }

    @Override
    protected void doOnViewRemoved() {
        router = null;
    }

    @Nullable
    public TaskDetailRouter getRouter() {
        return router;
    }
}
