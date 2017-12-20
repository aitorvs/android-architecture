package com.example.android.architecture.blueprints.todoapp.root.tasks;

import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
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
    @Inject TaskRepository taskRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        // subscribe to the presenter interface implemented by the TasksView
        disposables.add(presenter.addTask()
            .subscribe(o -> listener.onAddNewTask()));
        // on complete, complete the task
        disposables.add(presenter.competedTask()
            .subscribe(this::completeTask));
        //on activate, activate task
        disposables.add(presenter.activateTask()
            .subscribe(this::activateTask));
        // on task clicked, show task details
        disposables.add(presenter.task()
            .subscribe(this::showTaskDetails));

        // subscribe to the repository
        disposables.add(taskRepository
            .getTasks()
            .subscribe(tasks -> presenter.showTasks(tasks)));
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
        Snackbar.make(getRouter().getView(), R.string.task_marked_completed, BaseTransientBottomBar.LENGTH_LONG).show();
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
        Observable<Task> competedTask();
        Observable<Task> task();
        Observable<Task> activateTask();
        void showTasks(List<Task> tasks);
    }
}
