package com.supervisor.billing;

public enum PaymentReceiptState {
	
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	PENDING  {
		@Override
		public String toString() {
            return "En attente";
        }
	}, 
	CONFIRMED  {
		@Override
		public String toString() {
            return "Confirmé";
        }
	},
	CANCELLED  {
		@Override
		public String toString() {
            return "Annulé";
        }
	};
}
