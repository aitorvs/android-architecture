package com.example.android.architecture.blueprints.todoapp.root.tasks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.R;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;

/**
 * Top level view for {@link TasksBuilder.TasksScope}.
 */
class TasksView extends CoordinatorLayout implements TasksInteractor.TasksPresenter {

    @BindView(R.id.add_task) View addTaskButton;

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
    }

    @Override
    public Observable<Object> addTask() {
        return RxView.clicks(addTaskButton);
    }
}
