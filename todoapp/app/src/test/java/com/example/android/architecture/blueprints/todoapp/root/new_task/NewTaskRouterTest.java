package com.example.android.architecture.blueprints.todoapp.root.new_task;

import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewTaskRouterTest extends RibTestBasePlaceholder {

    @Mock NewTaskBuilder.Component component;
    @Mock NewTaskInteractor interactor;
    @Mock NewTaskView view;

    private NewTaskRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new NewTaskRouter(view, interactor, component);
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
