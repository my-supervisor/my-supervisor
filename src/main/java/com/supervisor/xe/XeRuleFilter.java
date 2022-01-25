package com.supervisor.xe;

import java.io.IOException;

import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.ModelFilter;
import com.supervisor.domain.ModelFilterCondition;
import com.supervisor.domain.ModelFilters;

public final class XeRuleFilter extends XeWrap {

	public XeRuleFilter(final String root, final ModelFilter item) throws IOException {
		this(transform(root, item));
	}
	
	public XeRuleFilter(final ModelFilter item) throws IOException {
		this("rule_filter", item);
	}
	
	public XeRuleFilter(final ModelFilters items) throws IOException {
		this(new Directives()
                		.add("rule_filters")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<ModelFilter, Iterable<Directive>>(
	            			            item -> transform("rule_filter", item),
	            			            items.items()
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeRuleFilter(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final ModelFilter item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("rule_id", item.model().id())
	                    .add("model_id", item.model().id())
	                    .add("model", item.model().name())	                            
                )        
                .add("conditions") 
                .append(
                    new Joined<>(
                		new Mapped<ModelFilterCondition, Iterable<Directive>>(
        			            cd -> transform("condition", cd),
        			            item.conditions().items()
            		    )
                    )
                 )
                .up()
                .up();
	}
	
	private static Iterable<Directive> transform(final String root, final ModelFilterCondition item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("filter_id", item.filter().id())
	                    .add("field_code", item.field().code())
	                    .add("field", item.field().name())
	                    .add("comparator", item.comparator().toString())
	                    .add("comparator_id", item.comparator().name())
	                    .add("value", item.value())        
                )                
                .up();
	}

}
