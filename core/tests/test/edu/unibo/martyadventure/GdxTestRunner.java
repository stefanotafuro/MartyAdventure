package test.edu.unibo.martyadventure;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Implements a headless gdx test runner that runs the tests in a GL context.
 */
public class GdxTestRunner implements ApplicationListener, InvocationInterceptor {

    // FIFO enforced synchronous queue.
    private final Queue<WaitableRunnable> invokeInRenderThread;
    private final Application app;


    private Future<Void> dispatchToRenderThread(final Invocation<Void> invocation) {
        final WaitableRunnable wr = new WaitableRunnable(invocation);
        this.invokeInRenderThread.add(wr);
        return wr.getFuture();
    }

    public GdxTestRunner() {
        this.invokeInRenderThread = new ConcurrentLinkedQueue<WaitableRunnable>();
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

        this.app = new HeadlessApplication(this, conf);
        Gdx.gl = mock(GL20.class);
        Gdx.files = new HeadlessFiles();
        /*
         * Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
         * this.app = new Lwjgl3Application(this, config);
         */
    }

    @Override
    public void create() {}

    @Override
    public void resume() {}

    @Override
    public void render() {
        for (WaitableRunnable invocation : this.invokeInRenderThread) {
            invocation.run();
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    /**
     * Intercept the @Test method calls and runs it in the render thread.
     */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext) throws Throwable {
        Future<Void> handle = dispatchToRenderThread(invocation);
        try {
            handle.get();
        } catch (ExecutionException e) {
            // Extract the cause and re-throw-it.
            throw e.getCause();
        }
    }

    @Override
    public void dispose() {}
}
