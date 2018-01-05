package com.example.android.architecture.blueprints.todoapp.root;

import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerView;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsScreen;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowRouter;
import com.example.android.architecture.blueprints.todoapp.screen_stack.ScreenStack;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RootRouterTest extends RibTestBasePlaceholder {

    @Mock RootBuilder.Component component;
    @Mock RootInteractor interactor;
    @Mock RootView rootView;
    @Mock TaskFlowBuilder taskFlowBuilder;
    @Mock TaskFlowRouter taskFlowRouter;
    @Mock MenuDrawerBuilder menuDrawerBuilder;
    @Mock StatisticsScreen statisticsScreen;
    @Mock MenuDrawerRouter menuDrawerRouter;
    @Mock MenuDrawerView menuDrawerView;
    @Mock ScreenStack backStack;

    private RootRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        router = new RootRouter(rootView, backStack, interactor, component, taskFlowBuilder, menuDrawerBuilder,
            statisticsScreen);

        // Init the mocks that are called in the attach methods
        when(statisticsScreen.lifecycle()).thenReturn(Observable.just(ScreenStackEvent.BUILT));
    }

    @Test
    public void whenWillAttach_shouldSubscribeToStatisticsScreenLifecycle() {
        RouterHelper.attach(router);
        verify(statisticsScreen).lifecycle();
        RouterHelper.detach(router);
    }

    @Test
    public void whenWillAttach_shouldCreateStatisticsScreenRouter() {
        RouterHelper.attach(router);
        verify(statisticsScreen).getRouter();
        RouterHelper.detach(router);
    }

    ///// Tasks /////

    @Test
    public void whenAttachTasks_shouldBuildRouter() {
        when(taskFlowBuilder.build()).thenReturn(taskFlowRouter);
        RouterHelper.attach(router);
        router.attachTasks();
        verify(taskFlowBuilder).build();
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTask_andRouterNotNull_shouldPopFullBackStack() {
        router.taskFlowRouter = taskFlowRouter;
        RouterHelper.attach(router);
        router.detachTasks();
        verify(backStack).popBackTo(-1, false);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTask_andRouterNotNull_shouldNullRouter() {
        router.taskFlowRouter = taskFlowRouter;
        RouterHelper.attach(router);
        assertEquals(router.taskFlowRouter, taskFlowRouter);
        router.detachTasks();
        assertEquals(router.taskFlowRouter, null);
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTask_andRouterNull_shouldNEverPopFullBackStack() {
        RouterHelper.attach(router);
        router.detachTasks();
        verify(backStack, never()).popBackTo(-1, false);
        RouterHelper.detach(router);
    }

    ///// Menu Drawer /////

    @Test
    public void whenAttachMenuDrawer_shouldBuildRouter() {
        when(menuDrawerBuilder.build(rootView)).thenReturn(menuDrawerRouter);
        RouterHelper.attach(router);
        router.attachMenuDrawer();
        verify(menuDrawerBuilder).build(rootView);
        RouterHelper.detach(router);
    }

    @Test
    public void whenAttachMenuDrawer_shouldAddView() {
        when(menuDrawerBuilder.build(rootView)).thenReturn(menuDrawerRouter);
        when(menuDrawerRouter.getView()).thenReturn(menuDrawerView);
        RouterHelper.attach(router);
        router.attachMenuDrawer();
        verify(rootView).addView(menuDrawerView);
        RouterHelper.detach(router);
    }
}
