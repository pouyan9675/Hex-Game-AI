package run;

import hex.exceptions.BadMoveException;
import players.AbstractPlayer;
import players.ComputerPlayer;
import players.RandomPlayer;

public class Main {
    public static void main(String[] args) throws BadMoveException, InterruptedException {
	 AbstractPlayer p1 = new RandomPlayer();
	// AbstractPlayer p2 = new RandomPlayer();
	ComputerPlayer p2 = new ComputerPlayer();
	Game g = new Game(p1, p2);
	g.start();
    }
}
