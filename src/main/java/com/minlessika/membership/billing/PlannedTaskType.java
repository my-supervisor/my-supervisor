package com.minlessika.membership.billing;

public enum PlannedTaskType {
	
	NONE    {
		@Override
		public String toString() {
            return "Not defined";
        }
	},
	DIRECT  {
		@Override
		public String toString() {
            return "Direct";
        }
	}, 
	DELAYED  {
		@Override
		public String toString() {
            return "Delayed";
        }
	};
}
