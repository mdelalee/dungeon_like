package com.cgi.dungeon_like.entity;

public abstract class Entity {
	protected String name;
	protected int def;
	protected int force;
	protected int gold;
	protected float hp;
	
	
	public abstract void upgrade(int roomId);
	public abstract void spawn();

	public Entity(String name, float hp, int def, int force,int gold) {
		super();
		this.name = name;
		this.hp = hp;
		this.def = def;
		this.force = force;
		this.gold = gold;
	}

	public String getName() {
		return name;
	}

	public float getHp() {
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

	public void setHp(float hp) {
		this.hp = hp;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public void setForce(int force) {
		this.force = force;
	}
	
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void showStats() {
		System.out.println("***** Caractèristique de "+ this.getName() +" *****");
		System.out.printf("Hp => %.2f %nDéfence => %d%nForce => %d %nOr => %d %n",this.getHp(),this.getDef(),this.getForce(),this.getGold());
	}
	
	public void hit(Entity target) {
		float damage = (float) (this.getForce() * (target.getDef() * 0.1));
		float newHp = damage <= target.getHp() ? target.getHp() - damage : 0F;
		target.setHp(newHp);
	}

}
