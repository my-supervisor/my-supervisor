package com.minlessika.supervisor.domain;

public enum TemplateAvailability {
	NONE        {
		public String toString() {
            return "Non défini";
        }
	},
	FREE      {
		public String toString() {
            return "Gratuit";
        }
	},
	PAYABLE   {
		public String toString() {
            return "Payant";
        }
	}
}
