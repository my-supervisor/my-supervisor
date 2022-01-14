package com.minlessika.supervisor.domain;

public enum DataFieldType {
	
	NONE    {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	NUMBER  {
		@Override
		public String toString() {
            return "Nombre";
        }
	}, 
	STRING  {
		@Override
		public String toString() {
            return "Chaine de caractère";
        }
	},
	BOOLEAN {
		@Override
		public String toString() {
            return "Booléen";
        }
	},
	DATE {
		@Override
		public String toString() {
            return "Date";
        }
	},
	TABLE {
		@Override
		public String toString() {
            return "Table";
        }
	};
}
