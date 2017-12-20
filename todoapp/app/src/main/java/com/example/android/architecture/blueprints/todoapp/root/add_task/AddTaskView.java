package com.example.android.architecture.blueprints.todoapp.root.add_task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.util.Strings;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Top level view for {@link AddTaskBuilder.AddTaskScope}.
 */
class AddTaskView extends CoordinatorLayout implements AddTaskInteractor.AddTaskPresenter {

    @BindView(R.id.add_task_title) EditText title;
    @BindView(R.id.add_task_description) EditText description;
    @BindView(R.id.done_button) View doneButton;

    private final Relay<Pair<String, String>> publishRelay = PublishRelay.<Pair<String, String>>create().toSerialized();
    private Observable<Boolean> titleObservable;
    private Observable<Boolean> descriptionObservable;

    public AddTaskView(Context context) {
        this(context, null);
    }

    public AddTaskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddTaskView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        titleObservable = RxTextView.textChanges(title)
            .map(input -> !Strings.isBlank(input.toString()));
        descriptionObservable = RxTextView.textChanges(description)
            .map(input -> !Strings.isBlank(input.toString()));

        Observable.combineLatest(
            titleObservable,
            descriptionObservable,
            (titleValid, descriptionValid) -> titleValid && descriptionValid)
            .distinctUntilChanged()
            .subscribe(valid -> doneButton.setEnabled(valid));
    }

    @Override
    public Observable<Pair<String, String>> task() {
        return publishRelay;
    }

    @Override
    public void clear() {
        title.setText("");
        description.setText("");
    }

    @OnClick(R.id.done_button) void done() {
        Timber.d("done() called");
        publishRelay.accept(new Pair<>(title.getText().toString(), description.getText().toString()));
    }
}
