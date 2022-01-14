package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.supervisor.data.sharing.Sharable;
import com.minlessika.supervisor.indicator.Indicator;
import com.minlessika.supervisor.interaction.Interactable;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_data_link", 
		label="Liaison de données"
)
public interface DataLink extends Recordable, Sharable, Interactable {
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(
			label="Indicateur",
			rel=Relation.MANY2ONE
	)
	Indicator indicator() throws IOException;
	
	@Field(
			label="Modèle agrégé",
			rel=Relation.MANY2ONE
	)
	DataModel model() throws IOException;
	
	@Field(label="Est activée ?")
	boolean active() throws IOException;
	
	@Field(label="Data domain definition")
	DataDomainDefinition dataDomainDefinition() throws IOException;
	
	boolean interacting() throws IOException;
	
	MappedDataFields mappings() throws IOException;
	DataLinkParams params() throws IOException;
	
	void activate(boolean active) throws IOException;
	void update(DataDomainDefinition ddf) throws IOException;
	void update(String newName) throws IOException;
	
	DataLink EMPTY = new DataLink() {
		
		@Override
		public String tag() throws IOException {
			return StringUtils.EMPTY;
		}
		
		@Override
		public Long ownerId() throws IOException {
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
			return 0L;
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
		public DataModel model() throws IOException {
			return DataModel.EMPTY;
		}
		
		@Override
		public MappedDataFields mappings() throws IOException {
			throw new UnsupportedOperationException("#mappings");
		}

		@Override
		public Indicator indicator() throws IOException {
			throw new UnsupportedOperationException("#indicator");
		}

		@Override
		public boolean active() throws IOException {
			return false;
		}

		@Override
		public void activate(boolean active) throws IOException {
			throw new UnsupportedOperationException("#activate");
		}

		@Override
		public Base base() {
			return Base.EMPTY;
		}
		
		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
			throw new UnsupportedOperationException("#listOf");
		}

		@Override
		public DataLinkParams params() throws IOException {
			throw new UnsupportedOperationException("#params");
		}

		@Override
		public String name() throws IOException {
			return StringUtils.EMPTY;
		}

		@Override
		public void update(String newName) throws IOException {
			throw new UnsupportedOperationException("#newName");
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			return false;
		}

		@Override
		public Activity activity() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DataDomainDefinition dataDomainDefinition() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(DataDomainDefinition ddf) throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
}
