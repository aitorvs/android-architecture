package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
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
}
