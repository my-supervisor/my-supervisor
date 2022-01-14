package com.minlessika.supervisor.domain;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.lang.StringUtils;

import com.minlessika.membership.domain.Membership;
import com.minlessika.membership.domain.Profiles;
import com.minlessika.membership.domain.User;
import com.minlessika.sdk.datasource.Module;
import com.minlessika.supervisor.indicator.IndicatorTypes;

public interface Supervisor extends Module {
	
	public static final String NAME = "supervisor";
	
	/**
     * Revision.
     */
    public static final String REV = "1.1.1.0085";
    public static final LocalDate RELEASE_DATE = LocalDate.of(2019, 12, 8);
    public static final String PRODUCT_RANGE = "SAAS"; 
    public static final String NOTES = StringUtils.EMPTY;
    
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