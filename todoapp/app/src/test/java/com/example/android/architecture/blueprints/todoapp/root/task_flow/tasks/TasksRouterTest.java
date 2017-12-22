package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TasksRouterTest extends RibTestBasePlaceholder {

    @Mock TasksBuilder.Component component;
    @Mock TasksInteractor interactor;
    @Mock TasksView view;

    private TasksRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new TasksRouter(view, interactor, component);
    }
}
