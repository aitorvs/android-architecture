package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksRouter;
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
    private final TaskDetailBuilder taskDetailBuilder;
    @Nullable private TasksRouter tasksRouter;
    @Nullable private AddTaskRouter newTaskRouter;
    @Nullable private TaskDetailRouter taskDetailRouter;

    TaskFlowRouter(TaskFlowInteractor interactor, TaskFlowBuilder.Component component, ViewGroup viewGroup,
        TasksBuilder tasksBuilder, AddTaskBuilder addTaskBuilder, TaskDetailBuilder taskDetailBuilder) {

        super(interactor, component);
        this.parentView = viewGroup;
        this.tasksBuilder = tasksBuilder;
        this.newTaskBuilder = addTaskBuilder;
        this.taskDetailBuilder = taskDetailBuilder;
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

    boolean isNewTaskAttached() {
        return newTaskRouter != null;
    }

    void attachTaskDetails(Task selectedTask) {
        Timber.d("attachTaskDetails() called");
        taskDetailRouter = taskDetailBuilder.build(parentView, selectedTask);
        attachChild(taskDetailRouter);
        parentView.addView(taskDetailRouter.getView());
    }

    void detachTaskDetails() {
        if (taskDetailRouter != null) {
            detachChild(taskDetailRouter);
            parentView.removeView(taskDetailRouter.getView());
            taskDetailRouter = null;
        }
    }

    boolean isTaskDetailsAttached() {
        return taskDetailRouter != null;
    }
}
