package org.codehaus.griffon.runtime.javafx;

import com.sun.javafx.tk.Toolkit;
import griffon.core.ExceptionHandler;
import javafx.application.Platform;
import org.codehaus.griffon.runtime.core.threading.AbstractUIThreadManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static java.util.Objects.requireNonNull;

/**
 * @author Dean Iverson
 */
public class JavaFXUIThreadManager extends AbstractUIThreadManager {
    private static final Logger LOG = LoggerFactory.getLogger(JavaFXUIThreadManager.class);
    @Inject
    private ExceptionHandler exceptionHandler;

    /**
     * True if the current thread is the UI thread.
     */
    public boolean isUIThread() {
        return Platform.isFxApplicationThread();
    }

    @Override
    public void runInsideUIAsync(@Nonnull Runnable runnable) {
        requireNonNull(runnable, ERROR_RUNNABLE_NULL);
        Platform.runLater(runnable);
    }

    @Override
    public void runInsideUISync(final @Nonnull Runnable runnable) {
        requireNonNull(runnable, ERROR_RUNNABLE_NULL);
        if (isUIThread()) {
            runnable.run();
        } else {
            FutureTask<Void> task = new FutureTask<>(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } catch (Throwable throwable) {
                        exceptionHandler.uncaughtException(Thread.currentThread(), throwable);
                    }
                }
            }, null);

            Platform.runLater(task);
            try {
                LOG.info("" + System.getSecurityManager());
                task.get();
            } catch (InterruptedException | ExecutionException e) {
                exceptionHandler.uncaughtException(Thread.currentThread(), e);
            }
        }
    }
}
