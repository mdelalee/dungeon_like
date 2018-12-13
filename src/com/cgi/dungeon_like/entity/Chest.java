package com.cgi.dungeon_like.entity;

public class Chest extends Entity {
	private int gold;

	public Chest() {
		super("Chest",0,0,0);
		this.gold = 1;
	}

	public void upgrade(int roomId) {
		this.gold += 5 * roomId;
	}

	@Override
	public void spawn() {
		System.out.println("********** /!\\ Un coffre est apparu /!\\ **********");
	}

}
