package com.example.android.architecture.blueprints.todoapp.root.task_flow.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.Task;
import java.util.List;

import static com.example.android.architecture.blueprints.todoapp.util.Preconditions.checkNotNull;

final class TasksAdapter extends BaseAdapter {
    private List<Task> tasks;
    private TaskItemListener itemListener;

    TasksAdapter(List<Task> tasks, TaskItemListener itemListener) {
        setList(tasks);
        this.itemListener = itemListener;
    }

    void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<Task> tasks) {
        this.tasks = checkNotNull(tasks);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
        }
        final Task task = getItem(i);
        TextView titleTV = rowView.findViewById(R.id.title);
        titleTV.setText(task.getTitle());
        CheckBox completeCB = rowView.findViewById(R.id.complete);
        // Active/completed task UI
        completeCB.setChecked(task.isDone());
        if (task.isDone()) {
            rowView.setBackgroundDrawable(
                viewGroup.getContext().getResources().getDrawable(R.drawable.list_completed_touch_feedback));
        } else {
            rowView.setBackgroundDrawable(viewGroup.getContext().getResources().getDrawable(R.drawable.touch_feedback));
        }
        completeCB.setOnClickListener(v -> {
            if (!task.isDone()) {
                itemListener.onCompleteTaskClick(task);
            } else {
                itemListener.onActivateTaskClick(task);
            }
        });
        rowView.setOnClickListener(view1 -> itemListener.onTaskClick(task));
        return rowView;
    }

    public interface TaskItemListener {
        void onTaskClick(Task task);
        void onCompleteTaskClick(Task task);
        void onActivateTaskClick(Task task);
    }
}
