package test.edu.unibo.martyadventure;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * Implements a headless gdx test runner that runs the tests in a GL context.
 */
public class GdxTestRunner implements ApplicationListener, BeforeAllCallback, InvocationInterceptor {

    // FIFO enforced synchronous queue.
    private Queue<WaitableRunnable> invokeInRenderThread = new SynchronousQueue<WaitableRunnable>(true);


    private Future<Void> dispatchToRenderThread(final Invocation<Void> invocation) {
        final WaitableRunnable wr = new WaitableRunnable(invocation);
        this.invokeInRenderThread.add(wr);
        return wr.getFuture();
    }

    @Override
    public void create() {}

    @Override
    public void resume() {}

    @Override
    public void render() {
        synchronized (invokeInRenderThread) {
            for (WaitableRunnable invocation : invokeInRenderThread) {
                invocation.run();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void dispose() {}

    /**
     * Initialize the headless backend and GL context.
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

        new HeadlessApplication(this, conf);
        Gdx.gl = mock(GL20.class);
    }

    /**
     * Intercept the @Test method calls and runs it in the render thread.
     */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext) throws Throwable {
        Future<Void> handle = dispatchToRenderThread(invocation);
        handle.get();
    }
}
