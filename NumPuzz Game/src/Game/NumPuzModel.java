package Game;

import java.util.ArrayList;


import Game.NumPuzView.Tile;

/**
 * Handles the data of the game
 * @author suraj
 *
 */
public class NumPuzModel {
	
	public static int gamesPlayed = 0;
	
	public final boolean PAUSED = false;
	public final boolean PLAY = true;
	
	private ArrayList<Tile> tileList = new ArrayList<Tile>();
	private String[] typeSelections = {"Integer", "Text"};
	private Integer[] dimensionList = {3,4,5,6,7};
	private ArrayList<Integer> solution = new ArrayList<Integer>();
	//private ArrayList<Integer> currentConfig = new ArrayList<Integer>();
	private boolean state = PAUSED;
	private int points = 0;
	private int moves = 0;
	private int secTime = 0;
	
	
	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * @return the secTime
	 */
	public int getSecTime() {
		return secTime;
	}

	/**
	 * @param secTime the secTime to set
	 */
	public void setSecTime(int secTime) {
		this.secTime = secTime;
	}

	/**
	 * @return the tileList
	 */
	public ArrayList<Tile> getTileList() {
		return tileList;
	}

	/**
	 * @param tileList the tileList to set
	 */
	public void setTileList(ArrayList<Tile> tileList) {
		this.tileList = tileList;
	}

	/**
	 * @return the typeSelections
	 */
	public String[] getTypeSelections() {
		return typeSelections;
	}

	/**
	 * @param typeSelections the typeSelections to set
	 */
	public void setTypeSelections(String[] typeSelections) {
		this.typeSelections = typeSelections;
	}

	/**
	 * @return the dimensionList
	 */
	public Integer[] getDimensionList() {
		return dimensionList;
	}

	/**
	 * @param dimensionList the dimensionList to set
	 */
	public void setDimensionList(Integer[] dimensionList) {
		this.dimensionList = dimensionList;
	}

	/**
	 * @return the solution
	 */
	public ArrayList<Integer> getSolution() {
		return solution;
	}

	/**
	 * @return the moves
	 */
	public int getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	

	
}
