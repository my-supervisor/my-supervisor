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
import com.minlessika.membership.domain.User;
import com.minlessika.membership.domain.impl.OwnerOf;
import com.minlessika.supervisor.domain.DataSheetModel;
import com.minlessika.supervisor.domain.DataSheetModels;

public final class XeDataSheetModel extends XeWrap {

	public XeDataSheetModel(final String root, final DataSheetModel item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataSheetModel(final DataSheetModel item) throws IOException {
		this("data_sheet_model", item);
	}
	
	public XeDataSheetModel(final DataSheetModels items) throws IOException {
		this(items.items());
	}
	
	public XeDataSheetModel(final List<DataSheetModel> items) {
		this(
			new Directives()
            		.add("data_sheet_models")
					.append(
	                    new Joined<>(
                    		new Mapped<DataSheetModel, Iterable<Directive>>(
            			            item -> transform("data_sheet_model", item),
            			            items
	            		    )
                        )
	                 )
					 .up()
    	);
	}
	
	public XeDataSheetModel(final Iterable<Directive> dir) {
		super(
			new XeSource() {
	            @Override
	            public Iterable<Directive> toXembly() throws IOException {
	                return dir;
	            }
	        }
		);
	}
	
	private static Iterable<Directive> transform(final String root, final DataSheetModel item) throws IOException {
		
		final User owner = new OwnerOf(item);
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("full_name", String.format("%s - %s", item.name(), item.activity().name()))
	                    .add("active", item.active())
	                    .add("can_merge_at_same_date", item.canMergeAtSameDate())
	                    .add("description", item.description())
	                    .add("owner_id", item.ownerId())
	                    .add("owner", owner.name())
	                    .add("activity_id", item.activity().id())
	                    .add("activity", item.activity().name())           
                )                
                .up();
	}

}
