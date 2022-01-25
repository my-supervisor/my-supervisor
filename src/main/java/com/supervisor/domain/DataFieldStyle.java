package com.supervisor.domain;

public enum DataFieldStyle {
	NONE        {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	SIMPLE      {
		@Override
		public String toString() {
            return "Simple";
        }
	},
	LIST      {
		@Override
		public String toString() {
            return "Liste";
        }
	}, 
	STRUCTURE  {
		@Override
		public String toString() {
            return "Structure";
        }
	}, 
	FORMULAR  {
		@Override
		public String toString() {
            return "Formule";
        }
	}, 
	PARAMETER  {
		@Override
		public String toString() {
            return "Paramètre";
        }
	};
}
