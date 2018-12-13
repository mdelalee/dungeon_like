package com.cgi.dungeon_like.entity;

public class Player extends Entity {

	public Player() {
		super("Player", 999, 999, 999);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void upgrade(int roomId) {
		
	}

	@Override
	public void spawn() {
		
	}

}
