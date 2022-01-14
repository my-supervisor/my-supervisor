package com.minlessika.supervisor.domain.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minlessika.sdk.utils.logging.Logger;
import com.minlessika.sdk.utils.logging.MLogger;
import com.minlessika.sdk.websockets.WebSocketServer;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.ActivityNotification;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.IndicatorDependencies;
import com.minlessika.supervisor.domain.bi.impl.PgDataWarehouse;
import com.minlessika.supervisor.server.ActivityRealtime;
import com.minlessika.supervisor.server.ActivityRealtime.ActivityData;
import com.minlessika.supervisor.server.ActivityRealtime.IndicatorData;

public final class ActivityNotificationImpl implements ActivityNotification {

	private static final Logger logger = new MLogger(PgDataWarehouse.class);
	
	private final WebSocketServer server;
	private static final String ACTIVITY_NAME = "activity";
	
	public ActivityNotificationImpl(WebSocketServer server) {
		this.server = server;
	}
	
	@Override
	public void publish(final DataSheet sheet) throws IOException {
		publish(sheet.model());
		
	}

	@Override
	public void publish(DataSheetModel model) throws IOException {
		
		final List<ActivityData> activityDatas = new ArrayList<>();
		final IndicatorDependencies indicatorsThatDependsOn = new DataSheetIndicatorDependencies(model);
		
		indicatorsThatDependsOn
		     .items()
             .stream()
             .forEach(item -> {
            	 try {
            		 Activity activity = item.activity();
            		 if(server.hasHeader(ActivityRealtime.class, ACTIVITY_NAME, activity.id().toString())) {
							
							final ActivityData activityData; 
							
							if(activityDatas.stream().noneMatch(c -> c.getId() == activity.id())) {
								activityData = new ActivityData(activity.id(), new ArrayList<>());
								activityDatas.add(activityData);
							}else {
								activityData = activityDatas.stream()
										                    .filter(c -> c.getId() == activity.id())
										                    .findFirst()
										                    .get();
							}
							
							activityData.add(new IndicatorData(item.code()));
					}
				} catch (IOException e) {
					logger.error(e);
					throw new RuntimeException(e);
				}
             });
		
		for (ActivityData data : activityDatas) {
			
			server.publish(
					ActivityRealtime.class, 
					data, 
					ACTIVITY_NAME,
					data.getId().toString()
			);
		}
	}
}
