package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Coordinates Business Logic for {@link AddTaskBuilder.AddTaskScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class AddTaskInteractor
        extends Interactor<AddTaskInteractor.AddTaskPresenter, AddTaskRouter> {

    @Inject AddTaskPresenter presenter;
    @Inject TaskRepository taskRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        disposables.add(presenter.task()
            .subscribe(task -> insertTask(task.first, task.second)));
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposables.clear();
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface AddTaskPresenter {
        Observable<Pair<String, String>> task();
        void clear();
    }

    private void insertTask(@NonNull String title, @Nullable String description) {
        Timber.d("insertTask() called with: title = [" + title + "], description = [" + description + "]");
        // insert to the repository
        taskRepository.newTask(title, description);
        // all good clear the view form to enter a new one
        presenter.clear();
    }
}
