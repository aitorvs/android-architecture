package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder.EditableTask;
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
    @Inject @EditableTask Task editableTask;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        disposables.add(presenter.task()
            .filter(t -> editableTask.isEmpty()) // ADD task
            .doOnNext(o -> presenter.clear())
            .subscribe(task -> insertTask(task.title, task.description)));

        disposables.add(presenter.task()
            .filter(t -> !editableTask.isEmpty()) // EDIT task
            .map(taskViewModel -> editableTask.update(taskViewModel.title, taskViewModel.description))
            .doOnNext(ignored -> presenter.clear()) // side effect to clear view
            .subscribe(this::updateTask));

        presenter.editTask(editableTask);
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
        Observable<TaskViewModel> task();
        void editTask(Task editableTask);
        void clear();
    }

    private void insertTask(@NonNull String title, @Nullable String description) {
        Timber.d("insertTask() called with: title = [" + title + "], description = [" + description + "]");
        taskRepository.newTask(title, description);
    }

    private void updateTask(Task task) {
        Timber.d("updateTask() called with: task = [" + task + "]");
        taskRepository.updateTask(task);
    }
}
