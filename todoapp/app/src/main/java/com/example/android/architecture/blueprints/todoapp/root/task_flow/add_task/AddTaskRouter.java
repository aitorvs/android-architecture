package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import com.example.android.architecture.blueprints.todoapp.ViewRouterExtension;

/**
 * Adds and removes children of {@link AddTaskBuilder.AddTaskScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class AddTaskRouter extends ViewRouterExtension<AddTaskView, AddTaskInteractor, AddTaskBuilder.Component> {

    AddTaskRouter(AddTaskView view, AddTaskInteractor interactor, AddTaskBuilder.Component component) {
        super(view, interactor, component);
    }
}
