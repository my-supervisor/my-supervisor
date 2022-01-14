package com.minlessika.supervisor.domain;

public enum TemplateLicense {
	
	NONE        {
		public String toString() {
            return "Non défini";
        }
	},
	AGPL_3      {
		public String toString() {
            return "AGPL-3";
        }
	},
	MPL_1      {
		public String toString() {
            return "MPL-1";
        }
	}
}
