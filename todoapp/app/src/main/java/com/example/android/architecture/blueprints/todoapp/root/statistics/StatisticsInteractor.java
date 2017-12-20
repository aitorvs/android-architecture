package com.example.android.architecture.blueprints.todoapp.root.statistics;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link StatisticsBuilder.StatisticsScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class StatisticsInteractor
        extends Interactor<StatisticsInteractor.StatisticsPresenter, StatisticsRouter> {

    @Inject StatisticsPresenter presenter;
    @Inject TaskRepository taskRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        disposables.add(taskRepository
            .getTasks()
            .subscribe(this::showStatistics));
    }

    private void showStatistics(List<Task> tasks) {
        int active = 0;
        int completed = 0;
        for (Task task : tasks) {
            if (task.isDone()) {
                completed++;
            } else {
                active++;
            }
        }
        presenter.showStatistics(completed, active);
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposables.dispose();
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface StatisticsPresenter {
        void showStatistics(int completed, int active);
    }
}
