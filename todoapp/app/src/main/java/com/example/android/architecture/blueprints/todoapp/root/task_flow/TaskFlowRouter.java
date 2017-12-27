package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.RouterExtension;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksRouter;
import timber.log.Timber;

/**
 * Adds and removes children of {@link TaskFlowBuilder.TaskFlowScope}.
 *
 * This task mainly attaches and detaches the views related to the task workflow.
 */
public class TaskFlowRouter
        extends RouterExtension<TaskFlowInteractor, TaskFlowBuilder.Component> {
    private final ViewGroup parentView;
    private final AddTaskBuilder newTaskBuilder;
    private final TasksBuilder tasksBuilder;
    private final TaskDetailsFlowBuilder taskDetailsFlowBuilder;
    @Nullable TasksRouter tasksRouter;
    @Nullable AddTaskRouter newTaskRouter;
    @Nullable TaskDetailsFlowRouter taskDetailsFlowRouter;

    TaskFlowRouter(TaskFlowInteractor interactor, TaskFlowBuilder.Component component, ViewGroup viewGroup,
        TasksBuilder tasksBuilder, AddTaskBuilder addTaskBuilder, TaskDetailsFlowBuilder taskDetailsFlowBuilder) {

        super(interactor, component);
        this.parentView = viewGroup;
        this.tasksBuilder = tasksBuilder;
        this.newTaskBuilder = addTaskBuilder;
        this.taskDetailsFlowBuilder = taskDetailsFlowBuilder;
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
        Timber.d("attachTaskDetails() called with: selectedTask = [" + selectedTask + "]");
        taskDetailsFlowRouter = taskDetailsFlowBuilder.build(parentView, selectedTask);
        attachChild(taskDetailsFlowRouter);
    }

    void detachTaskDetails() {
        Timber.d("detachTaskDetails() called");
        if (taskDetailsFlowRouter != null) {
            detachChild(taskDetailsFlowRouter);
            // remove any child that is attached here
            parentView.removeAllViews();
            taskDetailsFlowRouter = null;
        }
    }

    boolean isTaskDetailsAttached() {
        return taskDetailsFlowRouter != null;
    }

    boolean dispatchBackPress() {
        if (isTaskDetailsAttached()) {
            return taskDetailsFlowRouter.getInteractor().handleBackPress();
        }
        return false;
    }
}
