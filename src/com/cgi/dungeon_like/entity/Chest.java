package com.cgi.dungeon_like.entity;

import com.cgi.dungeon_like.enumerators.DefaultStatsEnum;

public class Chest extends Entity {

	public Chest() {
		super("Chest",0F,0,0,DefaultStatsEnum.CHEST_GOLD.getValue());
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
