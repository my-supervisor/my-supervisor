package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataLinks;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.MappedDataField;
import com.minlessika.supervisor.domain.TemplateState;

@com.minlessika.sdk.metadata.Recordable( 
	comodel=Indicator.class
)
public interface IndicatorTemplate extends Indicator {

	@Field(label="Libellé")
	String name() throws IOException;
	
	@Field(label="Tags", isMandatory=false)
	List<String> tags() throws IOException;
	
	@Field(label="Etat")
	TemplateState state() throws IOException;
	
	void update(String name, String description) throws IOException;
	void tags(List<String> tags) throws IOException;
	
	IndicatorTemplate EMPTY = new IndicatorTemplate() {
		
		@Override
		public String tag() throws IOException {
			return null;
		}
		
		@Override
		public Long ownerId() throws IOException {
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
			return Base.EMPTY;
		}
		
		@Override
		public void update(String code, String singleLabel, String pluralLabel,
				String description) throws IOException {

			throw new UnsupportedOperationException();
		}
		
		@Override
		public IndicatorType type() throws IOException {
			return null;
		}
		
		@Override
		public String singleLabel() throws IOException {
			return null;
		}
		
		@Override
		public DataLinks links() throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public String pluralLabel() throws IOException {
			return null;
		}
		
		@Override
		public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public BasePeriodicity periodicity() throws IOException {
			return null;
		}
		
		@Override
		public IndicatorStaticParams staticParams() throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public IndicatorDynamicParams dynamicParams() throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean isTemplate() throws IOException {
			return false;
		}
		
		@Override
		public List<MappedDataField> generateMappedFields() throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public String description() throws IOException {
			return null;
		}
		
		@Override
		public String code() throws IOException {
			return null;
		}
		
		@Override
		public void update(String name, String description) throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void tags(List<String> tags) throws IOException {
			throw new UnsupportedOperationException();			
		}
		
		@Override
		public List<String> tags() throws IOException {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public TemplateState state() throws IOException {
			return TemplateState.NONE;
		}
		
		@Override
		public String name() throws IOException {
			return null;
		}

		@Override
		public void calculate(Activity activity) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void calculate(LocalDate date, Activity activity) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T extends Recordable> RecordSet<T> listOf(Class<T> clazz, String viewScript) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void removePeriodicity() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public BasePeriodicity periodicity(Activity activity) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Indicator copyTo(Activity activity) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void copy(Indicator source) throws IOException {
			
		}

		@Override
		public Activity activity() throws IOException {
			return Activity.EMPTY;
		}

		@Override
		public int sizeX() throws IOException {
			return 0;
		}

		@Override
		public int sizeY() throws IOException {
			return 0;
		}

		@Override
		public int row() throws IOException {
			return 0;
		}

		@Override
		public int col() throws IOException {
			return 0;
		}

		@Override
		public void locate(int sizeX, int sizeY, int row, int col) throws IOException {
			throw new UnsupportedOperationException("indicator#locate"); 
		}

		@Override
		public List<DataModel> dataModels() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
