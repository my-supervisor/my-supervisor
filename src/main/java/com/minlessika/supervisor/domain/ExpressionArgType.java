package com.minlessika.supervisor.domain;

public enum ExpressionArgType {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	VALUE    {
		@Override
		public String toString() {
            return "Valeur";
        }
	},
	DATA_FIELD    {
		@Override
		public String toString() {
            return "Champ de donnée";
        }
	},
	FORMULAR    {
		@Override
		public String toString() {
            return "Formule";
        }
	},
	EXPRESSION    {
		@Override
		public String toString() {
            return "Expression";
        }
	},
	PARAMETER    {
		@Override
		public String toString() {
            return "Paramètre";
        }
	};
}
