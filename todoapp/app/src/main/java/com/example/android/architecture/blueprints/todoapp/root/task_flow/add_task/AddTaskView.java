package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.example.android.architecture.blueprints.todoapp.util.Services;
import com.example.android.architecture.blueprints.todoapp.util.Strings;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Top level view for {@link AddTaskBuilder.AddTaskScope}.
 */
public class AddTaskView extends CoordinatorLayout implements AddTaskInteractor.AddTaskPresenter {

    @BindView(R.id.add_task_title) EditText title;
    @BindView(R.id.add_task_description) EditText description;
    @BindView(R.id.done_button) View doneButton;

    private final Relay<TaskViewModel> publishRelay = PublishRelay.<TaskViewModel>create().toSerialized();
    private CharSequence savedToolbarTitle;

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
        // title view observable
        RxTextView.textChanges(title)
            .map(input -> !Strings.isBlank(input.toString()))
            .subscribe(hasInput -> doneButton.setEnabled(hasInput));
    }

    @Override
    public Observable<TaskViewModel> task() {
        return publishRelay;
    }

    @Override
    public void editTask(Task editableTask) {
        title.setText(editableTask.getTitle());
        description.setText(editableTask.getDescription());
        // saved previous toolbar title and set the one for this view
        savedToolbarTitle = getActionBar().getTitle();
        getActionBar().setTitle(editableTask.isEmpty()
            ? R.string.new_todo
            : R.string.edit_todo);
    }

    @Override
    public void onDetachedFromWindow() {
        // restore previous title
        getActionBar().setTitle(savedToolbarTitle);
        super.onDetachedFromWindow();
    }

    @Override
    public void clear() {
        title.setText("");
        description.setText("");
    }

    @OnClick(R.id.done_button) void done() {
        Timber.d("done() called");
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
        publishRelay.accept(new TaskViewModel(title.getText().toString(), description.getText().toString()));
    }

    private ActionBar getActionBar() {
        return Services.getSupportActionBar(getContext());
    }
}
