package com.supervisor.xe;

import java.io.IOException;
import java.util.List;

import com.supervisor.sdk.utils.OptUUID;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import com.minlessika.map.CleanMap;
import com.supervisor.domain.FormularCondition;
import com.supervisor.domain.FormularDataField;
import com.supervisor.domain.FormularExpression;
import com.supervisor.domain.FormularDataFields;

public final class XeRuleFormular extends XeWrap {

	public XeRuleFormular(final String root, final FormularDataField item) throws IOException {
		this(transform(root, item));
	}
	
	public XeRuleFormular(final FormularDataField item) throws IOException {
		this("rule_formular", item);
	}
	
	public XeRuleFormular(final FormularDataFields items) throws IOException {
		this(items.items());
	}
	
	public XeRuleFormular(final List<FormularDataField> items) {
		this(new Directives()
                		.add("rule_formulars")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<FormularDataField, Iterable<Directive>>(
	            			            item -> transform("rule_formular", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeRuleFormular(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final FormularDataField item) throws IOException {
		
		FormularCondition condition = item.condition();
		
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("rule_id", item.model().id())
	                    .add("name", item.name())
	                    .add("code", item.code())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("model_id", item.model().id())
	                    .add("model", item.model().name())        
                )                
                .add("expressions")
                .append(
                    new Joined<>(
                		new Mapped<FormularExpression, Iterable<Directive>>(
        			            cd -> transform("expression", cd),
        			            item.expressions().items()
            		    )
                    )
                 )
                .up()
                .add("condition")
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", condition.id())
	                    .add("param_id", condition.param().id())
	                    .add("param_code", condition.param().code())
	                    .add("comparator_id", condition.comparator().name())
	                    .add("state", condition.id() == null ? "removed" : "added")
	                    .add("value", condition.value())
	                    .add("default_value", condition.defaultValue())         
                )                
                .up()                
                .up();
	}
	
	private static Iterable<Directive> transform(final String root, final FormularExpression item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("id", item.id())
	                    .add("name", item.name())
	                    .add("text", item.toText())
	                    .add("formular_id", item.formular().id())
	                    .add("type", item.type().toString())
	                    .add("type_id", item.type().name())
	                    .add("order", item.no())           
                )                
                .up();
	}

}
