package com.example.android.architecture.blueprints.todoapp.root.add_task;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Locale;
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
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        disposables.add(presenter.task()
            .subscribe(task -> {
                Timber.d(String.format(Locale.US, "New Task: %s, %s", task.first, task.second));
            }));
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
    }
}
