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
import com.minlessika.supervisor.interaction.Interaction;
import com.minlessika.supervisor.interaction.Interactions;

public final class XeInteraction extends XeWrap {

	public XeInteraction(final String root, final Interaction item) throws IOException {
		this(transform(root, item));
	}
	
	public XeInteraction(final Interaction item) throws IOException {
		this("interaction", item);
	}
	
	public XeInteraction(final Interactions items) throws IOException {
		this(items.items());
	}
	
	public XeInteraction(final List<Interaction> items) {
		this(new Directives()
                		.add("interactions")
    					.append(
    	                    new Joined<>(
	                    		new Mapped<Interaction, Iterable<Directive>>(
	            			            item -> transform("interaction", item),
	            			            items
    	            		    )
                            )
    	                 ).up());
	}
	
	public XeInteraction(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Interaction item) throws IOException {
		return new Directives()
                .add(root)
                .add(
                	new CleanMap<String, Object>()
	                	.add("actor", item.actor().name())
	                    .add("actor_id", item.actor().id())
	                    .add("receiver", item.receiver().name())
	                    .add("receiver_id", item.receiver().id())
	                    .add("active", item.active())          
                )                
                .up();
	}

}
