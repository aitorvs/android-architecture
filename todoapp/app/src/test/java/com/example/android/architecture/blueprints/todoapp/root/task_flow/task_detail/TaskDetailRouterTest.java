package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TaskDetailRouterTest extends RibTestBasePlaceholder {

    @Mock TaskDetailBuilder.Component component;
    @Mock TaskDetailInteractor interactor;
    @Mock TaskDetailView view;

    private TaskDetailRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new TaskDetailRouter(view, interactor, component);
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
