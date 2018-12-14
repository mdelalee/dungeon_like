package com.cgi.dungeon_like.entity;

public class Enemy extends Entity {
	
	public Enemy(String name, float hp, int def, int force,int gold) {
		super(name,hp,def,force,gold);
	}

	public void upgrade(int roomId) {
		float coef = roomId % 10 == 0 ? 0.5F : 0.4F;
		this.setDef((int) (this.getDef() * coef));
		this.setHp(this.getHp() * coef);
		this.setForce((int) (this.getForce() * coef));
		this.setGold((int) (this.getGold() * coef));
	}

	@Override
	public void spawn() {
		System.out.println("********** /!\\ Un monstre est apparu /!\\ **********");
		this.showStats();
	}

}
