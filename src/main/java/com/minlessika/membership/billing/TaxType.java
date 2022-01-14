package com.minlessika.membership.billing;

public enum TaxType {
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	TVA_GROUP  {
		@Override
		public String toString() {
            return "TVA et taxes assimilées";
        }
	}, 
	OTHERS  {
		@Override
		public String toString() {
            return "Autres";
        }
	};
}
