package com.minlessika.membership.billing;

public enum InvoiceState {
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	DRAFT  {
		@Override
		public String toString() {
            return "Brouillon";
        }
	}, 
	OPENED  {
		@Override
		public String toString() {
            return "Ouverte";
        }
	}, 
	PAID  {
		@Override
		public String toString() {
            return "Payée";
        }
	};
}
