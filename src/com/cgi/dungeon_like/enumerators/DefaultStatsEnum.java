package com.cgi.dungeon_like.enumerators;

public enum DefaultStatsEnum {
	PLAYER_HP (20),
	PLAYER_FORCE (10),
	PLAYER_DEF (10),
	PLAYER_GOLD (0),
	ENEMY_HP (5),
	ENEMY_FORCE (5),
	ENEMY_DEF (5),
	ENEMY_GOLD (5),
	CHEST_GOLD (1);
	
	private Integer value;
	
	private DefaultStatsEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
}