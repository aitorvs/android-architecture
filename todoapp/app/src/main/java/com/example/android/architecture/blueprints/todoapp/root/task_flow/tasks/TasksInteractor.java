package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

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
    @Inject TaskRepository taskRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Relay<Filter> filterRelay = BehaviorRelay.createDefault(Filter.ALL).toSerialized();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        // subscribe to the presenter interface implemented by the TasksView
        disposables.add(presenter.addTask()
            .subscribe(o -> listener.onAddNewTask()));
        // on complete, complete the task
        disposables.add(presenter.competeTask()
            .subscribe(this::completeTask));
        //on activate, activate task
        disposables.add(presenter.activateTask()
            .subscribe(this::activateTask));
        // on task clicked, show task details
        disposables.add(presenter.selectTask()
            .subscribe(this::showTaskDetails));
        // on refresh
        disposables.add(presenter.refreshTasks()
            .subscribe(o -> Timber.d("REFRESH")));
        // on clear completed
        disposables.add(presenter.clearCompleted()
            .subscribe(o -> taskRepository.deleteCompletedTasks()));
        // on filter
        disposables.add(presenter.filter()
            .subscribe(filterRelay::accept));

        // watch the filter
        disposables.add(filterRelay
            .flatMap(filter -> {
                if (filter == Filter.ACTIVE) {
                    return taskRepository.getActiveTasks();
                } else if (filter == Filter.COMPLETED) {
                    return taskRepository.getCompletedTasks();
                } else {
                    return taskRepository.getTasks();
                }
            })
            .map(TasksViewModel::success)
            .startWith(TasksViewModel.loading())
            .onErrorReturn(TasksViewModel::error)
            .subscribe(model -> presenter.updateView(model)));
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposables.clear();
    }

    public interface Listener {
        void onAddNewTask();
        void onTaskSelected(Task selectedTask);
    }

    private void completeTask(Task task) {
        taskRepository.completeTask(task);
    }

    private void activateTask(Task task) {
        taskRepository.activateTask(task);
    }

    private void showTaskDetails(Task task) {
        listener.onTaskSelected(task);
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface TasksPresenter {
        Observable<Object> addTask();
        Observable<Task> competeTask();
        Observable<Task> selectTask();
        Observable<Task> activateTask();
        Observable<Object> clearCompleted();
        Observable<Object> refreshTasks();
        Observable<Filter> filter();
        void updateView(TasksViewModel model);
    }

    enum Filter {
        ALL, ACTIVE, COMPLETED
    }
}
