package com.wolf.minimq.store.domain.index;

import com.wolf.minimq.domain.service.store.domain.CommitLogDispatcher;
import com.wolf.minimq.domain.service.store.manager.IndexManager;
import com.wolf.minimq.store.server.StoreContext;

public class DefaultIndexManager implements IndexManager {
    @Override
    public void initialize() {
        CommitLogDispatcher dispatcher = StoreContext.getBean(CommitLogDispatcher.class);
        IndexCommitLogHandler handler = new IndexCommitLogHandler();
        dispatcher.registerHandler(handler);


    }

    @Override
    public void start() {

    }

    @Override
    public void shutdown() {

    }



    @Override
    public void cleanup() {

    }

    @Override
    public State getState() {
        return null;
    }
}
