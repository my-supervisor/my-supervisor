package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.FormularFunc;

public final class XeFormularFunc extends XeWrap {

	public XeFormularFunc() throws IOException {
		this(Arrays.asList(FormularFunc.values())
                .stream()
                .filter(c -> c != FormularFunc.NONE)
                .collect(Collectors.toList()));
	}
	
	public XeFormularFunc(final String root, final FormularFunc item) throws IOException {
		this(transform(root, item));
	}
	
	public XeFormularFunc(final FormularFunc item) throws IOException {
		this("formular_func", item);
	}
	
	public XeFormularFunc(final List<FormularFunc> items) throws IOException {
		this(new Directives()
                		.add("formular_funcs")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularFunc, Iterable<Directive>>(
	            			            item -> transform("formular_func", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeFormularFunc(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularFunc item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())
	                    .add("nb_args", item.nbArgs())         
                )                
                .up();
	}

}
