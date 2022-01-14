package com.minlessika.sdk.report;

public enum ReportFormat {
	
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	}, 
	PDF    {
		@Override
		public String toString() {
            return "PDF";
        }
	}, 
	POSTSCRIPT  {
		@Override
		public String toString() {
            return "Postscript";
        }
	},
	XLS  {
		@Override
		public String toString() {
            return "XLS";
        }
	};
}
