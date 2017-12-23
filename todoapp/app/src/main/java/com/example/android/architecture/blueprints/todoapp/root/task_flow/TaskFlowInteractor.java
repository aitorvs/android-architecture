package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksInteractor;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import timber.log.Timber;

/**
 * Coordinates Business Logic for {@link TaskFlowBuilder.TaskFlowScope}.
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
public class TaskFlowInteractor extends Interactor<EmptyPresenter, TaskFlowRouter> {

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        getRouter().attachTasks();
    }

    protected void willResignActive() {
        super.willResignActive();
        Timber.d("willResignActive() called");
    }

    /**
     * Care about what the {@link TasksInteractor} has to say
     */
    class TasksListener implements TasksInteractor.Listener {

        @Override
        public void onAddNewTask() {
            Timber.d("onAddNewTask() called");
            getRouter().detachTasks();
            getRouter().attachNewTask();
        }
        @Override public void onTaskSelected(Task selectedTask) {
            Timber.d("onTaskSelected() called with: selectedTask = [" + selectedTask + "]");
            getRouter().detachTasks();
            getRouter().attachTaskDetails(selectedTask);
        }

    }

    /**
     * Care about what the {@link TaskDetailInteractor} has to say
     */
    class TaskDetailListener implements TaskDetailInteractor.Listener {
        @Override
        public void onEditTask(Task task) {
            getRouter().detachTaskDetails();
            getRouter().attachEditTask(task);
        }

    }

    class AddOrEditTaskListener implements AddTaskInteractor.Listener {
        @Override
        public void onNewTaskAdded() {
            getRouter().detachEditTask();
            getRouter().detachNewTask();
            getRouter().attachTasks();
        }

        @Override
        public void onTaskUpdated() {
            getRouter().detachEditTask();
            getRouter().detachNewTask();
            getRouter().attachTasks();
        }
    }

    public boolean handleBackPress() {
        Timber.d("handleBackPress() called");
        if (getRouter().isNewTaskAttached()) {
            getRouter().detachNewTask();
            getRouter().attachTasks();
            return true;
        }
        if (getRouter().isTaskDetailsAttached()) {
            getRouter().detachTaskDetails();
            getRouter().attachTasks();
            return true;
        }
        if (getRouter().isEditTaskAttached()) {
            getRouter().detachEditTask();
            // FIXME: This should be attachTaskDetails()
            getRouter().attachTasks();
            return true;
        }
        return false;
    }
}
