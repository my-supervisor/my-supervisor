package com.minlessika.membership.domain;

public enum EventLevel {
	
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	}, 
	INFO  {
		@Override
		public String toString() {
            return "Information";
        }
	}, 
	FINE  {
		@Override
		public String toString() {
            return "Fine";
        }
	}, 
	SEVERE  {
		@Override
		public String toString() {
            return "Severe";
        }
	},
	WARNING {
		@Override
		public String toString() {
            return "Warning";
        }
	};
}
