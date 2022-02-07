package com.supervisor.domain;

import java.io.IOException;

import com.supervisor.indicator.IndicatorTypes;

public interface Supervisor {
    
	Membership membership() throws IOException;
	Profiles profiles() throws IOException;
	Activities activities() throws IOException;
	IndicatorTypes indicatorTypes() throws IOException;
	DataSheetModels dataSheetModels() throws IOException;
	UserAggregatedModels aggregatedModels() throws IOException;
	UserDataModels dataModels() throws IOException;
	DataSheets dataSheets() throws IOException;
	ActivityNotification activityNotification() throws IOException;
	Sharing sharing() throws IOException;
	Subscription subscription() throws IOException;
	Resources resources() throws IOException;	
	
	ActivityTemplatesPublished activityTemplatesPublished() throws IOException;
	ActivityTemplates activityTemplates() throws IOException;
	
	User user();
}