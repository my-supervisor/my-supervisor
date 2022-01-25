package com.supervisor.sdk.takes;

import com.supervisor.sdk.app.info.AppInfo;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeWrap;
import org.xembly.Directive;
import org.xembly.Directives;

public final class XeRecaptcha extends XeWrap {
	
	public XeRecaptcha(AppInfo info) {
        this("recaptcha", info);
    }
	
	public XeRecaptcha(final CharSequence node, final AppInfo info) {
	        super(
	            new XeSource() {
	                @Override
	                public Iterable<Directive> toXembly() {
	                    return new Directives()
	                        .add(node)
	                        .add("active").set(info.activeRecaptcha()).up()
	                        .add("site-key").set(info.recaptchaSiteKey()).up()
	                        .up();
	                }
	            }
	        );
	    }
}
