package com.minlessika.sdk.time;

public enum PeriodicityUnit {
	NONE        {
		public String toString() {
            return "Non défini";
        }
	},
	DAILY  {
		public String toString() {
            return "Jour";
        }
	}, 
	WEEKLY  {
		public String toString() {
            return "Semaine";
        }
	}, 
	MONTHLY  {
		public String toString() {
            return "Mois";
        }
	}, 
	YEARLY  {
		public String toString() {
            return "Année";
        }
	}, 
	FOREVER  {
		public String toString() {
            return "Tous les temps";
        }
	};
}
