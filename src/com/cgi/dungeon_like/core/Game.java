package com.cgi.dungeon_like.core;

import java.util.HashMap;
import java.util.Scanner;
import com.cgi.dungeon_like.entity.*;
import com.cgi.dungeon_like.enumerators.ChestMenu;
import com.cgi.dungeon_like.enumerators.EnemyMenu;

public class Game {

	private int roomId;
	private Player player;
	private Enemy enemy;
	private Chest chest;
	private Entity target;
	private Scanner sc;
	private ChestMenu[] chestMenu;
	private EnemyMenu[] enemyMenu;

	public void init() {
		this.player = new Player();
		this.enemy = new Enemy();
		this.chest = new Chest();
		this.target = null;
		this.chestMenu = ChestMenu.values();
		this.enemyMenu = EnemyMenu.values();

		System.out.println("************* Dungeon Like *************");
		System.out.printf("Bienvenu aventurier %nMerci de rensigner ton nom %n");
		this.sc = new Scanner(System.in);
		String name = sc.nextLine();
		name = name.trim().equals("") ? "Player" : name;
		player.setName(name);
		System.out.printf("Que le combat commence %s %n", player.getName());
		this.roomId = 1;
	}

	public void run() {
		String reponse = null;
		boolean run = true;
		do {
			System.out.printf("%n********** Salle %s: **********%n",this.getRoomId());
			target = this.spawnEntity();
			target.upgrade(this.getRoomId());
			target.spawn();
			switch(target.getClass().getSimpleName()) {
			case "Chest":
				do {
					this.getMenu(target);
					reponse = sc.nextLine().trim().toLowerCase();
					
				}while(false);
				break;
			case "Enemy":
				
				break;
			}
			this.nextRoom();
			if(this.getRoomId() == 25)
				run = false;
		}while(run);
	}

	public void nextRoom() {
		this.roomId++;
	}

	public int getRoomId() {
		return roomId;
	}

	public Entity spawnEntity() {
		int rnd = (int) (Math.random() * 100);
		if (rnd < 95) {
			return this.enemy;
		}
		return this.chest;
	}

	private void getMenu(Entity target) {
		System.out.println("Que voulez-vous faire ?");
		if(target instanceof Chest) {
			for(ChestMenu nom : this.chestMenu) {
				System.out.printf("%s => %s %n",nom.getName(),nom.getDescription());
			}
		}else {
			for(EnemyMenu nom : this.enemyMenu) {
				System.out.printf("%s => %s %n",nom.getName(),nom.getDescription());
			}
		}
	}
	
}
