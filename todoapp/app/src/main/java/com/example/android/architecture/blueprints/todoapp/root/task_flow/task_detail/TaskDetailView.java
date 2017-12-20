package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;

/**
 * Top level view for {@link TaskDetailBuilder.TaskDetailScope}.
 */
class TaskDetailView extends CoordinatorLayout implements TaskDetailInteractor.TaskDetailPresenter {
    @BindView(R.id.task_detail_title) TextView taskTitle;
    @BindView(R.id.task_detail_description) TextView taskDescription;
    @BindView(R.id.task_detail_complete) CheckBox taskComplete;
    @BindView(R.id.edit_task) View editTask;

    // complete events
    private PublishRelay<Object> completeRelay = PublishRelay.create();
    private Relay<Object> completeEvent = completeRelay.toSerialized();
    // activate events
    private PublishRelay<Object> activateRelay = PublishRelay.create();
    private Relay<Object> activateEvent = activateRelay.toSerialized();


    public TaskDetailView(Context context) {
        this(context, null);
    }

    public TaskDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskDetailView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.task_detail_complete)
    void onCompleteClicked(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            Snackbar.make(this, R.string.task_marked_completed, Snackbar.LENGTH_LONG).show();
            completeRelay.accept(Notification.INSTANCE);
        } else {
            activateRelay.accept(Notification.INSTANCE);
        }
    }

    @Override public void showDetailTask(Task task) {
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
        taskComplete.setChecked(task.isDone());
    }

    @Override public Observable<Object> onEditTask() {
        return RxView.clicks(editTask);
    }

    @Override
    public Observable<Object> completeTask() {
        return completeEvent;
    }

    @Override
    public Observable<Object> activateTask() {
        return activateEvent;
    }

    enum Notification {
        INSTANCE
    }
}
