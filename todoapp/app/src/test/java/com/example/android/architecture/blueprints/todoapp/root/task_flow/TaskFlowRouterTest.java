package com.example.android.architecture.blueprints.todoapp.root.task_flow;

import com.example.android.architecture.blueprints.todoapp.root.RootView;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task.AddTaskBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail.TaskDetailBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks.TasksBuilder;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskFlowRouterTest extends RibTestBasePlaceholder {

    @Mock TaskFlowBuilder.Component component;
    @Mock TaskFlowInteractor interactor;
    @Mock RootView rootView;

    private TaskFlowRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        router = new TaskFlowRouter(interactor, component, rootView.viewContainer(), new TasksBuilder(component),
            new AddTaskBuilder(component), new TaskDetailBuilder(component));
    }

    /**
     * TODO: Delete this example and add real tests.
     */
    @Test
    public void anExampleTest_withSomeConditions_shouldPass() {
        // Use RouterHelper to drive your router's lifecycle.
        RouterHelper.attach(router);
        RouterHelper.detach(router);

        throw new RuntimeException("Remove this test and add real tests.");
    }

}
