package com.minlessika.supervisor.domain;

public enum DataModelType {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	DATA_SHEET_MODEL    {
		@Override
		public String toString() {
            return "Modèle de feuille de données";
        }
	}, 
	AGGREGATED_MODEL  {
		@Override
		public String toString() {
            return "Modèle agrégé";
        }
	};
}
