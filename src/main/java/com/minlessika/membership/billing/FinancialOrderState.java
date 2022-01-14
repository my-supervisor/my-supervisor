package com.minlessika.membership.billing;

public enum FinancialOrderState {
	NONE    {
		@Override
		public String toString() {
            return "Undefined";
        }
	},
	PENDING  {
		@Override
		public String toString() {
            return "Pending";
        }
	}, 
	COMPLETED  {
		@Override
		public String toString() {
            return "Completed";
        }
	}, 
	CANCELLED  {
		@Override
		public String toString() {
            return "Cancelled";
        }
	};
}
