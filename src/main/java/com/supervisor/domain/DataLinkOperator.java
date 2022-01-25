package com.supervisor.domain;

public enum DataLinkOperator {
	NONE {
		@Override
		public String toString() {
            return "Aucun";
        }
	},
	NEGATIVE {
		@Override
		public String toString() {
            return "Négatif";
        }
	}	
}
