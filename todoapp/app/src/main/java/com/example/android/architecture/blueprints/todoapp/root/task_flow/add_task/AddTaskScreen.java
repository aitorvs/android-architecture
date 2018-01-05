package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.view.View;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.uber.rib.core.screenstack.ViewProvider;
import io.reactivex.annotations.NonNull;
import javax.annotation.Nullable;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

public class AddTaskScreen extends ViewProvider {
    private final AddTaskBuilder builder;
    @Nullable private AddTaskRouter router;
    private Task editableTask = Task.EMPTY;

    public AddTaskScreen(AddTaskBuilder builder) {
        this.builder = builder;
    }

    @Override
    public View buildView(ViewGroup parentView) {
        router = builder.build(parentView, editableTask);
        return router.getView();
    }

    public void setEditableTask(@NonNull Task editableTask) {
        this.editableTask = checkNotNull(editableTask);
    }

    @Override
    protected void doOnViewRemoved() {
        router = null;
    }

    @Nullable
    public AddTaskRouter getRouter() {
        return router;
    }
}
