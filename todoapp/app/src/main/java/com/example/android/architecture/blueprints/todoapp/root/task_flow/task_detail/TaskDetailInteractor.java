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
import timber.log.Timber;

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

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);
        // just show the task
        presenter.showDetailTask(selectedTask);

        disposable.add(presenter
            .onEditTask()
            .subscribe(o -> Timber.d("edit task")));

        disposable.add(presenter
            .completeTask()
            .subscribe(o -> taskRepository.completeTask(selectedTask)));

        disposable.add(presenter
            .activateTask()
            .subscribe(o -> taskRepository.activateTask(selectedTask)));
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
        disposable.dispose();
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface TaskDetailPresenter {
        void showDetailTask(Task task);
        Observable<Object> onEditTask();
        Observable<Object> completeTask();
        Observable<Object> activateTask();
    }
}
