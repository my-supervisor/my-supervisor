package com.supervisor.billing;

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
