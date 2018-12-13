package com.cgi.dungeon_like.enumerators;

public enum EnemyMenu {
	COMBATTRE("Combattre","Lancer le combat"),
	EXAMINER("Examiner","Afficher les informations de la cible"),
	FUIRE("Fuire","Retourné au début du donjon (perte de 50% de votre Or)");
	
	private String name;
	private String description;

	EnemyMenu(String name,String descriptif) {
		this.name = name;
		this.description = descriptif;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
}
