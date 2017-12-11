package com.example.android.architecture.blueprints.todoapp.root.tasks;

import android.support.annotation.Nullable;

import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link TasksBuilder.TasksScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class TasksInteractor
        extends Interactor<TasksInteractor.TasksPresenter, TasksRouter> {

    @Inject TasksPresenter presenter;
    @Inject Listener listener;

    private final CompositeDisposable disposables = new CompositeDisposable();
    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        disposables.add(presenter.addTask()
            .subscribe(o -> listener.onAddNewTask()));
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposables.clear();
    }

    public interface Listener {
        void onAddNewTask();
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface TasksPresenter {
        Observable<Object> addTask();
    }
}
