package com.example.android.architecture.blueprints.todoapp.root.new_task;

import com.uber.rib.core.ViewRouter;

/**
 * Adds and removes children of {@link NewTaskBuilder.NewTaskScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class NewTaskRouter extends
        ViewRouter<NewTaskView, NewTaskInteractor, NewTaskBuilder.Component> {

    public NewTaskRouter(
            NewTaskView view,
            NewTaskInteractor interactor,
            NewTaskBuilder.Component component) {
        super(view, interactor, component);
    }
}
