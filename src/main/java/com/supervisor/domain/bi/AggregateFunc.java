package com.supervisor.domain.bi;

public enum AggregateFunc {
	NONE {
		@Override
		public String toString() {
            return "Aucune";
        }
	},
	SUM {
		@Override
		public String toString() {
            return "Somme";
        }
	}, 
	COUNT {
		@Override
		public String toString() {
            return "Compteur";
        }
	}, 
	MAX {
		@Override
		public String toString() {
            return "Maximum";
        }
	}, 
	MIN {
		@Override
		public String toString() {
            return "Minimum";
        }
	}, 
	AVG {
		@Override
		public String toString() {
            return "Moyenne";
        }
	}, 
	FIRST_VALUE {
		@Override
		public String toString() {
            return "Première valeur";
        }
	}, 
	LAST_VALUE {
		@Override
		public String toString() {
            return "Dernière valeur";
        }
	}	
}
