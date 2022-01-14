package com.minlessika.sdk.takes;

import com.minlessika.sdk.datasource.Base;
import org.cactoos.iterable.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeSource;
import org.xembly.Directive;
import org.xembly.Directives;

import java.io.IOException;

public abstract class TkForm implements Take {

	protected final Base base;
	
	public TkForm(Base base) {
		this.base = base;
	}
	
	protected static Long getId(Request req) throws IOException {
		return Long.parseLong(new RqHref.Smart(req).single("id", "0"));
	}

	@Override
	public Response act(Request req) throws IOException {
		
		final Long id = getId(req);																		
		XeSource itemToShow;
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		boolean showExistingPostData = form.names().iterator().hasNext();
		if(showExistingPostData) {
			Directives dir = new Directives().add("item");
			
			if(id > 0)
				dir.add("id").set(id).up();
			
			dir.append(postDataToDirs(form));
			
			dir.up();						
			itemToShow = postItemDataToShow(id, req, form, dir);
		}else {
			if(id > 0) {
				itemToShow = preItemDataToShow(id, req);
			}else {
				itemToShow = newItemToShow(req); 
			}
		}
		
		Sticky<XeSource> contentToShow = new Sticky<>(
												contentToShow(req, itemToShow)
											 );						
		return new RsPage(
				xslFormPath(),
				req, 
				base,
				()-> contentToShow
		);
				   		
	}
	
	protected final Iterable<Directive> postDataToDirs(final RqFormSmart form) throws IOException {
		Directives dir = new Directives();
				
		for (String name : form.names()) {
			dir.add(name)
			   .set(form.single(name))
			   .up();												
		}
		
		return dir;
	}
	
	protected XeSource newItemToShow(final Request req) throws IOException {
		return XeSource.EMPTY;
	}
	
	protected XeSource postItemDataToShow(final Long id, final Request req, final RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSource() {
			
			@Override
			public Iterable<Directive> toXembly() throws IOException {
				return dir;
			}
		};
	}
	
	protected abstract String xslFormPath();
	protected abstract Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException;
	protected abstract XeSource preItemDataToShow(final Long id, final Request req) throws IOException;
}
