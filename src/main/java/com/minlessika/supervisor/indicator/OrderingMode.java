package com.minlessika.supervisor.indicator;

public enum OrderingMode {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	NATUREL    {
		@Override
		public String toString() {
            return "Naturel";
        }
	}, 
	ASC  {
		@Override
		public String toString() {
            return "Ascendant";
        }
	},
	DESC  {
		@Override
		public String toString() {
            return "Descendant";
        }
	} ;
}
