package com.supervisor.billing;

public enum PaymentRequestStatus {
	
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
	ACCEPTED  {
		@Override
		public String toString() {
            return "Accepted";
        }
	},
	CANCELLED  {
		@Override
		public String toString() {
            return "Cancelled";
        }
	};
}
