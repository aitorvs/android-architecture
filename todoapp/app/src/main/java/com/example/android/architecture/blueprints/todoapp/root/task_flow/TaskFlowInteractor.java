package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow.TaskDetailsFlowInteractor;
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

    private TasksInteractor.Filter defaultTasksFilter = TasksInteractor.Filter.ALL;

    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);

        getRouter().attachTasks(defaultTasksFilter);
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

        @Override
        public void onFilterApplied(TasksInteractor.Filter filter) {
            // update filter
            defaultTasksFilter = filter;
        }
    }

    class AddOrEditTaskListener implements AddTaskInteractor.Listener {
        @Override
        public void onActionCompleted() {
            getRouter().detachNewTask();
            getRouter().attachTasks(defaultTasksFilter);
        }
    }

    class TaskDetailsFlowListener implements TaskDetailsFlowInteractor.Listener {
        @Override
        public void onFlowFinished() {
            getRouter().detachTaskDetails();
            getRouter().attachTasks(defaultTasksFilter);
        }
    }

    @Override
    public boolean handleBackPress() {
        Timber.d("handleBackPress() called");
        // first dispatch to children
        if (getRouter().dispatchBackPress()) {
            return true;
        }

        if (getRouter().isTaskDetailsAttached()) {
            getRouter().detachTaskDetails();
            getRouter().attachTasks(defaultTasksFilter);
            return true;
        }

        if (getRouter().isNewTaskAttached()) {
            getRouter().detachNewTask();
            getRouter().attachTasks(defaultTasksFilter);
            return true;
        }
        return super.handleBackPress();
    }
}
