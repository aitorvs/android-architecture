package com.example.android.architecture.blueprints.todoapp.root.task_flow.add_task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;

/**
 * Top level view for {@link AddTaskBuilder.AddTaskScope}.
 */
public class AddTaskView extends CoordinatorLayout implements AddTaskInteractor.AddTaskPresenter {

    @BindView(R.id.add_task_title) EditText title;
    @BindView(R.id.add_task_description) EditText description;
    @BindView(R.id.done_button) View doneButton;

    private final Relay<Task> publishRelay = PublishRelay.<Task>create().toSerialized();
    private CharSequence savedToolbarTitle;
    private Task task;

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
        // Request focus and show soft keyboard automatically
        title.post(() -> {
            title.requestFocus();
            Services.showSoftKeyboard(getContext(), title);
        });
    }

    @Override
    public Observable<Task> editOrAddTask(Task addOrEditTask) {
        this.task = addOrEditTask;
        title.setText(addOrEditTask.getTitle());
        description.setText(addOrEditTask.getDescription());
        // saved previous toolbar title and set the one for this view
        savedToolbarTitle = getActionBar().getTitle();
        getActionBar().setTitle(addOrEditTask.isEmpty()
            ? R.string.new_todo
            : R.string.edit_todo);


        return publishRelay;
    }

    @Override
    public void onDetachedFromWindow() {
        // restore previous title
        getActionBar().setTitle(savedToolbarTitle);
        Services.hideSoftKeyboard(getContext(), this);
        super.onDetachedFromWindow();
    }

    @OnClick(R.id.done_button) void done() {
        if (isValidInput()) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            String newTitle = this.title.getText().toString();
            String newDescription = this.description.getText().toString();
            if (task.isEmpty()) {
                // ADD new task
                publishRelay.accept(Task.create(newTitle, newDescription));
            } else {
                // EDIT new task
                publishRelay.accept(task.update(newTitle, newDescription));
            }
        } else {
            Snackbar.make(AddTaskView.this, R.string.todos_cannot_be_empty, Snackbar.LENGTH_LONG).show();
        }
    }

    private ActionBar getActionBar() {
        return Services.getSupportActionBar(getContext());
    }

    private boolean isValidInput() {
        return !Strings.isBlank(title.getText().toString());
    }
}
