package com.supervisor.domain;

public enum FormularFunc {
	
	NONE("Non défini", 0),
	IDEM("Le même", 1),
	ADD("Additionner", 2),
	SUBSTRUCT("Soustraire", 2),
	MULTIPLY("Multiplier", 2),
	DIVIDE("Diviser", 2),
	ECART("Ecart", 2),
	MIN("Minimum", 2),
	MAX("Maximum", 2),
	FIRST("Premier", 1),
	PREVIOUS("Précédent", 1),
	NEXT("Suivant", 1),
	TRUNC("Tronquer", 2),
	ROUND("Arrondir", 2),	
	ROUND_UP("Arrondir par excès", 1),
	ROUND_DOWN("Arrondir par défaut", 1),
	ROUND_TO_NEAREST_VALUE("Arrondir à la valeur la plus proche", 2),
	ROUND_TO_DOWN_VALUE("Arrondir à la valeur par défaut", 2),
	ROUND_TO_UP_VALUE("Arrondir à la valeur par excès", 2),
	CONCAT("Concaténer", 2),
	CONCAT_WITH_BLANK("Concaténer avec espace", 2),
	CONCAT_WITH_SEPARATOR("Concaténer avec séparateur", 3),
	CONCAT_WITH_SEPARATOR_SPACED("Concaténer avec séparateur espacé", 3),
	CONCAT3("Concaténer 3 chaines", 3),
	NOW_DATE("Date en cours", 0),
	DAYS_OF_PERIOD("Nombre de jours d'une période", 2),
	BEGIN_OF_PERIOD("Premier jour de la période", 0),
	END_OF_PERIOD("Dernier jour de la période", 0),	
	BEGIN_OF_MONTH("Premier jour du mois", 0),
	END_OF_MONTH("Fin du mois", 0),	
	BEGIN_OF_YEAR("Début de l'année", 0),
	END_OF_YEAR("Fin de l'année", 0);
	
	private final String name;
	private final int nbArgs;
	
	FormularFunc(final String name, final int nbArgs){
		this.name = name;
		this.nbArgs = nbArgs;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int nbArgs() {
		return nbArgs;
	}
}
