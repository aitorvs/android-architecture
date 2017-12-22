package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Top level view for {@link TasksBuilder.TasksScope}.
 */
public class TasksView extends CoordinatorLayout implements TasksInteractor.TasksPresenter {

    @BindView(R.id.add_task) View addTaskButton;
    @BindView(R.id.tasks_list) ListView taskList;
    @BindView(R.id.empty_view) View emptyView;
    @BindView(R.id.tasks_content) View content;

    private TasksAdapter taskAdapter;
    private final PublishRelay<Task> completeTaskRelay = PublishRelay.create();
    private final Relay<Task> completedTask = completeTaskRelay.toSerialized();
    private final PublishRelay<Task> activateTaskRelay = PublishRelay.create();
    private final Relay<Task> activatedTask = activateTaskRelay.toSerialized();
    private final PublishRelay<Task> clickedTaskRelay = PublishRelay.create();
    private final Relay<Task> clickedTask = clickedTaskRelay.toSerialized();

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
    public Observable<Object> addTask() {
        return RxView.clicks(addTaskButton);
    }

    @Override
    public Observable<Task> competedTask() {
        return completedTask;
    }

    @Override
    public Observable<Task> task() {
        return clickedTask;
    }

    @Override
    public Observable<Task> activateTask() {
        return activatedTask;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        boolean empty = tasks.isEmpty();
        content.setVisibility(empty ? GONE : VISIBLE);
        emptyView.setVisibility(empty ? VISIBLE : GONE);
        taskAdapter.replaceData(tasks);
    }
}
