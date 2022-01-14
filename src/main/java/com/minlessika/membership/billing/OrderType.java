package com.minlessika.membership.billing;

public enum OrderType {
	
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	PURCHASE_ORDER  {
		@Override
		public String toString() {
            return "Purchase order";
        }
	}, 
	INVOICE  {
		@Override
		public String toString() {
            return "Invoice";
        }
	};
}
