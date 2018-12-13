package com.cgi.dungeon_like.entity;

public class Enemy extends Entity {
	
	private int gold;

	public Enemy() {
		super("Unknown Enemy", 5, 5, 5);
		this.gold = 1;
	}
	
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void upgrade(int roomId) {
		this.setDef(this.getDef() + (int)(0.5 * roomId));
		this.setHp(this.getHp() + (int)(0.5 * roomId));
		this.setForce(this.getForce() + (int)(0.5 * roomId));
		this.setGold(this.getGold() + (int)(0.5 * roomId));
	}

	@Override
	public void spawn() {
		System.out.println("********** /!\\ Un monstre est apparu /!\\ **********");
	}

}
