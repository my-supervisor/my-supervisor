package com.minlessika.membership.takes;

import com.minlessika.membership.xe.XeReportPreviewParameters;
import com.minlessika.sdk.datasource.Base;
import com.minlessika.sdk.takes.RsPage;
import com.minlessika.sdk.takes.TkBaseWrap;
import org.apache.commons.lang.StringUtils;
import org.cactoos.iterable.Sticky;
import org.takes.facets.previous.RsPrevious;
import org.takes.rq.RqGreedy;
import org.takes.rq.RqHref;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeSource;

public final class TkPrintPreview extends TkBaseWrap {

	public TkPrintPreview(final Base base, String module) {
		super(
				base,
				req -> {
										
					final String reportName = new RqHref.Smart(req).single("name");
					final String returnUrl = new RqHref.Smart(req).single("return_url", StringUtils.EMPTY);
					
					final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
					final XeSource parameters = new XeReportPreviewParameters(form);
					final XeSource reportUrl = new XeAppend("report_url", String.format("/report/%s", reportName));
					
					if(StringUtils.isBlank(returnUrl)) {
						return new RsPage(
								String.format("/com/%s/xsl/report_preview.xsl", module),
								req, 
								base,
								()-> new Sticky<>(
									parameters,
									reportUrl
								)
						);
					} else {
						return new RsPrevious(
							   new RsPage(
										String.format("/com/%s/xsl/report_preview.xsl", module),
										req, 
										base,
										()-> new Sticky<>(
											parameters,
											reportUrl
										)
								), 
								returnUrl
						);
					}
					
				}
		);
	}	
}