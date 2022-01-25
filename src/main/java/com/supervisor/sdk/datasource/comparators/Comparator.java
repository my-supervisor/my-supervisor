package com.supervisor.sdk.datasource.comparators;

public enum Comparator {
	NONE {
		public String toString() {
            return "Non défini";
        }
	},
	EQUALS {
		public String toString() {
            return "Egal";
        }
	},
	IS_PRESENT {
		public String toString() {
            return "Est présent";
        }
	},
	NOT_EQUALS {
		public String toString() {
            return "Différent";
        }
	},
	IS_ABSENT {
		public String toString() {
            return "Est absent";
        }
	},
	GREATER_THAN {
		public String toString() {
            return "Supérieur";
        }
	},
	LESS_THAN {
		public String toString() {
            return "Inférieur";
        }
	},
	GREATER_OR_EQUALS {
		public String toString() {
            return "Supérieur ou égal";
        }
	},
	LESS_OR_EQUALS {
		public String toString() {
            return "Inférieur ou égal";
        }
	},
	BETWEEN {
		public String toString() {
            return "Compris entre";
        }
	},
	NOT_BETWEEN {
		public String toString() {
            return "Non compris entre";
        }
	},
	CONTAINS {
		public String toString() {
            return "Contient";
        }
	},
	NOT_CONTAINS {
		public String toString() {
            return "Ne contient pas";
        }
	},
	STARTS_WITH {
		public String toString() {
            return "Commence par";
        }
	},
	NOT_STARTS_WITH {
		public String toString() {
            return "Ne commence pas par";
        }
	},
	ENDS_WITH {
		public String toString() {
            return "Fini par";
        }
	},
	NOT_ENDS_WITH {
		public String toString() {
            return "Ne fini pas par";
        }
	}
}
