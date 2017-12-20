package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
interface TaskDao {
    /**
     * Select all tasks from the table
     * @return list of all tasks
     */
    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getTasks();

    /**
     * Select all tasks by title.
     * @param title title of the task
     * @return {@link List} of {@link Task} that match the title.
     */
    @Query("SELECT * FROM tasks WHERE title = :title")
    Flowable<List<Task>> getTasksByTitle(String title);

    /**
     * Insert one {@link Task}
     * @param task the task to be inserted. If the task already exist, it'll be replaced.
     */
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    /**
     * Delete a {@link Task}
     * @param task {@link Task} to be deleted
     */
    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM tasks WHERE title = :title")
    void deleteTaskByTitle(String title);

    /**
     * Delete all the tasks in the database
     */
    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("UPDATE tasks SET done = :completed WHERE id = :taskId")
    void completeTask(int taskId, boolean completed);
}
