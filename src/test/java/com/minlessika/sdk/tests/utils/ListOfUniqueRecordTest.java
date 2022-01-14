package com.minlessika.sdk.tests.utils;

import com.minlessika.sdk.datasource.Recordable;
import com.minlessika.sdk.utils.ListOfUniqueRecord;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ListOfUniqueRecordTest {

	@Test
	public void addTest() throws IOException {
		
		List<Recordable> records = new ListOfUniqueRecord<>();
		records.add(new FkRecordable(1L));
		records.add(new FkRecordable(1L));
		
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(1)
		);
		
		records.add(0, new FkRecordable(1L));
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(1)
		);
	}
	
	@Test
	public void addAllTest() throws IOException {
		
		List<Recordable> subRecords = new ArrayList<>();
		subRecords.add(new FkRecordable(1L));
		subRecords.add(new FkRecordable(2L));
		
		List<Recordable> records = new ListOfUniqueRecord<>();
		records.add(new FkRecordable(1L));
		records.addAll(subRecords);
		
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(2)
		);
		
		records.addAll(0, subRecords);
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(2)
		);
	}
}
