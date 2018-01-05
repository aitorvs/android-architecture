package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_details_flow;

import com.example.android.architecture.blueprints.todoapp.RouterExtended;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailRouter;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailScreen;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Adds and removes children of {@link TaskDetailsFlowBuilder.TaskDetailsFlowScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class TaskDetailsFlowRouter
        extends RouterExtended<TaskDetailsFlowInteractor, TaskDetailsFlowBuilder.Component> {

    private final ScreenStack backStack;
    private final AddTaskScreen editTaskScreen;
    private final TaskDetailScreen taskDetailScreen;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final int backStackIndex;

    TaskDetailsFlowRouter(ScreenStack stack, TaskDetailsFlowInteractor interactor,
        TaskDetailsFlowBuilder.Component component, TaskDetailScreen taskDetailScreen, AddTaskScreen addTaskScreen) {

        super(interactor, component);

        this.backStack = stack;
        this.backStackIndex = backStack.indexOfLastItem(); // save the index to the last item in the backstack
        this.editTaskScreen = addTaskScreen;
        this.taskDetailScreen = taskDetailScreen;
    }

    @Override
    protected void willAttach() {
        disposables.add(taskDetailScreen
            .lifecycle()
            .subscribe(event -> {
                TaskDetailRouter router = taskDetailScreen.getRouter();
                handleScreenEvents(router, event);
            }));

        disposables.add(editTaskScreen
            .lifecycle()
            .subscribe(event -> {
                AddTaskRouter router = editTaskScreen.getRouter();
                handleScreenEvents(router, event);
            }));
    }

    @Override
    protected void willDetach() {
        backStack.popBackTo(backStackIndex, false);
        disposables.clear();
    }

    void attachTaskDetails(Task selectedTask) {
        taskDetailScreen.setTask(selectedTask);
        backStack.pushScreen(taskDetailScreen);
    }

    void attachEditTask(Task selectedTask) {
        editTaskScreen.setEditableTask(selectedTask);
        backStack.pushScreen(editTaskScreen);
    }
}
