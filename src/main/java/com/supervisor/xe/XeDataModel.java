package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import com.supervisor.domain.impl.OwnerOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.User;
import com.supervisor.domain.DataModel;
import com.supervisor.domain.DataModels;

public final class XeDataModel extends XeWrap {

	public XeDataModel(final String root, final DataModel item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataModel(final DataModel item) throws IOException {
		this("data_model", item);
	}
	
	public XeDataModel(final DataModels items) throws IOException {
		this(items.items());
	}
	
	public XeDataModel(final List<DataModel> items) {
		this(
			new Directives()
            		.add("data_models")
					.append(
	                    new Joined<>(
                    		new Mapped<DataModel, Iterable<Directive>>(
            			            item -> transform("data_model", item),
            			            items
	            		    )
                        )
	                 )
					 .up()
    	);
	}
	
	public XeDataModel(final Iterable<Directive> dir) {
		super(
			new XeSource() {
	            @Override
	            public Iterable<Directive> toXembly() throws IOException {
	                return dir;
	            }
	        }
		);
	}
	
	private static Iterable<Directive> transform(final String root, final DataModel item) throws IOException {
		
		final User owner = new OwnerOf(item);
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("code", item.code())
	                    .add("name", item.name())
	                    .add("active", item.active())
	                    .add("description", item.description())
	                    .add("owner_id", item.ownerId())
	                    .add("owner", owner.name())
	                    .add("activity_id", item.activity().id())
	                    .add("activity", item.activity().name())
	                    .add("viewName", String.format("%s - %s", item.code(), item.name()))        
                )                
                .up();
	}

}
