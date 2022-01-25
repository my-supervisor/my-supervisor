package com.supervisor.xe;

import com.minlessika.map.CleanMap;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public final class XeReportPreviewParameters extends XeWrap {

	public XeReportPreviewParameters(final RqFormSmart form) throws IOException {
		this(new Directives()
                		.add("parameters")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<String, Iterable<Directive>>(
            			            name -> transform("parameter", name, form),
            			            form.names()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeReportPreviewParameters(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final String name, final RqFormSmart form) throws IOException {
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("name", name)
	                    .add("value", form.single(name))                 
                )                
				.up();		
	}
}
