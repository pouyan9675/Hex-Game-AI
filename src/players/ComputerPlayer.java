package players;

import hex.Board;
import hex.Cell;
import hex.Move;
import hex.exceptions.BadMoveException;
import java.util.ArrayList;

public class ComputerPlayer extends AbstractPlayer {

    private int playerNumber = 1;
    private int oppNumber = 2;

    private final int MY_CHAIN_SCORE = 10;

    @Override
    public void setColor(int color) {
	super.setColor(color);
	playerNumber = getColor();
	if(playerNumber == 1){
	    oppNumber = 2;
	}else{
	    oppNumber = 1;
	}
    }

    @Override
    public Move getMove(Board board) {
	int[] index = minmax(board, 5, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
	Move move = new Move(new Cell(index[1], index[2]));
	// System.out.println(index[1] + " , " + index[2]);
	return move;
    }

    /**
     * return the next move to make using minimax Search tree + alpha beta pruning
     * 
     * @return Move : the next move
     */
    private int[] minmax(Board board, int depth, boolean isMax, int alpha, int beta) {

	int nodeValue;
	int bestRow = -1;
	int bestCol = -1;

	// System.out.println("Win : " + board.win());
	// System.out.println(playerNumber);
	// System.out.println(oppNumber);

	if(board.win() == playerNumber){
	    nodeValue = Integer.MAX_VALUE;
	    return new int[] { nodeValue, bestRow, bestCol };
	}else if(board.win() == oppNumber){
	    nodeValue = Integer.MIN_VALUE;
	    return new int[] { nodeValue, bestRow, bestCol };
	}
	if(depth <= 0){
	    // Returning this state heuristic function value
	    // return val;
	    int a = heuristic(board);
	    return new int[] { a, bestRow, bestCol };
	}else{
	    ArrayList<Cell> cells = getAvailableCells(board); // get all available moves
	    if(isMax){ // It's my turn
		nodeValue = Integer.MIN_VALUE; // - infinite
		for(Cell availableCell : cells){
		    Board newBoard = new Board(board);
		    try{
			Move availableMove = new Move(availableCell);
			newBoard.move(availableMove, playerNumber);
		    }catch(BadMoveException e){
		    }
		    nodeValue = Math.max(nodeValue, minmax(newBoard, depth - 1, !isMax, alpha, beta)[0]);
		    if(nodeValue > alpha){ // maximizing alpha
			alpha = nodeValue;
			bestRow = availableCell.getR(); // Saving best move index
			bestCol = availableCell.getC();
		    }
		    if(beta <= alpha) // pruning
			break;
		}
		return new int[] { alpha, bestRow, bestCol };
	    }else{
		nodeValue = Integer.MAX_VALUE; // + infinite
		for(Cell availableCell : cells){
		    Board newBoard = new Board(board);
		    try{
			Move availableMove = new Move(availableCell);
			newBoard.move(availableMove, oppNumber);
		    }catch(BadMoveException e){
		    }
		    nodeValue = Math.min(nodeValue, minmax(newBoard, depth - 1, !isMax, alpha, beta)[0]);
		    if(nodeValue < beta){ // minimizing beta
			beta = nodeValue;
			bestRow = availableCell.getR(); // Saving best move index
			bestCol = availableCell.getC();
		    }
		    if(beta <= alpha) // pruning
			break;

		}
		return new int[] { beta, bestRow, bestCol };
	    }

	}
	// return 0;
    }

    private int heuristic(Board board) {
	int score = 0;

	boolean[][] visited = new boolean[board.getSize()][board.getSize()];
	score += findChain(board, playerNumber, visited);

	// score = (int) (Math.random() * 500);
	// return (int) (Math.random() * 100);
	return score;
    }

    private int findChain(Board board, int player, boolean[][] visited) {
	int chain = 0;
	for(int i = 0; i < board.getSize(); i++) { // Moving on all rows and columns
	    for(int j = 0; j < board.getSize(); j++) {
		int tmpChain = 0;
		if(visited[i][j])
		    continue;
		for(Cell cell : board.getAdjacents(new Cell(i, j))){
		    if(board.get(cell) == player && !visited[i][j]){
			tmpChain++;
			if(playerNumber == 1 && cell.getR() == i) // Blue player should play in X axis
			    tmpChain += 2;
			if(playerNumber == 2 && cell.getC() == j)
			tmpChain += 2;
			visited[i][j] = true;
		    }
		}
		chain = Math.max(chain, tmpChain);
		tmpChain = 0;
	    }
	}

	if(player == playerNumber)
	    chain *= MY_CHAIN_SCORE;
	return chain;
    }

    private ArrayList<Cell> getAvailableCells(Board board) {
	ArrayList<Cell> availables = new ArrayList<Cell>();
	for(int i = 0; i < board.getSize(); i++)
	    for(int j = 0; j < board.getSize(); j++){
		if(board.get(new Cell(i, j)) == 0)
		    availables.add(new Cell(i, j));
	    }
	return availables;
    }

}
