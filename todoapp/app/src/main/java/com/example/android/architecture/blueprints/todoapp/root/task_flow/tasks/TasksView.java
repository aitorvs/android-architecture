package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.OptionsMenuService;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.TodoActivity;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

import static android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;

/**
 * Top level view for {@link TasksBuilder.TasksScope}.
 */
public class TasksView extends CoordinatorLayout implements TasksInteractor.TasksPresenter {
    private static final int MENU_REFRESH = Menu.FIRST;
    private static final int MENU_CLEAR_COMPLETED = Menu.FIRST + 1;
    private static final int MENU_FILTER = Menu.FIRST + 2;
    private static final int MENU_FILTER_ALL = Menu.FIRST + 3;
    private static final int MENU_FILTER_ACTIVE = Menu.FIRST + 4;
    private static final int MENU_FILTER_COMPLETED = Menu.FIRST + 5;

    @BindView(R.id.add_task) View addTaskButton;
    @BindView(R.id.tasks_list) ListView taskList;
    @BindView(R.id.filter_label) TextView tvFilterLabel;
    @BindView(R.id.empty_view) View emptyView;
    @BindView(R.id.tasks_content) View content;
    @BindView(R.id.loading_tasks) View loadingView;

    private TasksAdapter taskAdapter;
    private final PublishRelay<Task> completeTaskRelay = PublishRelay.create();
    private final Relay<Task> completedTask = completeTaskRelay.toSerialized();
    private final PublishRelay<Task> activateTaskRelay = PublishRelay.create();
    private final Relay<Task> activatedTask = activateTaskRelay.toSerialized();
    private final PublishRelay<Task> clickedTaskRelay = PublishRelay.create();
    private final Relay<Task> clickedTask = clickedTaskRelay.toSerialized();
    private final Relay<Object> clearCompleted = PublishRelay.create().toSerialized();
    private final Relay<Object> refresh = PublishRelay.create().toSerialized();
    private final Relay<TasksInteractor.Filter> filterEvent = PublishRelay.<TasksInteractor.Filter>create()
        .toSerialized();

    private OptionsMenuService menuService;
    private final OptionsMenuService.Listener optionsMenuListener = new OptionsMenuService.Listener() {
        @Override
        public void onPrepareOptionsMenu(Menu menu) {
            menu.add(0, MENU_REFRESH, Menu.NONE, R.string.refresh);
            menu.add(0, MENU_CLEAR_COMPLETED, Menu.NONE, R.string.clear_completed);
            SubMenu filter = menu.addSubMenu(0, MENU_FILTER, Menu.NONE, "Filter")
                .setIcon(R.drawable.ic_filter_list);
            filter.getItem().setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
            filter.add(0, MENU_FILTER_ALL, Menu.NONE, "All");
            filter.add(0, MENU_FILTER_ACTIVE, Menu.NONE, "Active");
            filter.add(0, MENU_FILTER_COMPLETED, Menu.NONE, "Completed");
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == MENU_REFRESH) {
                refresh.accept(Notification.INSTANCE);
                return true;
            } else if (item.getItemId() == MENU_CLEAR_COMPLETED) {
                clearCompleted.accept(Notification.INSTANCE);
                return true;
            } else if (item.getItemId() == MENU_FILTER_ALL) {
                filterEvent.accept(TasksInteractor.Filter.ALL);
                return true;
            } else if (item.getItemId() == MENU_FILTER_ACTIVE) {
                filterEvent.accept(TasksInteractor.Filter.ACTIVE);
                return true;
            } else if (item.getItemId() == MENU_FILTER_COMPLETED) {
                filterEvent.accept(TasksInteractor.Filter.COMPLETED);
                return true;
            }
            return false;
        }
    };

    public TasksView(Context context) {
        this(context, null);
    }

    public TasksView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TasksView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        taskAdapter = new TasksAdapter(new ArrayList<>(0), new TasksAdapter.TaskItemListener() {
            @Override
            public void onTaskClick(Task task) {
                clickedTask.accept(task);
            }

            @Override
            public void onCompleteTaskClick(Task task) {
                Snackbar.make(TasksView.this, R.string.task_marked_completed, Snackbar.LENGTH_LONG)
                    .show();

                completedTask.accept(task);
            }

            @Override
            public void onActivateTaskClick(Task task) {
                activatedTask.accept(task);
            }
        });
        taskList.setAdapter(taskAdapter);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        menuService = (OptionsMenuService) getContext().getSystemService(TodoActivity.OPTIONS_MENU_SERVICE);
        menuService.addOptionsMenuListener(optionsMenuListener);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        menuService.removeOptionsMenuListener(optionsMenuListener);
    }

    @Override
    public Observable<Object> addTask() {
        return RxView.clicks(addTaskButton);
    }

    @Override
    public Observable<Task> competeTask() {
        return completedTask;
    }

    @Override
    public Observable<Task> selectTask() {
        return clickedTask;
    }

    @Override
    public Observable<Task> activateTask() {
        return activatedTask;
    }

    @Override
    public Observable<Object> clearCompleted() {
        return clearCompleted;
    }

    @Override
    public Observable<Object> refreshTasks() {
        return refresh;
    }

    @Override
    public Observable<TasksInteractor.Filter> filter() {
        return filterEvent;
    }

    @Override
    public void updateView(TasksViewModel model) {
        loadingView.setVisibility(model.isLoading() ? VISIBLE: GONE);
        // populate the tasks
        if (model.isSuccess()) {
            List<Task> tasks = model.getData().getTasks();
            boolean empty = tasks.isEmpty();
            content.setVisibility(empty ? GONE : VISIBLE);
            emptyView.setVisibility(empty ? VISIBLE : GONE);
            taskAdapter.replaceData(tasks);

            // update the list header
            if (model.getData().isFilteredByActive()) {
                tvFilterLabel.setText(R.string.active_to_dos);
            } else if (model.getData().isFilteredByCompleted()) {
                tvFilterLabel.setText(R.string.completed_to_dos);
            } else {
                tvFilterLabel.setText(R.string.all_to_dos);
            }
        } else if (model.isError()) {
            Timber.e(model.getError(), "Error updating the tasks view");
        }
    }

    private enum Notification {
        INSTANCE
    }
}
