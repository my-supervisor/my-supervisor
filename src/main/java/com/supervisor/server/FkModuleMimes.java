package com.supervisor.server;

import org.takes.facets.fork.FkChain;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.Fork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkWithType;

public final class FkModuleMimes extends FkWrap {

	public FkModuleMimes(final Fork fork) {
		super(
			new FkChain(
				new FkRegex(
					"/org/takes/.+\\.xsl",
					new TkClasspath()
				),
				new FkRegex(
					"/html/.+",
					new TkWithType(new TkClasspath(), "text/html")
				),
				new FkRegex(
					"/xls/.+",
					new TkWithType(new TkClasspath(), "application/vnd.ms-excel")
				),
				new FkRegex(
					"/xlsx/.+",
					new TkWithType(
						new TkClasspath(),
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
					)
				),
				new FkRegex(
					"/img/.+\\.svg",
					new TkWithType(new TkClasspath(), "image/svg+xml")
				),
				new FkRegex(
					"/img/.+",
					new TkWithType(new TkClasspath(), "image/*")
				),
				new FkRegex(
					"/fonts/.+\\.svg",
					new TkWithType(new TkClasspath(), "image/svg+xml")
				),
				new FkRegex(
					"/fonts/.+\\.otf",
					new TkWithType(new TkClasspath(), "application/vnd.ms-outlook")
				),
				new FkRegex(
					"/fonts/.+\\.eot",
					new TkWithType(new TkClasspath(), "application/vnd.ms-fontobject")
				),
				new FkRegex(
					"/fonts/.+\\.ttf",
					new TkWithType(new TkClasspath(), "application/octet-stream")
				),
				new FkRegex(
					"/fonts/.+\\.woff",
					new TkWithType(new TkClasspath(), "application/font-woff")
				),
				new FkRegex(
					"/fonts/.+\\.woff2",
					new TkWithType(new TkClasspath(), "application/font-woff")
				),
				new FkRegex(
					"/css/.+\\.css",
					new TkWithType(new TkClasspath(), "text/css")
				),
				new FkRegex(
					"/js/.+\\.js",
					new TkWithType(new TkClasspath(), "application/javascript")
				),
				new FkRegex(
					"/js/.+\\.map",
					new TkWithType(new TkClasspath(), "text/plain")
				),
				new FkRegex(
					"/pdf/.+\\.pdf",
					new TkWithType(new TkClasspath(), "application/pdf")
				),
				fork
			)
		);
	}

}
