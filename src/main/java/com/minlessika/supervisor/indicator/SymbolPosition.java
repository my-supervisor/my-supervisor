package com.minlessika.supervisor.indicator;

public enum SymbolPosition {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	RIGHT    {
		@Override
		public String toString() {
            return "Droite";
        }
	}, 
	LEFT  {
		@Override
		public String toString() {
            return "Gauche";
        }
	};
}
