package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.supervisor.domain.WhenCase;
import com.supervisor.domain.WhenCases;

public final class XeWhenCase extends XeWrap {

	public XeWhenCase(final String root, final WhenCase item) throws IOException {
		this(transform(root, item));
	}
	
	public XeWhenCase(final WhenCase item) throws IOException {
		this("when_case", item);
	}
	
	public XeWhenCase(final WhenCases items) throws IOException {
		this(items.items());
	}
	
	public XeWhenCase(final List<WhenCase> items) {
		this(new Directives()
                		.add("when_cases")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<WhenCase, Iterable<Directive>>(
	            			            item -> transform("when_case", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeWhenCase(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final WhenCase item) throws IOException {
		return new Directives()
                .add(root)
                .add("id").set(item.id()).up()
                .add("expression").set(item.expression().name()).up()
                .add("expression_id").set(item.expression().id()).up()
                .append(new XeExpressionArg("left_arg", item.leftArg()).toXembly())
                .append(new XeExpressionArg("right_arg", item.rightArg()).toXembly()) 
                .append(new XeExpressionArg("result", item.result()).toXembly()) 
                .add("comparator").set(item.comparator().toString()).up()
                .add("comparator_id").set(item.comparator().name()).up()
                .up();
	}
}
