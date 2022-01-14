package com.minlessika.sdk.takes;

import com.minlessika.sdk.datasource.Module;
import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.Fork;
import org.takes.tk.TkWithType;

public final class FkModuleMimes extends FkWrap {

	public FkModuleMimes(final Module module, final Fork fork) {
		super(
			new FkChain(
				new FkRegex(
					module.virtualUrlOf(".+\\.html"),
	                new TkWithType(
	                   new TkRefresh(module.physicalUrl()),
	                   "text/html"
	                )
	             ),
				new FkRegex(
					module.virtualUrlOf("xsl/.+\\.xsl"),
	                new TkWithType(
	                   new TkRefresh(module.physicalUrlOf("xsl")),
	                   "text/xsl"
	                )
	             ),
				new FkRegex(
					module.virtualUrlOf(".+\\.xls"),
	                new TkWithType(
	                   new TkRefresh(module.physicalUrl()),
	                   "application/vnd.ms-excel"
	                )
	             ),
				new FkRegex(
					module.virtualUrlOf(".+\\.xlsx"),
	                new TkWithType(
	                   new TkRefresh(module.physicalUrl()),
	                   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
	                )
	             ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.jpg"),
	                 new TkWithType(
	                     new TkRefresh(module.physicalUrl()),
	                     "image/jpeg"
	                 )
	             ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.png"),
	                 new TkWithType(
	                     new TkRefresh(module.physicalUrl()),
	                     "image/png"
	                 )
	             ),
	             new FkRegex(
	                 module.virtualUrlOf(".+\\.svg"),
	                 new TkWithType(
	                	 new TkRefresh(module.physicalUrl()),
	                     "image/svg+xml"
	                 )
	             ),
	             new FkRegex(
	                 module.virtualUrlOf(".+\\.ico"),
	                 new TkWithType(
	                	 new TkRefresh(module.physicalUrl()),
	                     "image/x-icon"
	                 )
	             ),
	             new FkRegex(
	            	module.virtualUrlOf(".+\\.map"),
	                new TkWithType(
	                   new TkRefresh(module.physicalUrl()),
	                   "text/plain"
	                )
	             ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.ttf"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "application/octet-stream"
	                 )
	              ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.woff"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "application/font-woff"
	                 )
	              ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.woff2"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "application/font-woff"
	                 )
	              ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.css"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "text/css"
	                 )
	              ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.js"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "application/javascript"
	                 )
	              ),
	             new FkRegex(
	            	 module.virtualUrlOf(".+\\.pdf"),
	                 new TkWithType(
	                	new TkRefresh(module.physicalUrl()),
	                    "application/pdf"
	                 )
	              ),
	             fork
			)
		);
	}

}
