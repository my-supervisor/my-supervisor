package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.membership.domain.Profile;
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.membership.domain.impl.UserOf;
import com.minlessika.supervisor.domain.Activities;
import com.minlessika.supervisor.domain.Activity;
import com.minlessika.supervisor.domain.Supervisor;

public final class XeActivity extends XeWrap {

	public XeActivity(final String root, final Activity field) throws IOException {
		this(transform(root, field));
	}
	
	public XeActivity(final Activity field) throws IOException {
		this("activity", field);
	}
	
	public XeActivity(final Activities fields) throws IOException {
		this("activities", fields);
	}
	
	public XeActivity(final List<Activity> fields) throws IOException {
		this("activities", fields);
	}
	
	public XeActivity(final String root, final Activities fields) throws IOException {
		this(new Directives()
                		.add(root)
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Activity, Iterable<Directive>>(
	            			            item -> transform("activity", item),
	            			            fields.items()
    	            		    )
                            )
    	                 )
    					.add("own_activity_count").set(fields.ownActivities().count()).up()
    					.up());
	}
	
	public XeActivity(final String root, final List<Activity> fields) throws IOException {
		this(new Directives()
                		.add(root)
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Activity, Iterable<Directive>>(
	            			            item -> transform("activity", item),
	            			            fields
    	            		    )
                            )
    	                 )
    					.add("own_activity_count").set(fields.size()).up()
    					.up());
	}
	
	public XeActivity(final String root, final List<Activity> fields, int ownActivitiesCount) throws IOException {
		this(new Directives()
                		.add(root)
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Activity, Iterable<Directive>>(
	            			            item -> transform("activity", item),
	            			            fields
    	            		    )
                            )
    	                 )
    					.add("own_activity_count").set(ownActivitiesCount).up()
    					.up());
	}
	
	public XeActivity(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Activity item) throws IOException {
		
		User user = new UserOf(item);
		Profile profile = user.profileOf(Supervisor.NAME);
		
		boolean canInteracteActivities = profile.hasAccess("INTERACTE_ACTIVITIES");
		boolean canCreateIndicator = profile.hasAccess("NEW_INDICATOR", "1");
		boolean canCreateTemplate = profile.hasAccess("NEW_ACTIVITY_TEMPLATE", "1");
		boolean canCreateIndicatorTemplate = profile.hasAccess("NEW_INDICATOR_TEMPLATE", "1");
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", viewNameOf(item))
	                    .add("description", item.description())
	                    .add("owner_id", item.ownerId())
	                    .add("owner", item.owner().name())
	                    .add("designer_id", item.templateSrc().designer().id())
	                    .add("designer", item.templateSrc().designer().name())
	                    .add("default_shown", item.defaultShown())
	                    .add("is_template", item.isTemplate())
	                    .add("template_src_id", item.templateSrc().id())
	                    .add("periodicity_unit", item.periodicity().unit().toString())
	                    .add("periodicity_unit_id", item.periodicity().unit().name())
	                    .add("periodicity_reference", item.periodicity().reference())
	                    .add("periodicity_number", item.periodicity().number())
	                    .add("periodicity_close_interval", item.periodicity().closeInterval())
	                    .add("version", item.version())
	                    .add("can_create_indicator", canCreateIndicator)
	                    .add("can_create_template", canCreateTemplate)
	                    .add("can_create_indicator_template", canCreateIndicatorTemplate)
	                    .add("can_interacte_activities", canInteracteActivities)
	                    .add("is_up_to_date", item.isUpToDate())
	                    .add("app_owner_name", item.appOwner().module())         
                )                
                .up();
	}

	private static String viewNameOf(Activity activity) throws IOException {
		if(new UserOf(activity).own(activity))
			return activity.name();
		else
			return String.format("%s - %s", activity.name(), new OwnerOf(activity).name());
	}
}
