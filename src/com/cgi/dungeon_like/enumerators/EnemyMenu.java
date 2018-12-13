package com.cgi.dungeon_like.enumerators;

public enum EnemyMenu {
	COMBATTRE("Combattre","Lancer le combat (en cas de defaite, vous perdrez 50% de votre or et retourn� au d�but du donjon)"),
	FUIRE("Fuir","Retourn� au d�but du donjon (perte de 75% de votre Or)");
	
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
