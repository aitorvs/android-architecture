package com.example.android.architecture.blueprints.todoapp.root.menu_drawer;

import com.uber.rib.core.RibTestBasePlaceholder;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MenuDrawerRouterTest extends RibTestBasePlaceholder {

    @Mock MenuDrawerBuilder.Component component;
    @Mock MenuDrawerInteractor interactor;
    @Mock MenuDrawerView view;

    private MenuDrawerRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new MenuDrawerRouter(view, interactor, component);
    }
}
