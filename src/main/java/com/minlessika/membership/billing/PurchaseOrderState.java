package com.minlessika.membership.billing;

public enum PurchaseOrderState {
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
	CONFIRMED  {
		@Override
		public String toString() {
            return "Confirmed";
        }
	}, 
	PAID  {
		@Override
		public String toString() {
            return "Paid";
        }
	}, 
	CANCELLED  {
		@Override
		public String toString() {
            return "Cancelled";
        }
	};
}
