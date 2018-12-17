package com.cgi.dungeon_like.entity;

import java.util.HashMap;

import com.cgi.dungeon_like.enumerators.DefaultStatsEnum;

public class Player extends Entity {
	
	private HashMap<String, Integer> statCost = new HashMap<>();

	public Player() {
		super("Player", DefaultStatsEnum.PLAYER_HP.getValue(),
				DefaultStatsEnum.PLAYER_DEF.getValue(),
				DefaultStatsEnum.PLAYER_FORCE.getValue(),
				DefaultStatsEnum.PLAYER_GOLD.getValue());
		//Prix pour augmenter chaque caractéristiques
		statCost.put("Hp", 5);
		statCost.put("Def",3);
		statCost.put("Force",3);
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
	
	public void upgradePlayer(String stat) throws Exception {
		if(this.getGold() < this.statCost.get(stat)) {
			throw new Exception("Pas assez d'or");
		}
		switch(stat) {
		case "Hp":
			this.setHp(this.getHp() + 10);
			break;
		case "Def":
			this.setDef(this.getDef() + 1);
			break;
			
		case "Force":
			this.setForce(this.getForce() + 1);
			break;
		}
		this.setGold(this.getGold() - statCost.get(stat));
	}
}
