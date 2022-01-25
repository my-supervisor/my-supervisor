package com.supervisor.billing;

public enum TaxValueType {
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	PERCENT  {
		@Override
		public String toString() {
            return "Pourcentage";
        }
	}, 
	AMOUNT  {
		@Override
		public String toString() {
            return "Montant";
        }
	};
}
