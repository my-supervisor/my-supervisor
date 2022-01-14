package com.minlessika.supervisor.domain;

public enum DataDomainDefinition {
	
	ALL_PERIOD {
		@Override
		public String toString() {
            return "Toute période";
        }
	},
	DURING_PERIOD {
		@Override
		public String toString() {
            return "Durant la période";
        }
	},
	BEGIN_PERIOD_TO_NOW {
		@Override
		public String toString() {
            return "Début de la période à aujourd'hui";
        }
	},
	NOW_TO_END_PERIOD {
		@Override
		public String toString() {
            return "Aujourd'hui à la fin de la période";
        }
	},
	AFTER_NOW {
		@Override
		public String toString() {
            return "Après ajourd'hui";
        }
	},
	BEFORE_NOW {
		@Override
		public String toString() {
            return "Avant aujourd'hui";
        }
	},
	NOW_TO_AFTER {
		@Override
		public String toString() {
            return "Ajourd'hui et après";
        }
	},
	BEFORE_TO_NOW {
		@Override
		public String toString() {
            return "Avant à aujourd'hui";
        }
	},
	BEFORE_PERIOD {
		@Override
		public String toString() {
            return "Avant la période";
        }
	},
	AFTER_PERIOD {
		@Override
		public String toString() {
            return "Après la période";
        }
	},
	BEFORE_AND_AFTER_PERIOD {
		@Override
		public String toString() {
            return "Avant et après la période";
        }
	},
	BEFORE_AND_DURING_PERIOD {
		@Override
		public String toString() {
            return "Avant et période actuelle";
        }
	},
	DURING_PERIOD_AND_AFTER {
		@Override
		public String toString() {
            return "Période actuelle et après";
        }
	}
}
