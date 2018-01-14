package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

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

    @Inject Listener listener;
    @Inject AddTaskPresenter presenter;
    @Inject TaskRepository taskRepository;
    @Inject @EditableTask Task editableTask;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        // watch for changes in the editable task to ensure we have the latest info.
        // With this we ensure that the complete/active flag is correct even when changed from the task details screen
        disposables.add(taskRepository
            .getTaskById(editableTask.getId())
            .subscribe(task -> editableTask = task));

        disposables.add(presenter.editOrAddTask(editableTask)
            .subscribe(task -> {
                Timber.d("Insert/update task = [" + task + "]");
                taskRepository.updateTask(task);
                listener.onActionCompleted();
            })
        );
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
    }

    public interface Listener {
        void onActionCompleted();
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface AddTaskPresenter {
        Observable<Task> editOrAddTask(Task editableTask);
    }
}
