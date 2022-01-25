package com.supervisor.billing;

public enum PaymentType {
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	CASH  {
		@Override
		public String toString() {
            return "Liquidités";
        }
	}, 
	MOBILE_MONEY  {
		@Override
		public String toString() {
            return "Paiement mobile";
        }
	},
	BANK {
		@Override
		public String toString() {
            return "Banque";
        }
	},
	ASSOCIATE {
		@Override
		public String toString() {
            return "Aggrégé";
        }
	};
}
