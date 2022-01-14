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
import com.minlessika.supervisor.domain.ParamDataField;
import com.minlessika.supervisor.domain.ParamDataFields;

public final class XeRuleParam extends XeWrap {

	public XeRuleParam(final String root, final ParamDataField item) throws IOException {
		this(transform(root, item));
	}
	
	public XeRuleParam(final ParamDataField item) throws IOException {
		this("rule_param", item);
	}
	
	public XeRuleParam(final List<ParamDataField> items) throws IOException {
		this(new Directives()
                		.add("rule_params")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ParamDataField, Iterable<Directive>>(
	            			            item -> transform("rule_param", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeRuleParam(final ParamDataFields items) throws IOException {
		this(items.items());
	}
	
	public XeRuleParam(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ParamDataField item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("rule_id", item.model().id())
	                    .add("name", item.name())
	                    .add("code", item.code())
	                    .add("value", item.value())
	                    .add("typeId", item.type().name())
	                    .add("type", item.type().toString())
	                    .add("model_id", item.model().id())
	                    .add("model", item.model().name())       
                )                
                .up();
	}

}
