package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;

@com.minlessika.sdk.metadata.Recordable(
	comodel=EditableDataField.class
)
public interface SimpleDataField extends EditableDataField, TakeLastValueOption, MustEditOnceOption, ReadOnlyOption {	
			
	@Field(label="Valeur par défaut", isMandatory=false)
	String defaultValue() throws IOException;
	
	void defaultValue(String value) throws IOException;
	void update(String code, String name, DataFieldType type, String description) throws IOException;
	
	SimpleDataField EMPTY = new SimpleDataField() {

		@Override
		public int order() throws IOException {
			
			return 0;
		}

		@Override
		public boolean isMandatory() throws IOException {
			
			return false;
		}

		@Override
		public UserScope userScope() throws IOException {
			
			return null;
		}

		@Override
		public void order(int order) throws IOException {
			
			
		}

		@Override
		public void makeMandatory(boolean mandatory) throws IOException {
			
			
		}

		@Override
		public String code() throws IOException {
			
			return null;
		}

		@Override
		public String name() throws IOException {
			
			return null;
		}

		@Override
		public String description() throws IOException {
			
			return null;
		}

		@Override
		public DataFieldType type() throws IOException {
			
			return null;
		}

		@Override
		public DataFieldStyle style() throws IOException {
			
			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
				throws IOException {
			
			
		}

		@Override
		public Long id() {
			
			return 0L;
		}

		@Override
		public UUID guid() throws IOException {
			
			return null;
		}

		@Override
		public LocalDateTime creationDate() throws IOException {
			
			return null;
		}

		@Override
		public Long creatorId() throws IOException {
			
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			
			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {
			
			return null;
		}

		@Override
		public Long ownerId() throws IOException {
			
			return null;
		}

		@Override
		public String tag() throws IOException {
			
			return null;
		}

		@Override
		public Base base() {
			
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public boolean takeLastValue() throws IOException {
			
			return false;
		}

		@Override
		public void takeLastValue(boolean takeLastValue) throws IOException {
			
			
		}

		@Override
		public boolean mustEditOnce() throws IOException {
			
			return false;
		}

		@Override
		public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
			
			
		}

		@Override
		public boolean isReadOnly() throws IOException {
			
			return false;
		}

		@Override
		public void makeReadOnly(boolean readOnly) throws IOException {
			
			
		}

		@Override
		public String defaultValue() throws IOException {
			
			return null;
		}

		@Override
		public void defaultValue(String value) throws IOException {
			
			
		}

		@Override
		public DataSheetModel model() throws IOException {
			
			return null;
		}

		@Override
		public void changeUserScope(UserScope scope) throws IOException {
			
			
		}

		@Override
		public void update(String code, String name, DataFieldType type, String description) throws IOException {
			
			
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {
			
			return null;
		}
		
	};
}
