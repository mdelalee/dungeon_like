package com.cgi.dungeon_like.entity;

public abstract class Entity {
	protected String name;
	protected int hp,def,force;
	
	
	public abstract void upgrade(int roomId);
	public abstract void spawn();

	public Entity(String name, int hp, int def, int force) {
		super();
		this.name = name;
		this.hp = hp;
		this.def = def;
		this.force = force;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getDef() {
		return def;
	}

	public int getForce() {
		return force;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public void setForce(int force) {
		this.force = force;
	}

}
