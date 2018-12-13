package com.cgi.dungeon_like.enumerators;

public enum ChestMenu{
	OUVRIR ("Ouvrir","Récupérer le contenu du coffre");
	
	private String name;
	private String description;

	ChestMenu(String name,String descriptif) {
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
