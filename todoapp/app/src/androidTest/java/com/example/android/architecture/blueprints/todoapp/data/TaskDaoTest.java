package com.example.android.architecture.blueprints.todoapp.data;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 * {@link Task} database Android tests.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private static final String TASK_1_ID= UUID.randomUUID().toString();
    private static final String TASK_2_ID= UUID.randomUUID().toString();

    private static final Task TASK_1 = new Task(TASK_1_ID, "title 1", "description", false);
    private static final Task TASK_1_BIS = new Task(TASK_1_ID, "title 1 bis", "desc bis", false);
    private static final Task TASK_2 = new Task(TASK_2_ID, "title 2", "description", false);
    private TodoDatabase db;

    @Before
    public void initDb() throws Exception {
        // create in memory DB. No need to clean up after every test
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodoDatabase.class)
            .build();
    }

    @After
    public void closeDb() throws Exception {
        // close DB
        db.close();
    }

    @Test
    public void whenInsertOneTask_shouldSuccess() throws Exception {
        db.taskDao().insert(TASK_1);
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
    }

    @Test
    public void whenInsertMultipleTask_shouldSuccess() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().insert(TASK_2);
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(2));
    }

    @Test
    public void whenSelectTaskById_shouldSuccess() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().insert(TASK_2);
        Task task = db.taskDao().findById(TASK_1_ID).blockingGet();
        assertThat(task, is(TASK_1));
    }

    @Test
    public void whenSelectTaskByWrongId_shouldNotFind() throws Exception {
        db.taskDao().insert(TASK_1);
        Task task = db.taskDao().findById("wrong id").blockingGet(TASK_2);
        assertThat(task, is(TASK_2));
    }

    @Test
    public void whenInsertDuplicateTask_shouldReplace() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().insert(TASK_1_BIS);
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
    }

    @Test
    public void whenDeleteTask_dbShouldBeEmpty() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().deleteTask(TASK_1);
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(0));
    }

    @Test
    public void whenDeleteTask_oneShouldRemain() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().insert(TASK_2);
        db.taskDao().deleteTask(TASK_1);
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(1));
    }

    @Test
    public void whenDeleteAllTasks_dbShouldBeEmpty() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().insert(TASK_2);
        db.taskDao().deleteAllTasks();
        // assert insertion
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(0));
    }

    @Test
    public void whenDeleteTaskByTitle_dbShouldBeEmpty() throws Exception {
        db.taskDao().insert(TASK_1);
        db.taskDao().deleteTaskByTitle(TASK_1.getTitle());
        // assert insertion
        List<Task> tasks = db.taskDao().getTasks().blockingFirst();
        assertThat(tasks.size(), is(0));
    }
}
