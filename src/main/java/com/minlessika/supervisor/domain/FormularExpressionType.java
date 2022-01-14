package com.minlessika.supervisor.domain;

public enum FormularExpressionType {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	SIMPLE    {
		@Override
		public String toString() {
            return "Simple";
        }
	}, 
	CASE  {
		@Override
		public String toString() {
            return "Condition multiple";
        }
	}, 
	EXTENDED_TO_PARENT  {
		@Override
		public String toString() {
            return "Etendue au parent";
        }
	}, 
	EXTENDED_TO_CHILD  {
		@Override
		public String toString() {
            return "Etendue à l'enfant";
        }
	}, 
	EXTENDED_TO_MODEL  {
		@Override
		public String toString() {
            return "Etendue au modèle";
        }
	};
}
