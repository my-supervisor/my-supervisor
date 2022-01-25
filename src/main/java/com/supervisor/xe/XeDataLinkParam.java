package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.DataLinkParam;
import com.supervisor.domain.DataLinkParams;

public final class XeDataLinkParam extends XeWrap {

	public XeDataLinkParam(final String root, final DataLinkParam item) throws IOException {
		this(transform(root, item));
	}
	
	public XeDataLinkParam(final DataLinkParam item) throws IOException {
		this("data_link_param", item);
	}
	
	public XeDataLinkParam(final DataLinkParams items) throws IOException {
		this(
			new Directives()
        		.add("data_link_params")
				.append(
                    new Joined<>(
                		new Mapped<DataLinkParam, Iterable<Directive>>(
        			            item -> transform("data_link_param", item),
        			            items.items()
            		    )
                    )
                 )
				 .up()
    	);
	}
	
	public XeDataLinkParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final DataLinkParam item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("link_id", item.link().id())
	                    .add("name", item.name())
	                    .add("code", item.code())
	                    .add("value", item.value())
	                    .add("typeId", item.type().name())
	                    .add("type", item.type().toString())     
                )               
                .up();
	}

}
