package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import com.uber.rib.core.ViewRouter;

/**
 * Adds and removes children of {@link TasksBuilder.TasksScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TasksRouter extends
        ViewRouter<TasksView, TasksInteractor, TasksBuilder.Component> {

    public TasksRouter(TasksView view, TasksInteractor interactor, TasksBuilder.Component component) {
        super(view, interactor, component);
    }
}
