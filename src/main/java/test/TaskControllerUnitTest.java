package test;

import com.wecode.controller.TaskController;
import com.wecode.entity.Project;
import com.wecode.entity.Task;
import com.wecode.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class TaskControllerUnitTest {

    Task task;

    @InjectMocks
    TaskController taskController;

    @Mock
    TaskService taskService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        task = new Task();
        task.setDescription("Test Task");
        task.setLabel("Test");
        assert task.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";
        task.setStatus(1);
        task.setProject(new Project());
    }

    @Test
    void listTask() {
        List<Task> list = taskController.listTask();
        assertNotNull(list);
    }

    @Test
    void getOne() {
        when(taskService.findById(anyLong())).thenReturn(task);
        Task ts = taskController.getOne(task.getId());

        assert task.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";
        assert ts.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";

        assertNotNull(ts);
        assertNotNull(ts);
        assertEquals(ts.getDescription(), task.getDescription());
        assertEquals(ts.getStatus(), task.getStatus());
        assertEquals(ts.getProject(), task.getProject());
        assertEquals(ts.getId(), task.getId());
        assertEquals(ts.getLabel(), task.getLabel());
    }

    @Test
    void saveTask() {
        when (taskService.findById(anyLong())).thenReturn(task);

        assert task.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";

        taskService.save(task);
        assertNotNull(task.getLabel());
        assertNotNull(task.getDescription());
        assertNotNull(task.getProject());
        assertNotNull(task.getStatus());


        Task ts = taskController.getOne(task.getId());

        assert ts.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";

        assertEquals(ts.getDescription(), task.getDescription());
        assertEquals(ts.getLabel(), task.getLabel());
        assertEquals(ts.getId(), task.getId());
        assertEquals(ts.getProject(), task.getProject());
        assertEquals(ts.getStatus(), task.getStatus());
    }

    @Test
    void update() {
        String newLabel= "Update Test";
        String oldLabel  = task.getLabel();
        task.setLabel(newLabel);

        when (taskController.getOne(anyLong())).thenReturn(task);
        Task ts = taskService.findById(task.getId());

        assert task.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";
        assert ts.getLabel().length() >=3 : "Task Label Length Should Be at min 3 chars !";

        assertEquals(ts.getLabel(), newLabel);

        assertNotNull(ts.getDescription());
        assertNotNull(ts.getProject());
        assertNotNull(ts.getStatus());
    }

    @Test
    void delete() {
        long projectId=42;

        taskController.delete(projectId);

        verify(taskService, times(1)).deleteById(eq(projectId));
    }
}