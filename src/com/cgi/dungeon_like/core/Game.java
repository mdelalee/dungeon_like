package com.cgi.dungeon_like.core;

import java.util.HashMap;
import java.util.Scanner;
import com.cgi.dungeon_like.entity.*;

public class Game {

	private int roomId;
	private int previousRoomId;
	private Player player;
	private Entity target;
	private Scanner sc;
	private boolean run;
	private HashMap<String, HashMap<String, String>> menu;

	public void initGame() {
		this.player = new Player();
		this.target = null;
		this.constructMenu();

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

	private void constructMenu() {
		this.menu = new HashMap<>();
		this.menu.put(Enemy.class.getSimpleName(), new HashMap<>());
		this.menu.get(Enemy.class.getSimpleName()).put("Combattre","Lancer le combat (en cas de defaite, vous perdrez 50% de votre or et retourné au début du donjon)");
		this.menu.get(Enemy.class.getSimpleName()).put("Fuir","Retourné au début du donjon (perte de 75% de votre Or)");
		this.menu.get(Enemy.class.getSimpleName()).put("Upgrade","Amérlioer les caractéritiques");
		
		this.menu.put(Chest.class.getSimpleName(), new HashMap<>());
		this.menu.get(Chest.class.getSimpleName()).put("Ouvrir","Récupérer le contenu du coffre");
		
		this.menu.put(null, new HashMap<>());
		this.menu.get(null).put("Hp","+10 Hp contre 5 pièce d'or");
		this.menu.get(null).put("Force","+1 force contre 3 pièce d'or");
		this.menu.get(null).put("Def","+1 force contre 3 pièce d'or");
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
				case "Upgrade":
					System.out.println("Upgrade");
					System.exit(0);
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
		String typeMenu = target == null ? null : target.getClass().getSimpleName();
		HashMap<String,String> menu = this.menu.get(typeMenu);
		menu.keySet().stream()
					.map(choix -> choix + " => " + menu.get(choix))
					.forEach(System.out::println);
	}

	private void containInChestMenu(Entity target, String reponse) throws Exception {
		String typeMenu = target == null ? null : target.getClass().getSimpleName();
		if(!this.menu.get(typeMenu).keySet().contains(reponse))
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