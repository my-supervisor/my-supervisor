package com.minlessika.supervisor.xe;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.minlessika.supervisor.domain.ExpressionArgType;

public final class XeExpressionArgType extends XeWrap {

	public XeExpressionArgType() {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return new Directives()
                		.add("expression_arg_types")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ExpressionArgType, Iterable<Directive>>(
	            			            item -> transform(item),
	            			            Arrays.asList(ExpressionArgType.values())
	            			            	  .stream()
										      .filter(m -> m != ExpressionArgType.NONE)
										      .collect(Collectors.toList())
    	            		    )
                            )
    	                 )
    					 .up();
            }
        });
	}
	
	private static Iterable<Directive> transform(final ExpressionArgType item) throws IOException {
		return new Directives()
                .add("expression_arg_type")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.name())
	                    .add("name", item.toString())   
                )                
                .up();
	}

}
