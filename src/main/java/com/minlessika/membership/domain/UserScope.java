package com.minlessika.membership.domain;

public enum UserScope {
	NONE      {
		public String toString() {
            return "Non défini";
        }
	},
	SYSTEM    {
		public String toString() {
            return "Système";
        }
	}, 
	USER  {
		public String toString() {
            return "Utilisateur";
        }
	};
}
