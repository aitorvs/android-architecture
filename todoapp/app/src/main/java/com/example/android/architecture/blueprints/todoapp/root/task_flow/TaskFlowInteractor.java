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

    /**
     * Care about what the {@link TasksInteractor} has to say
     */
    class TasksListener implements TasksInteractor.Listener {

        @Override
        public void onAddNewTask() {
            Timber.d("onAddNewTask() called");
            getRouter().attachNewTask();
        }
        @Override public void onTaskSelected(Task selectedTask) {
            Timber.d("onTaskSelected() called with: selectedTask = [" + selectedTask + "]");
            getRouter().attachTaskDetails(selectedTask);
        }

        @Override
        public void onFilterApplied(TasksInteractor.Filter filter) {
            // update filter
            defaultTasksFilter = filter;
        }
    }

    /**
     * Care about what the {@link AddTaskInteractor} has to say
     */
    class AddOrEditTaskListener implements AddTaskInteractor.Listener {
        @Override
        public void onActionCompleted() {
            // task is added added, go back to list of tasks
            getRouter().detachLatest();
        }
    }

    /**
     * Care about what the {@link TaskDetailsFlowInteractor} has to say
     */
    class TaskDetailsFlowListener implements TaskDetailsFlowInteractor.Listener {
        @Override
        public void onFlowFinished() {
            // just detach the task details flow
            getRouter().detachTaskDetails();
        }
    }
}
