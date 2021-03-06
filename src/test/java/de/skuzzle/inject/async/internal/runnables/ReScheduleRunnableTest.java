package de.skuzzle.inject.async.internal.runnables;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cronutils.model.time.ExecutionTime;

import de.skuzzle.inject.async.ScheduledContext;

@RunWith(MockitoJUnitRunner.class)
public class ReScheduleRunnableTest {

    @Mock
    private ScheduledExecutorService executor;
    @Mock
    private ExecutionTime executionTime;
    @Mock
    private Runnable invocation;
    @Mock
    private ScheduledContext context;
    @InjectMocks
    private ReScheduleRunnable subject;

    @Before
    public void setUp() throws Exception {
        final Duration toNext = Duration.ofMillis(5000);
        when(this.executionTime.timeToNextExecution(Mockito.any())).thenReturn(toNext);
    }

    @Test
    public void testRun() throws Exception {
        this.subject.run();
        verify(this.executor).schedule(isA(LatchLockableRunnable.class), eq(5000L),
                eq(TimeUnit.MILLISECONDS));
    }
}
