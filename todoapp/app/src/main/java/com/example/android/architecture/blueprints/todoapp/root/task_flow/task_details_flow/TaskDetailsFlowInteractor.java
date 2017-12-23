package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow;

import android.support.annotation.Nullable;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskInteractor;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailInteractor;
import com.uber.rib.core.Bundle;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.Interactor;
import com.uber.rib.core.RibInteractor;
import com.uber.rib.core.Router;

import javax.inject.Inject;

/**
 * Coordinates Business Logic for {@link TaskDetailsFlowBuilder.TaskDetailsFlowScope}.
 *
 * This RIB interactor is view less and it is used to keep some state that is necessary for the {@link Task} details
 * and edit flow.
 * In particular, the selected {@link Task} is kept in this RIB, greatly simplifying the state management of the
 * children.
 */
@RibInteractor
public class TaskDetailsFlowInteractor extends Interactor<EmptyPresenter, TaskDetailsFlowRouter> {

    @Inject Task selectedTask;
    @Inject Listener listener;

    @Override
    protected void didBecomeActive(@Nullable Bundle savedInstanceState) {
        super.didBecomeActive(savedInstanceState);
        getRouter().attachTaskDetails(selectedTask);
    }

    @Override
    protected void willResignActive() {
        super.willResignActive();
    }

    public interface Listener {
        void onFlowFinished();
    }

    class TaskDetailListener implements TaskDetailInteractor.Listener {
        @Override
        public void onEditTask(Task task) {
            getRouter().detachTaskDetails();
            getRouter().attachEditTask(selectedTask);
        }
    }

    class EditTaskListener implements AddTaskInteractor.Listener {
        @Override
        public void onActionCompleted() {
            listener.onFlowFinished();
        }
    }

    @Override
    public boolean handleBackPress() {
        if (getRouter().isEditTaskAttached()) {
            getRouter().detachEditTask();
            getRouter().attachTaskDetails(selectedTask);
            return true;
        }
        return super.handleBackPress();
    }
}
