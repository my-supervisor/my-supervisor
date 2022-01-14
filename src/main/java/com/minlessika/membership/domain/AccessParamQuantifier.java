package com.minlessika.membership.domain;

public enum AccessParamQuantifier {
	NONE    {
		public String toString() {
            return "Non défini";
        }
	},
	MIN  {
		public String toString() {
            return "Mininum";
        }
	}, 
	MAX  {
		public String toString() {
            return "Maximum";
        }
	},
	SAME {
		public String toString() {
            return "Pareil";
        }
	};
}
