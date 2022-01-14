package com.minlessika.membership.billing;

public enum PlannedTaskStatus {
	
	NONE    {
		@Override
		public String toString() {
            return "Not defined";
        }
	},
	PENDING  {
		@Override
		public String toString() {
            return "Pending";
        }
	}, 
	EXECUTED  {
		@Override
		public String toString() {
            return "Excecuted";
        }
	},
	CANCELLED  {
		@Override
		public String toString() {
            return "Cancelled";
        }
	};
}
