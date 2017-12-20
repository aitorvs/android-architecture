package com.example.android.architecture.blueprints.todoapp.root.task_flow.task_detail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Top level view for {@link TaskDetailBuilder.TaskDetailScope}.
 */
class TaskDetailView extends CoordinatorLayout implements TaskDetailInteractor.TaskDetailPresenter {
    @BindView(R.id.task_detail_title) TextView taskTitle;
    @BindView(R.id.task_detail_description) TextView taskDescription;
    @BindView(R.id.task_detail_complete) CheckBox taskComplete;
    @BindView(R.id.edit_task) View editTask;


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

    @Override public void showDetailTask(Task task) {
        Timber.d("showDetailTask() called with: task = [" + task + "]");
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
    }

    @Override public Observable<Object> onEditTask() {
        return RxView.clicks(editTask);
    }
}
