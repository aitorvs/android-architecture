package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddTaskRouterTest extends RibTestBasePlaceholder {

    @Mock AddTaskBuilder.Component component;
    @Mock AddTaskInteractor interactor;
    @Mock AddTaskView view;

    private AddTaskRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new AddTaskRouter(view, interactor, component);
    }
}
