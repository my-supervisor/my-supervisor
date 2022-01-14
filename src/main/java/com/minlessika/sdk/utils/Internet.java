package com.minlessika.sdk.utils;

import org.apache.commons.lang.SystemUtils;

import java.io.IOException;

public final class Internet {

	public boolean isAvailable() {

		boolean isAvailable;

		try {

			Process process;
			if(SystemUtils.IS_OS_WINDOWS) {
				// windows
				process = Runtime.getRuntime().exec("ping -n 3 www.google.com");
			} else if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC_OSX) {
				// linux ou mac os
				process = Runtime.getRuntime().exec("ping -c 3 www.google.com");
			} else {
				throw new IllegalArgumentException("Internet Test connection : this OS is not supported !");
			}

			int x = process.waitFor();
	        if (x == 0) {
	            isAvailable = true;
	        }
	        else {
	            isAvailable = false;
	        }
		} catch (IOException | InterruptedException e) {
			throw new IllegalArgumentException(e);
		}

		return isAvailable;
	}
}
