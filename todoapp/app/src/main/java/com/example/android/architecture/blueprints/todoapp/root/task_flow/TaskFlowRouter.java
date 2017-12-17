package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.tasks.TasksRouter;
import com.uber.rib.core.Router;
import timber.log.Timber;

/**
 * Adds and removes children of {@link TaskFlowBuilder.TaskFlowScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TaskFlowRouter
        extends Router<TaskFlowInteractor, TaskFlowBuilder.Component> {
    private final ViewGroup parentView;
    private final AddTaskBuilder newTaskBuilder;
    private final TasksBuilder tasksBuilder;
    @Nullable private TasksRouter tasksRouter;
    @Nullable private AddTaskRouter newTaskRouter;

    public TaskFlowRouter(TaskFlowInteractor interactor, TaskFlowBuilder.Component component, ViewGroup viewGroup,
        TasksBuilder tasksBuilder, AddTaskBuilder addTaskBuilder) {

        super(interactor, component);
        this.parentView = viewGroup;
        this.tasksBuilder = tasksBuilder;
        this.newTaskBuilder = addTaskBuilder;
    }

    void attachTasks() {
        Timber.d("attachTasks() called");
        tasksRouter = tasksBuilder.build(parentView);
        attachChild(tasksRouter);
        parentView.addView(tasksRouter.getView());
    }

    void detachTasks() {
        Timber.d("detachTasks() called");
        if (tasksRouter != null) {
            detachChild(tasksRouter);
            parentView.removeView(tasksRouter.getView());
            tasksRouter = null;
        }
    }

    void attachNewTask() {
        Timber.d("attachNewTask() called");
        newTaskRouter = newTaskBuilder.build(parentView);
        attachChild(newTaskRouter);
        parentView.addView(newTaskRouter.getView());
    }

    void detachNewTask() {
        Timber.d("detachNewTask() called");
        if (newTaskRouter != null) {
            detachChild(newTaskRouter);
            parentView.removeView(newTaskRouter.getView());
            newTaskRouter = null;
        }
    }

    private boolean isNewTaskAttached() {
        return newTaskRouter != null;
    }

    @Override
    public boolean handleBackPress() {
        Timber.d("handleBackPress() called");
        if (isNewTaskAttached()) {
            detachNewTask();
            attachTasks();
            return true;
        }
        return false;
    }
}
