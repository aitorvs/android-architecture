package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import com.example.android.architecture.blueprints.todoapp.ViewRouterExtension;

/**
 * Adds and removes children of {@link TasksBuilder.TasksScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TasksRouter extends ViewRouterExtension<TasksView, TasksInteractor, TasksBuilder.Component> {

    TasksRouter(TasksView view, TasksInteractor interactor, TasksBuilder.Component component) {
        super(view, interactor, component);
    }
}
