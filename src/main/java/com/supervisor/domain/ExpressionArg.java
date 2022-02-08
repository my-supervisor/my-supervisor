package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Relation;

@com.supervisor.sdk.metadata.Recordable(
	name="supervisor_expression_arg", 
	label="Argument d'une expression"
)
public interface ExpressionArg extends com.supervisor.sdk.datasource.Recordable {
	
	String name() throws IOException;
	
	@Field(
			label="Expression",
			rel= Relation.MANY2ONE
	)
	FormularExpression expression() throws IOException;
	
	@Field(label="Ordre")
	int no() throws IOException;
	
	@Field(label="Type")
	ExpressionArgType type() throws IOException;
	
	void update(String value, DataFieldType valueType) throws IOException;
	void update(EditableDataField field) throws IOException;
	void update(ParamDataField param) throws IOException;	
	void update(FormularExpression expression) throws IOException;
	void update(FormularDataField formular) throws IOException;
	
	ExpressionArg EMPTY = new ExpressionArg() {
		
		@Override
		public String tag() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}
		
		@Override
		public UUID lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public UUID id() {
			
			return null;
		}
		
		@Override
		public UUID creatorId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Base base() {
			
			return null;
		}
		
		@Override
		public FormularExpression expression() throws IOException {
			
			return null;
		}

		@Override
		public String name() throws IOException {
			
			return null;
		}

		@Override
		public ExpressionArgType type() throws IOException {
			
			return null;
		}

		@Override
		public int no() throws IOException {
			
			return 0;
		}

		@Override
		public void update(String value, DataFieldType valueType) throws IOException {
			
			
		}

		@Override
		public void update(EditableDataField field) throws IOException {
			
			
		}

		@Override
		public void update(ParamDataField param) throws IOException {
			
			
		}

		@Override
		public void update(FormularExpression expression) throws IOException {
			
			
		}

		@Override
		public void update(FormularDataField formular) throws IOException {
			
			
		}
	};
}
