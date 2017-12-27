package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.example.android.architecture.blueprints.todoapp.ViewRouterExtension;

/**
 * Adds and removes children of {@link TaskDetailBuilder.TaskDetailScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TaskDetailRouter extends
    ViewRouterExtension<TaskDetailView, TaskDetailInteractor, TaskDetailBuilder.Component> {

    TaskDetailRouter(TaskDetailView view, TaskDetailInteractor interactor, TaskDetailBuilder.Component component) {
        super(view, interactor, component);
    }
}
