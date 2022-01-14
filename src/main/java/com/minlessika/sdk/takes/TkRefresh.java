package com.minlessika.sdk.takes;

import com.jcabi.log.VerboseProcess;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkHitRefresh;
import org.takes.facets.fork.TkFork;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkFiles;
import org.takes.tk.TkWrap;

import java.io.File;

public final class TkRefresh extends TkWrap {

	public TkRefresh(final String path) {
		super(
			new TkFork(
	            new FkHitRefresh(
                    new File(path),
                    () -> new VerboseProcess(
                        new ProcessBuilder(
                            "mvn",
                            "generate-resources"
                        )
                    ).stdout(),
                    new TkFiles("./target/classes")
                ),
                new FkFixed(new TkClasspath())
            )
		);
	}
}
