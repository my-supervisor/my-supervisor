package com.minlessika.sdk.tests.utils;

import com.supervisor.domain.User;
import com.supervisor.sdk.datasource.Recordable;
import com.supervisor.sdk.utils.ListOfUniqueRecord;
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
		records.add(new FkRecordable(User.ADMIN_ID));
		records.add(new FkRecordable(User.ADMIN_ID));
		
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(User.ADMIN_ID)
		);
		
		records.add(0, new FkRecordable(User.ADMIN_ID));
		MatcherAssert.assertThat(
				records.size(), 
				Matchers.equalTo(1)
		);
	}
	
	@Test
	public void addAllTest() throws IOException {
		
		List<Recordable> subRecords = new ArrayList<>();
		subRecords.add(new FkRecordable(User.ADMIN_ID));
		subRecords.add(new FkRecordable(User.ANONYMOUS_ID));
		
		List<Recordable> records = new ListOfUniqueRecord<>();
		records.add(new FkRecordable(User.ADMIN_ID));
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
