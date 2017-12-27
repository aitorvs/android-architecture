package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.RouterExtension;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailRouter;

/**
 * Adds and removes children of {@link TaskDetailsFlowBuilder.TaskDetailsFlowScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TaskDetailsFlowRouter
        extends RouterExtension<TaskDetailsFlowInteractor, TaskDetailsFlowBuilder.Component> {

    private final ViewGroup rootView;
    private final TaskDetailBuilder taskDetailBuilder;
    private final AddTaskBuilder editTaskBuilder;

    @Nullable TaskDetailRouter taskDetailRouter;
    @Nullable AddTaskRouter editTaskRouter;

    public TaskDetailsFlowRouter(TaskDetailsFlowInteractor interactor, TaskDetailsFlowBuilder.Component component,
        ViewGroup rootView, TaskDetailBuilder taskDetailBuilder, AddTaskBuilder editTaskBuilder) {
        super(interactor, component);

        this.rootView = rootView;
        this.taskDetailBuilder = taskDetailBuilder;
        this.editTaskBuilder = editTaskBuilder;
    }

    void attachTaskDetails(Task selectedTask) {
        taskDetailRouter = taskDetailBuilder.build(rootView, selectedTask);
        attachChild(taskDetailRouter);
        rootView.addView(taskDetailRouter.getView());
    }

    void detachTaskDetails() {
        if (taskDetailRouter != null) {
            detachChild(taskDetailRouter);
            rootView.removeView(taskDetailRouter.getView());
            taskDetailRouter = null;
        }
    }

    void attachEditTask(Task selectedTask) {
        editTaskRouter = editTaskBuilder.build(rootView, selectedTask);
        attachChild(editTaskRouter);
        rootView.addView(editTaskRouter.getView());
    }

    void detachEditTask() {
        if (editTaskRouter != null) {
            detachChild(editTaskRouter);
            rootView.removeView(editTaskRouter.getView());
            editTaskRouter = null;
        }
    }

    boolean isEditTaskAttached() {
        return editTaskRouter != null;
    }
}
