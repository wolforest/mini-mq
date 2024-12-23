package com.wolf.minimq.store.domain.commitlog.flush;

import com.wolf.common.convention.service.Lifecycle;
import com.wolf.minimq.domain.config.CommitLogConfig;
import com.wolf.minimq.domain.enums.EnqueueStatus;
import com.wolf.minimq.domain.enums.FlushType;
import com.wolf.minimq.domain.service.store.infra.MappedFileQueue;
import com.wolf.minimq.domain.model.vo.EnqueueResult;
import com.wolf.minimq.domain.model.vo.MessageContext;
import java.util.concurrent.CompletableFuture;

/**
 * depend on:
 *  - CommitLogConfig
 *  - MappedFileQueue
 */
public class FlushManager implements Lifecycle {
    private State state = State.INITIALIZING;

    private final CommitLogConfig commitLogConfig;
    private final MappedFileQueue mappedFileQueue;

    private final FlushService commitService;
    private final FlushService flushService;
    private final FlushWatcher flushWatcher;

    public FlushManager(CommitLogConfig commitLogConfig, MappedFileQueue mappedFileQueue) {
        this.commitLogConfig = commitLogConfig;
        this.mappedFileQueue = mappedFileQueue;

        this.flushWatcher = new FlushWatcher();
        this.commitService = new GroupCommitService();

        if (FlushType.SYNC.equals(commitLogConfig.getFlushType())) {
            this.flushService = new RealTimeFlushService();
        } else {
            this.flushService = new GroupFlushService();
        }
    }

    public CompletableFuture<EnqueueStatus> flush(EnqueueResult result, MessageContext context) {
        return null;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void start() {
        this.state = State.STARTING;

        this.flushService.start();

        this.flushWatcher.setDaemon(true);
        this.flushWatcher.start();

        if (commitLogConfig.isEnableWriteCache()) {
            this.commitService.start();
        }

        this.state = State.RUNNING;
    }

    @Override
    public void shutdown() {
        this.state = State.SHUTTING_DOWN;

        this.flushService.shutdown();
        this.flushWatcher.shutdown();

        if (commitLogConfig.isEnableWriteCache()) {
            this.commitService.shutdown();
        }

        this.state = State.TERMINATED;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public State getState() {
        return state;
    }
}
