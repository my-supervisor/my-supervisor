package com.minlessika.supervisor.indicator;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.datasource.RecordSet;
import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.metadata.Field;
import com.minlessika.sdk.metadata.Relation;
import com.minlessika.sdk.time.PeriodicityUnit;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.DataLinks;
import com.minlessika.supervisor.domain.DataModel;
import com.minlessika.supervisor.domain.MappedDataField;

@com.minlessika.sdk.metadata.Recordable(
		name="supervisor_indicator_general_setting",
		label="Configuration d'un indicateur"
)
public interface Indicator extends Recordable {
	
	@Field(
		label="Activité", 
		rel=Relation.MANY2ONE
	)
	Activity activity() throws IOException;
	
	@Field(
		label="Type", 
		rel=Relation.MANY2ONE
	)
	IndicatorType type() throws IOException;
	
	@Field(label="Est un modèle ?")
	boolean isTemplate() throws IOException;
	
	@Field(label="Libellé au singulier")
	String singleLabel() throws IOException;
	
	@Field(label="Libellé au pluriel")
	String pluralLabel() throws IOException;
	
	@Field(label="Description", isMandatory=false)
	String description() throws IOException;
	
	@Field(label="Code")
	String code() throws IOException;
		
	@Field(
		label="Périodicité", 
		rel=Relation.MANY2ONE,
		isMandatory=false
	)
	BasePeriodicity periodicity() throws IOException;
	
	BasePeriodicity periodicity(Activity activity) throws IOException;
	
	String name() throws IOException;
	
	@Field(label="X")
	int sizeX() throws IOException;
	
	@Field(label="Y")
	int sizeY() throws IOException;
	
	@Field(label="Row")
	int row() throws IOException;
	
	@Field(label="Col")
	int col() throws IOException;
	
	IndicatorStaticParams staticParams() throws IOException;
	IndicatorDynamicParams dynamicParams() throws IOException;
	
	DataLinks links() throws IOException;
	
	List<MappedDataField> generateMappedFields() throws IOException;
	List<DataModel> dataModels() throws IOException;
	
	void update(
			String code,
			String singleLabel, 
			String pluralLabel, 
			String description
	) throws IOException;
	
	void locate(int sizeX, int sizeY, int row, int col) throws IOException;
	
	void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException;
	void removePeriodicity() throws IOException;
	
	void calculate(Activity activity) throws IOException;
	void calculate(LocalDate date, Activity activity) throws IOException;	
	Indicator copyTo(Activity activity) throws IOException;
	void copy(Indicator source) throws IOException;
	
	public static double roundNumber(double number, int precision) {

		double numberFormatted;
		
		NumberFormat formatter = DecimalFormat.getInstance(java.util.Locale.US);
		formatter.setMinimumFractionDigits(precision);
		formatter.setMaximumFractionDigits(precision);
		formatter.setRoundingMode(RoundingMode.FLOOR);
		
		try {
			numberFormatted = formatter.parse(formatter.format(number)).doubleValue();
		} catch (ParseException e) {
			throw new NumberFormatException(e.getLocalizedMessage());
		}
		
		return numberFormatted;
	}
	
	Indicator EMPTY = new Indicator() {
		
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
		public void update(String code, String singleLabel, String pluralLabel, String description) throws IOException {
			
			
		}
		
		@Override
		public IndicatorType type() throws IOException {
			
			return null;
		}
		
		@Override
		public IndicatorStaticParams staticParams() throws IOException {
			
			return null;
		}
		
		@Override
		public String singleLabel() throws IOException {
			
			return null;
		}
		
		@Override
		public void removePeriodicity() throws IOException {
			
			
		}
		
		@Override
		public String pluralLabel() throws IOException {
			
			return null;
		}
		
		@Override
		public void periodicity(int number, PeriodicityUnit unit, LocalDate reference, boolean closeInterval) throws IOException {
			
			
		}
		
		@Override
		public BasePeriodicity periodicity(Activity activity) throws IOException {
			
			return null;
		}
		
		@Override
		public BasePeriodicity periodicity() throws IOException {
			
			return null;
		}
		
		@Override
		public String name() throws IOException {
			
			return null;
		}
		
		@Override
		public DataLinks links() throws IOException {
			
			return null;
		}
		
		@Override
		public boolean isTemplate() throws IOException {
			
			return false;
		}
		
		@Override
		public List<MappedDataField> generateMappedFields() throws IOException {
			
			return null;
		}
		
		@Override
		public IndicatorDynamicParams dynamicParams() throws IOException {
			
			return null;
		}
		
		@Override
		public String description() throws IOException {
			
			return null;
		}
		
		@Override
		public Indicator copyTo(Activity activity) throws IOException {
			
			return null;
		}
		
		@Override
		public String code() throws IOException {
			
			return null;
		}
		
		@Override
		public void calculate(LocalDate date, Activity activity) throws IOException {
			
			
		}
		
		@Override
		public void calculate(Activity activity) throws IOException {
			
			
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
			
			return null;
		}
	};
}
