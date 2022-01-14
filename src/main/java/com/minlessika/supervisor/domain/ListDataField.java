package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.minlessika.membership.domain.UserScope;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.OrderDirection;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.supervisor.domain.bi.BiResult;

@com.minlessika.sdk.metadata.Recordable(
	comodel=EditableDataField.class
)
public interface ListDataField extends EditableDataField, MustEditOnceOption, ReadOnlyOption, ListCanBeUpdatedOption {
	
	DataField fieldToDisplay() throws IOException;	
	DataField orderField() throws IOException;
	
	@Field(label="Order direction")
	OrderDirection orderDirection() throws IOException;
	
	@Field(label="Limit", name="nb_max_values")
	int limit() throws IOException;
	
	ListDataFieldSources sources() throws IOException;
		
	BiResult value(String reference) throws IOException;
	BiResult values() throws IOException;
	BiResult values(String filter, long start) throws IOException;
	BiResult values(String filter, long start, int limit) throws IOException;
	
	void update(OrderDirection orderDirection, int limit) throws IOException;
	void update(String code, String name, DataFieldType type, String description) throws IOException;
	
	boolean isBasedOn(DataModel model) throws IOException;
	boolean isStrictBasedOn(DataModel model) throws IOException;
	
	ListDataField EMPTY = new ListDataField() {

		@Override
		public int order() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isMandatory() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public UserScope userScope() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void order(int order) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void makeMandatory(boolean mandatory) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String code() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String name() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String description() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFieldType type() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataFieldStyle style() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, DataFieldStyle style, String description)
				throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Long id() {
			// TODO Auto-generated method stub
			return 0L;
		}

		@Override
		public UUID guid() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalDateTime creationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long creatorId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public LocalDateTime lastModificationDate() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long lastModifierId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Long ownerId() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String tag() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Base base() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean mustEditOnce() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void makeMustEditOnce(boolean mustEditOnce) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isReadOnly() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void makeReadOnly(boolean readOnly) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DataField fieldToDisplay() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataField orderField() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OrderDirection orderDirection() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int limit() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ListDataFieldSources sources() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BiResult value(String reference) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BiResult values(String filter, long start) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BiResult values(String filter, long start, int limit) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(OrderDirection orderDirection, int limit) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DataSheetModel model() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void changeUserScope(UserScope scope) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean canBeUpdated() throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void makeCanBeUpdated(boolean update) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public BiResult values() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(String code, String name, DataFieldType type, String description) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isBasedOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isStrictBasedOn(DataModel model) throws IOException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public DataFieldDependencies dependencies() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

	};
}
