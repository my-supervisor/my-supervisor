package com.supervisor.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.datasource.RecordSet;
import com.supervisor.sdk.metadata.Field;
import com.supervisor.sdk.metadata.Recordable;
import com.supervisor.sdk.metadata.Relation;
import org.apache.commons.lang.StringUtils;

import com.supervisor.sharing.Sharable;
import com.supervisor.indicator.Indicator;
import com.supervisor.interaction.Interactable;

@Recordable(
		name="data_link",
		label="Liaison de données"
)
public interface DataLink extends com.supervisor.sdk.datasource.Recordable, Sharable, Interactable {
	
	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(
			label="Indicateur",
			rel= Relation.MANY2ONE
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
		public UUID ownerId() throws IOException {
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
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz) throws IOException {
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
		public <T extends com.supervisor.sdk.datasource.Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			
			return null;
		}

		@Override
		public boolean interacting() throws IOException {
			return false;
		}

		@Override
		public Activity activity() throws IOException {
			
			return null;
		}

		@Override
		public DataDomainDefinition dataDomainDefinition() throws IOException {
			
			return null;
		}

		@Override
		public void update(DataDomainDefinition ddf) throws IOException {
			
			
		}
	};
}
