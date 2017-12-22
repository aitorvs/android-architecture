package com.example.android.architecture.blueprints.todoapp.root.statistics;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.data.TaskRepository;
import com.uber.rib.core.InteractorHelper;
import com.uber.rib.core.RibTestBasePlaceholder;
import io.reactivex.Observable;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatisticsInteractorTest extends RibTestBasePlaceholder {
    private static final Task TASK = Task.create("title", "description");

    @Mock StatisticsInteractor.StatisticsPresenter presenter;
    @Mock StatisticsRouter router;
    @Mock TaskRepository taskRepository;

    private StatisticsInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        interactor = TestStatisticsInteractor.create(presenter, taskRepository);
    }

    @Test
    public void whenTaskRepoEmitsTasks_shouldCallPresenterShowStatistics() {
        when(taskRepository.getTasks()).thenReturn(Observable.just(Collections.singletonList(TASK)));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).showStatistics(0, 1);
    }

    @Test
    public void whenTaskRepoEmitsEmptyList_shouldCallPresenterShowStatistics() {
        when(taskRepository.getTasks()).thenReturn(Observable.just(Collections.emptyList()));
        InteractorHelper.attach(interactor, presenter, router, null);
        verify(presenter).showStatistics(0, 0);
    }
}
