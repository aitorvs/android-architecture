package com.example.android.architecture.blueprints.todoapp.root.statistics;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Top level view for {@link StatisticsBuilder.StatisticsScope}.
 */
class StatisticsView extends LinearLayout implements StatisticsInteractor.StatisticsPresenter {

    public StatisticsView(Context context) {
        this(context, null);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
