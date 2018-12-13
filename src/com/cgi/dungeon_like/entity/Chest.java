package com.cgi.dungeon_like.entity;

public class Chest extends Entity {

	public Chest() {
		super("Chest",0F,0,0,1);
	}

	public void upgrade(int roomId) {
		this.setGold(this.getGold() + 3 * roomId);
	}

	@Override
	public void spawn() {
		System.out.println("********** /!\\ Un coffre est apparu /!\\ **********");
		System.out.printf("Il contient %d pièces d'or%n",this.getGold());
	}

}
