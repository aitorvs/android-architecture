package com.example.android.architecture.blueprints.todoapp.root.statistics;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.util.Services;

/**
 * Top level view for {@link StatisticsBuilder.StatisticsScope}.
 */
class StatisticsView extends LinearLayout implements StatisticsInteractor.StatisticsPresenter {

    @BindView(R.id.active_tasks) TextView activeTasks;
    @BindView(R.id.completed_tasks) TextView completedTasks;
    private CharSequence savedToolbarTitle;

    public StatisticsView(Context context) {
        this(context, null);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // saved previous toolbar title and set the one for this view
        ActionBar supportActionBar = Services.getSupportActionBar(getContext());
        savedToolbarTitle = supportActionBar.getTitle();
        supportActionBar.setTitle(R.string.statistics);
    }

    @Override
    protected void onDetachedFromWindow() {
        // restore the previous title
        Services.getSupportActionBar(getContext()).setTitle(savedToolbarTitle);
        super.onDetachedFromWindow();
    }

    @Override
    public void updateView(StatisticsViewModel viewModel) {
        completedTasks.setVisibility(viewModel.isEmpty() ? GONE : VISIBLE);
        if (viewModel.isEmpty()) {
            activeTasks.setText(R.string.you_have_no_tasks);
        } else {
            activeTasks.setText(getContext().getString(R.string.active_tasks, viewModel.active));
            completedTasks.setText(getContext().getString(R.string.completed_tasks, viewModel.completed));
        }
    }
}
