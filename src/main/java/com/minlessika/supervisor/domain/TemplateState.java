package com.minlessika.supervisor.domain;

public enum TemplateState {
	NONE        {
		@Override
		public String toString() {
            return "Non défini";
        }
	},
	CREATED      {
		@Override
		public String toString() {
            return "Créé";
        }
	},
	PUBLISHED      {
		@Override
		public String toString() {
            return "Publié";
        }
	},
	OBSOLETE      {
		@Override
		public String toString() {
            return "Obsolète";
        }
	}
}
