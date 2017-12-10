package com.example.android.architecture.blueprints.todoapp.root;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.root.new_task.NewTaskInteractor;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Coordinates Business Logic for {@link RootScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class RootInteractor
        extends Interactor<RootInteractor.RootPresenter, RootRouter> {

    @Inject RootPresenter presenter;

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);
        getRouter().attachTasks();
        getRouter().attachNewTask();
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    class NewTaskListener implements NewTaskInteractor.Listener {
        @Override
        public void addTask() {
            Timber.d("addTask() called");
        }
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter { }
}
