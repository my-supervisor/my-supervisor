package com.supervisor.sdk.datasource;

import com.supervisor.sdk.app.info.AppInfo;
import com.supervisor.sdk.websockets.WebSocketServer;
import org.takes.Request;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public interface Base {
	
	AppInfo appInfo();
	WebSocketServer wsServer();
	long currentUserId();
	
	void start(boolean inTransaction) throws IOException;
	void start(boolean inTransaction, long currentUserId) throws IOException;
	void start(boolean inTransaction, Request request) throws IOException;
	void changeUser(long userId) throws IOException;
	
	void commit() throws IOException;
	void rollback() throws IOException;
	void close() throws IOException;
	
	boolean withinTransaction() throws IOException;
	
	Connection connection() throws IOException;
	
	<A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz) throws IOException;
	<A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz, String viewScript) throws IOException;
	<A1 extends Recordable> Record<A1> select(Class<A1> clazz, Long id) throws IOException;
	
	<A1 extends Recordable> void register(Class<A1> clazz) throws IOException;
	
	<A1 extends Recordable> void createSchemeOf(Class<A1> clazz) throws IOException;	
	void createScheme() throws IOException;
	
	List<ResultStatement> query(String statement, List<Object> parameters) throws IOException;
	void update(String statement, List<Object> parameters) throws IOException;
	
	Base EMPTY = new Base() {
		
		@Override
		public WebSocketServer wsServer() {
			return null;
		}
		
		@Override
		public <A1 extends Recordable> Record<A1> select(Class<A1> clazz, Long id) throws IOException {
			return null;
		}
		
		@Override
		public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz, String viewScript)
				throws IOException {
			return null;
		}
		
		@Override
		public <A1 extends Recordable> RecordSet<A1> select(Class<A1> clazz) throws IOException {
			return null;
		}
		
		@Override
		public <A1 extends Recordable> void register(Class<A1> clazz) throws IOException {

		}
		
		@Override
		public List<ResultStatement> query(String statement, List<Object> parameters) throws IOException {
			return null;
		}
		
		@Override
		public <A1 extends Recordable> void createSchemeOf(Class<A1> clazz) throws IOException {

		}
		
		@Override
		public void createScheme() throws IOException {

		}
		
		@Override
		public void commit() throws IOException {

		}
		
		@Override
		public AppInfo appInfo() {
			return null;
		}

		@Override
		public void update(String statement, List<Object> parameters) throws IOException {

		}

		@Override
		public Connection connection() throws IOException {
			return null;
		}

		@Override
		public void rollback() throws IOException {
			
			
		}

		@Override
		public void close() throws IOException {
			
			
		}

		@Override
		public long currentUserId() {
			return 0;
		}

		@Override
		public void start(boolean inTransaction, long currentUserId) throws IOException {
			
			
		}

		@Override
		public void start(boolean inTransaction, Request request) throws IOException {
			
			
		}

		@Override
		public boolean withinTransaction() throws IOException {
			
			return false;
		}

		@Override
		public void start(boolean inTransaction) throws IOException {
			
			
		}

		@Override
		public void changeUser(long userId) throws IOException {
			
			
		}
	};
}
