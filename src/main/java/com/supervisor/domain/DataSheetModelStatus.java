package com.supervisor.domain;

public enum DataSheetModelStatus {
	NONE      {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	ACTIVE    {
		@Override
		public String toString() {
            return "Activé";
        }
	}, 
	INACTIVE  {
		@Override
		public String toString() {
            return "Désactivé";
        }
	};
}
