package com.minlessika.membership.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.Record;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.datasource.ResultStatement;
import com.supervisor.sdk.websockets.WebSocketServer;
import org.takes.Request;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;

/**
 * Fake base.
 * @since 0.1
 */
final class FakeBase implements Base {

    @Override
    public WebSocketServer wsServer() {
        return null;
    }

    @Override
    public UUID currentUserId() {
        return null;
    }

    @Override
    public void start(boolean inTransaction) throws IOException {

    }

    @Override
    public void start(boolean inTransaction, UUID currentUserId) throws IOException {

    }

    @Override
    public void start(boolean inTransaction, Request request) throws IOException {

    }

    @Override
    public void changeUser(UUID userId) throws IOException {

    }

    @Override
    public void commit() throws IOException {

    }

    @Override
    public void rollback() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean withinTransaction() throws IOException {
        return false;
    }

    @Override
    public Connection connection() throws IOException {
        return null;
    }

    @Override
    public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz) throws IOException {
        return null;
    }

    @Override
    public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz, String viewScript) throws IOException {
        return null;
    }

    @Override
    public <A1 extends Recordable> Record<A1> select(Class<A1> clazz, UUID id) throws IOException {
        return null;
    }

    @Override
    public <A1 extends Recordable> void register(Class<A1> clazz) throws IOException {

    }

    @Override
    public <A1 extends Recordable> void createSchemeOf(Class<A1> clazz) throws IOException {

    }

    @Override
    public void createScheme() throws IOException {

    }

    @Override
    public List<ResultStatement> query(String statement, List<Object> parameters) throws IOException {
        return null;
    }

    @Override
    public void update(String statement, List<Object> parameters) throws IOException {

    }
}
