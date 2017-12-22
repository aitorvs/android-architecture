package com.example.android.architecture.blueprints.todoapp.root;

import android.view.ViewGroup;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerBuilder;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerRouter;
import com.example.android.architecture.blueprints.todoapp.root.menu_drawer.MenuDrawerView;
import com.example.android.architecture.blueprints.todoapp.root.statistics.StatisticsBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowBuilder;
import com.example.android.architecture.blueprints.todoapp.root.task_flow.TaskFlowRouter;
import com.uber.rib.core.RibTestBasePlaceholder;
import com.uber.rib.core.RouterHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RootRouterTest extends RibTestBasePlaceholder {

    @Mock RootBuilder.Component component;
    @Mock RootInteractor interactor;
    @Mock RootView rootView;
    @Mock TaskFlowBuilder taskFlowBuilder;
    @Mock TaskFlowRouter taskFlowRouter;
    @Mock MenuDrawerBuilder menuDrawerBuilder;
    @Mock StatisticsBuilder statisticsBuilder;
    @Mock ViewGroup viewGroup;
    @Mock MenuDrawerRouter menuDrawerRouter;
    @Mock MenuDrawerView menuDrawerView;

    private RootRouter router;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        router = new RootRouter(rootView, interactor, component, taskFlowBuilder, menuDrawerBuilder, statisticsBuilder);
        when(taskFlowBuilder.build()).thenReturn(taskFlowRouter);
    }

    ///// Tasks /////

    @Test
    public void whenAttachTasks_shouldBuildRouter() {
        RouterHelper.attach(router);
        router.attachTasks();
        verify(taskFlowBuilder).build();
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTask_shouldRemoveAllViews() {
        when(rootView.viewContainer()).thenReturn(viewGroup);
        RouterHelper.attach(router);
        router.detachTasks();
        verify(rootView.viewContainer()).removeAllViews();
        RouterHelper.detach(router);
    }

    @Test
    public void whenDetachTask_shouldNullRouter() {
        RouterHelper.attach(router);
        router.detachTasks();
        assertEquals(router.taskFlowRouter, null);
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
