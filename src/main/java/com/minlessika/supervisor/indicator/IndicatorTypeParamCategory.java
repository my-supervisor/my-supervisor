package com.minlessika.supervisor.indicator;

public enum IndicatorTypeParamCategory {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	STATIC    {
		@Override
		public String toString() {
            return "Static";
        }
	}, 
	DYNAMIC  {
		@Override
		public String toString() {
            return "Dynamique";
        }
	};
}
