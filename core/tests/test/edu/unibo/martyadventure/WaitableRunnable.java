package test.edu.unibo.martyadventure;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.extension.InvocationInterceptor.Invocation;

class WaitableRunnable implements Runnable {

    private final Invocation<Void> invocation;
    private final CountDownLatch invokeCompleteLatch;
    private final AtomicBoolean wasInvoked;
    private final CompletableFuture<Void> future;


    public WaitableRunnable(final Invocation<Void> invocation) {
        this.invocation = invocation;
        this.invokeCompleteLatch = new CountDownLatch(1);
        this.wasInvoked = new AtomicBoolean(false);
        this.future = new CompletableFuture<Void>();
    }

    @Override
    public void run() {
        if (wasInvoked.compareAndSet(false, true)) {
            try {
                this.invocation.proceed();
                this.future.complete(null);
            } catch (Throwable t) {
                // Captures throws for the thread retrieving the result.
                this.future.completeExceptionally(t);
            }
            this.invokeCompleteLatch.countDown();
        } else {
            try {
                invokeCompleteLatch.await();
            } catch (InterruptedException e) {
                // Cancellation isn't used, so this shouldn't happen.
                this.future.completeExceptionally(e);
            }
        }
    }

    public CompletableFuture<Void> getFuture() {
        return this.future;
    }
}
