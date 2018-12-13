package com.cgi.dungeon_like.core;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.cgi.dungeon_like.entity.*;
import com.cgi.dungeon_like.enumerators.ChestMenu;
import com.cgi.dungeon_like.enumerators.EnemyMenu;

public class Game {

	private int roomId;
	private int previousRoomId;
	private Player player;
	private Entity target;
	private Scanner sc;
	private List<ChestMenu> chestMenu;
	private List<EnemyMenu> enemyMenu;
	private boolean run;

	public void initGame() {
		this.player = new Player();
		this.target = null;
		this.chestMenu = Arrays.asList(ChestMenu.values());
		this.enemyMenu = Arrays.asList(EnemyMenu.values());

		System.out.println("************* Dungeon Like *************");
		System.out.printf("Bienvenu aventurier %nMerci de rensigner ton nom %n");
		this.sc = new Scanner(System.in);
		String name = sc.nextLine();
		name = name.trim().equals("") ? "Player" : name;
		player.setName(name);
		System.out.printf("Que le combat commence %s %n", player.getName());
		this.roomId = 1;
		this.previousRoomId = 0;
	}

	public void runGame() {
		String reponse = null;
		this.run = true;
		boolean acceptedResponse,nextRoom;
		do {
			nextRoom = false;
			do {
				System.out.printf("%n********** Salle %s: **********%n",this.getRoomId());
				if(this.previousRoomId != this.roomId) {
					this.target = this.spawnEntity();
					this.target.upgrade(this.getRoomId());
				}
				this.previousRoomId = this.roomId;
				this.target.spawn();
				this.player.spawn();
				acceptedResponse = false;
				do {
					this.getMenu(target);
					reponse = sc.nextLine().trim();
					if(!reponse.isEmpty())
						reponse = reponse.substring(0, 1).toUpperCase() + reponse.substring(1);
					try {
						this.containInChestMenu(target, reponse);
						acceptedResponse = true;
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}while(!acceptedResponse);
				switch(reponse) {
					case "Combattre":
						float playerHpBeforeFight = this.player.getHp();
						boolean resultBattle = this.fight();
						if(resultBattle) {
							System.out.printf("Vous avez gagné %nVous remporter %d pièce(s) d'or",this.target.getGold());
							this.player.setGold(this.player.getGold() + this.target.getGold());
						}else {
							System.out.println("Vous avez perdu \nVous perdez 50% de votre or,10% de vos caractéritique et retourné à la première salle");
							this.resetGame(0.5F,0.9F);
						}
						nextRoom = true;
						break;
					case "Fuir":
						nextRoom = this.fuir();
						break;
					case "Ouvrir":
						nextRoom = this.openChest();
						break;
				}
			}while(!nextRoom);
			
			this.nextRoom();
			if(this.getRoomId() == 25)
				this.run = false;
		}while(this.run);
	}

	private void nextRoom() {
		this.roomId++;
	}

	private int getRoomId() {
		return roomId;
	}
	
	private void resetGame(float coefLostGold, float coefLostStat) {
		this.roomId = 0;
		this.player.setGold((int) (this.player.getGold() * coefLostGold));
		int newDef = (int) (this.player.getDef() * coefLostStat < Player.DEFAULTDEF ? Player.DEFAULTDEF : this.player.getDef() * coefLostStat);
		this.player.setDef(newDef);
		int newForce = (int) (this.player.getForce() * coefLostStat < Player.DEFAULTFORCE ? Player.DEFAULTFORCE : this.player.getForce() * coefLostStat);
		this.player.setForce(newForce);
		int newHp = (int) (this.player.getHp() * coefLostStat < Player.DEFAULTHP ? Player.DEFAULTHP : this.player.getHp() * coefLostStat);
		this.player.setHp(newHp);
	}

	public Entity spawnEntity() {
		int rnd = (int) (Math.random() * 100);
		if (rnd < 95) {
			return new Enemy();
		}
		return new Chest();
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

	private void containInChestMenu(Entity target, String reponse) throws Exception {
		boolean rep = false;
		if(target instanceof Chest) {
			for(ChestMenu choix : this.chestMenu) {
				if(choix.getName().equals(reponse)) {
					rep = true;
					break;
				}
			}
		}else {
			for(EnemyMenu choix : this.enemyMenu) {
				if(choix.getName().equals(reponse)) {
					rep = true;
					break;
				}
			}
		}
		if(!rep)
			throw new Exception("*** Choix incorrecte ***");
	}
	
	private boolean fight() {
		while(this.player.getHp() > 0 && this.target.getHp() > 0) {
			this.target.setHp((float) (this.target.getHp() - (this.player.getForce() * (this.target.getDef() * 0.01))));
			this.player.setHp((float) (this.player.getHp() - (this.target.getForce() * (this.player.getDef() * 0.01))));
		}
		return this.player.getHp() <= 0 ? false : true;
	}
	
	private boolean fuir() {
		String rep = null;
		do {
			System.out.println("Etes-vous certain de vouloir fuir? Vous perdrez 75% de votre Or et 50% de vos caractéristique (" + this.player.getGold() +") y/n");
			rep = this.sc.nextLine().trim().toLowerCase();
			switch(rep) {
			case "y":
				this.resetGame(0.75F,0.5F);
				return true;
			case "n":
				return false;
			default:
				rep = null;
				break;
			}

		}while(rep == null);
		return false;
	}
	
	private boolean openChest() {
		System.out.println("Ouverture du coffre");
		System.out.println("Vous obtenez "+this.target.getGold() + "pièces d'or");
		this.player.setGold(player.getGold() + this.target.getGold());
		return true;
	}

}