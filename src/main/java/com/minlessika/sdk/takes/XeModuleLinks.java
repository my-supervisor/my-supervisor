package com.minlessika.sdk.takes;

import com.minlessika.sdk.app.info.AppInfo;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;
import java.util.Map.Entry;

public final class XeModuleLinks extends XeWrap {
	
	public XeModuleLinks(AppInfo info) throws IOException {
        this("module_links", info);
    }
	
	public XeModuleLinks(final CharSequence node, final AppInfo info) throws IOException {
        this(new Directives()
        		.add(node)
				.append(
                    new Joined<>(
                		new Mapped<Entry<String, String>, Iterable<Directive>>(
        			            item -> transform("link", item),
        			            info.moduleLinks().entrySet()
            		    )
                    )
                 )
				.append(minlessikaLink(info).toXembly())
				.up());
    }
	
	private static XeSource minlessikaLink(final AppInfo info) {
		
		Entry<String, String> link = null;
		for (Entry<String, String> lk : info.moduleLinks().entrySet()) {
			if(lk.getKey().equals("minlessika")) {
				link = lk;
				break;
			}
		}
		
		if(link == null)
			return XeSource.EMPTY;
		else
			return new XeAppend(link.getKey(), link.getValue());
	}
	
	public XeModuleLinks(final Iterable<Directive> dir) {
		super(new XeSource() {
            @Override
            public Iterable<Directive> toXembly() throws IOException {
                return dir;
            }
        });
	}
	
	private static Iterable<Directive> transform(final String root, final Entry<String, String> item) {
		return new Directives()
                .add(root)
                .add("module").set(item.getKey()).up()
                .add("url").set(item.getValue()).up()
                .up();
	}
}
