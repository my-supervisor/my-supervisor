package com.supervisor.sdk.takes;

import com.supervisor.sdk.datasource.Base;
import com.supervisor.sdk.utils.OptUUID;
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
import java.util.UUID;

public abstract class TkForm implements Take {

	protected final Base base;
	
	public TkForm(Base base) {
		this.base = base;
	}
	
	protected static OptUUID getId(Request req) throws IOException {
		return new OptUUID(new RqHref.Smart(req).single("id", "0"));
	}

	@Override
	public Response act(Request req) throws IOException {
		
		final OptUUID id = getId(req);
		XeSource itemToShow;
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		boolean showExistingPostData = form.names().iterator().hasNext();
		if(showExistingPostData) {
			Directives dir = new Directives().add("item");
			
			if(id.isPresent())
				dir.add("id").set(id).up();
			
			dir.append(postDataToDirs(form));
			
			dir.up();						
			itemToShow = postItemDataToShow(id, req, form, dir);
		}else {
			if(id.isPresent()) {
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
	
	protected XeSource postItemDataToShow(final OptUUID id, final Request req, final RqFormSmart form, final Iterable<Directive> dir) throws IOException {
		return new XeSource() {
			
			@Override
			public Iterable<Directive> toXembly() throws IOException {
				return dir;
			}
		};
	}
	
	protected abstract String xslFormPath();
	protected abstract Iterable<XeSource> contentToShow(final Request req, final XeSource itemToShow) throws IOException;
	protected abstract XeSource preItemDataToShow(final OptUUID id, final Request req) throws IOException;
}
