package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.DataSheet;
import com.minlessika.supervisor.domain.DataSheets;

public final class XeDataSheet extends XeWrap {

	public XeDataSheet(final String root, final DataSheet item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataSheet(final DataSheet item) throws IOException {
		this("data_sheet", item);
	}
	
	public XeDataSheet(final DataSheets items) throws IOException {
		this(
			new Directives()
        		.add("data_sheets")
				.append(
                    new Joined<>(
                		new Mapped<DataSheet, Iterable<Directive>>(
        			            item -> transform("data_sheet", item),
        			            items.items()
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeDataSheet(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataSheet item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.model().name())
	                    .add("owner_id", item.ownerId())
	                    .add("owner", item.owner().name())
	                    .add("creator_id", item.creatorId())
	                    .add("creator", item.creator().name())
	                    .add("reference", item.reference())
	                    .add("date", item.date().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
	                    .add("creation_date", item.creationDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
	                    .add("model_id", item.model().id())              
                )                         
                .up();
	}

}
