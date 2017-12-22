package com.example.android.architecture.blueprints.todoapp.root.statistics;

import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StatisticsRouterTest extends RibTestBasePlaceholder {

    @Mock StatisticsBuilder.Component component;
    @Mock StatisticsInteractor interactor;
    @Mock StatisticsView view;

    private StatisticsRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new StatisticsRouter(view, interactor, component);
    }
}
