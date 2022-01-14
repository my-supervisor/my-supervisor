package com.minlessika.membership.domain;

public enum RegistrationRequestStatus {
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	PENDING  {
		@Override
		public String toString() {
            return "En cours";
        }
	}, 
	CONFIRMED  {
		@Override
		public String toString() {
            return "Confirmée";
        }
	},
	CANCELED {
		@Override
		public String toString() {
            return "Annulée";
        }
	};
}
