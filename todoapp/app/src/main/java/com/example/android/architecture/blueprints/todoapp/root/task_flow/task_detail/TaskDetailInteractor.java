package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link TaskDetailBuilder.TaskDetailScope}
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class TaskDetailInteractor
        extends Interactor<TaskDetailInteractor.TaskDetailPresenter, TaskDetailRouter> {

    @Inject TaskDetailPresenter presenter;
    @Inject @TaskDetailBuilder.TaskDetailInternal Task selectedTask;
    @Inject TaskRepository taskRepository;
    @Inject Listener listener;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        // ensure the task is always up to date fetching from repo
        disposable.add(taskRepository
            .getTaskById(selectedTask.getId())
            .subscribe(task -> {
                // update the selected task
                selectedTask = task;
                // update the view
                presenter.showDetailTask(task);
            }));

        disposable.add(presenter
            .editTask()
            .subscribe(o -> listener.editTask(selectedTask)));

        disposable.add(presenter
            .completeTask()
            .subscribe(o -> taskRepository.completeTask(selectedTask)));

        disposable.add(presenter
            .activateTask()
            .subscribe(o -> taskRepository.activateTask(selectedTask)));

        disposable.add(presenter
            .deleteTask()
            .subscribe(o -> {
                taskRepository.deleteTask(selectedTask);
                listener.taskDeleted();
            }));
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposable.dispose();
    }

    /**
     * Interface to be implemented by the parent RIB
     */
    public interface Listener {
        void editTask(Task task);
        void taskDeleted();
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface TaskDetailPresenter {
        void showDetailTask(Task task);
        Observable<Object> editTask();
        Observable<Object> completeTask();
        Observable<Object> activateTask();
        Observable<Object> deleteTask();
    }
}
