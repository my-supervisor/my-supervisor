package com.minlessika.supervisor.domain;

public enum ResourceType {
	NONE        {
		public String toString() {
            return "Non défini";
        }
	},
	DATA_SHEET_MODEL      {
		public String toString() {
            return "Modèle de feuille de données";
        }
	}, 
	ACTIVITY  {
		public String toString() {
            return "Activité";
        }
	}, 
	INDICATOR  {
		public String toString() {
            return "Indicateur";
        }
	};
}
