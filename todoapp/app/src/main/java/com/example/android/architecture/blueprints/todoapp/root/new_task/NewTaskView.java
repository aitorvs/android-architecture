package com.example.android.architecture.blueprints.todoapp.root.new_task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;

/**
 * Top level view for {@link NewTaskBuilder.NewTaskScope}.
 */
class NewTaskView extends FloatingActionButton implements NewTaskInteractor.NewTaskPresenter {

    public NewTaskView(Context context) {
        this(context, null);
    }

    public NewTaskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewTaskView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public Observable<Object> clicks() {
        return RxView.clicks(this);
    }
}
