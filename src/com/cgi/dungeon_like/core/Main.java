package com.cgi.dungeon_like.core;

public class Main {
	private static Game game;

	public static void main(String[] args) {
		game = new Game();
		game.initGame();
		game.runGame();
	}
}
