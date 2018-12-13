package com.cgi.dungeon_like.entity;


public class Player extends Entity {
	public static float DEFAULTHP = 20F;
	public static int DEFAULTFORCE = 10;
	public static int DEFAULTDEF = 10;
	public static int DEFAULTGOLD = 0;

	public Player() {
		super("Player", 20F, 10, 10,0);
	}
	
	public void setName(String name) {
		this.name = name;
	}	

	@Override
	public void upgrade(int roomId) {}

	@Override
	public void spawn() {
		this.showStats();
	}

}
