package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;

@com.minlessika.sdk.metadata.Recordable(
	name="supervisor_expression_arg", 
	label="Argument d'une expression"
)
public interface ExpressionArg extends Recordable {
	
	String name() throws IOException;
	
	@Field(
			label="Expression",
			rel=Relation.MANY2ONE
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
		public Long ownerId() throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}
		
		@Override
		public Long lastModifierId() throws IOException {
			
			return null;
		}
		
		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}
		
		@Override
		public Long id() {
			
			return null;
		}
		
		@Override
		public UUID guid() throws IOException {
			
			return null;
		}
		
		@Override
		public Long creatorId() throws IOException {
			
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
