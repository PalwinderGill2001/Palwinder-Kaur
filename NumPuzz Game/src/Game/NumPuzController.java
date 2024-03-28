package Game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Game.NumPuzView.Popup;
import Game.NumPuzView.Tile;

/**
 * Handles the actions of the game
 * @author suraj
 *
 */
@SuppressWarnings("unused")
public class NumPuzController implements ActionListener {
	
	NumPuzView nv;
	NumPuzModel nm;
	Timer timer;
	
	JFrame colorSelect;	
	private final boolean INTEGER = false;
	private final boolean TEXT = true;
	
	/**
	 * Default constructor
	 */
	NumPuzController() {
		
	}
	
	/**
	 * paramterized constructor
	 * @param nv - object of NumPuzView class
	 * @param nm - object of NumPuzModel class
	 */
	NumPuzController(NumPuzView nv, NumPuzModel nm) {
		this.nv = nv;
		this.nm = nm;
	}
	
	/**
	 * starts the game
	 */
	void start() {
		
		nv.setDimensionSelect(new JComboBox<Integer>(nm.getDimensionList()));
		nv.setTypeSelect(new JComboBox<String>(nm.getTypeSelections()));
		
		nv.getDesign().addActionListener(this);
		nv.getPlay().addActionListener(this);
		nv.getDimensionSelect().addActionListener(this);
		nv.getLogoButton().addActionListener(this);
		nv.getTypeSelect().addActionListener(this);
		nv.getTextSubmit().addActionListener(this);
		nv.getRand().addActionListener(this);
		nv.getShow().addActionListener(this);
		nv.getHide().addActionListener(this);
		nv.newGameItem.addActionListener(this);
		nv.exitItem.addActionListener(this);
		nv.aboutItem.addActionListener(this);
		nv.colorItem.addActionListener(this);
		
		createGame();
		
	}
	
	/**
	 * Creates tiles (JButton components) according to the dimension and type passed
	 * @param dimension - number of tiles per dimension
	 * @param type - boolean to specify the text input type If set true input type = TEXT else INPUT
	 */
	public void createTiles(int dimension, boolean type) {
		nm.getTileList().clear();
		
		if (type) {
			String emptyString = "                                                                                                                                                             ";
			String value = nv.getTextInput().getText() + emptyString;
			for (int i = 0; i < (dimension*dimension); i++) {
				if (i != ((dimension*dimension) - 1)) {
					nm.getTileList().add(nv.new Tile(value.charAt(i), TEXT));
					nm.getTileList().get(nm.getTileList().size() - 1).setRow(i/dimension);
					nm.getTileList().get(nm.getTileList().size() - 1).setCol(i%dimension);
					//System.out.println(nm.getTileList().get(nm.getTileList().size() - 1).toString());
				}
				else {
					nm.getTileList().add(nv.new Tile(0, TEXT));
					nm.getTileList().get(nm.getTileList().size() - 1).setRow(i/dimension);
					nm.getTileList().get(nm.getTileList().size() - 1).setCol(i%dimension);
					//System.out.println(nm.getTileList().get(nm.getTileList().size() - 1).toString());
				}
			}
		}
		else {
			for (int i = 0; i < (dimension*dimension); i++) {
				if (i != ((dimension*dimension)-1)) {
					nm.getTileList().add(nv.new Tile((i+1), INTEGER));
					nm.getTileList().get(nm.getTileList().size() - 1).setRow(i/dimension);
					nm.getTileList().get(nm.getTileList().size() - 1).setCol(i%dimension);
					//System.out.println(nm.getTileList().get(nm.getTileList().size() - 1).toString());
				}
				else {
					nm.getTileList().add(nv.new Tile(0, INTEGER));
					nm.getTileList().get(nm.getTileList().size() - 1).setRow(i/dimension);
					nm.getTileList().get(nm.getTileList().size() - 1).setCol(i%dimension);
					//System.out.println(nm.getTileList().get(nm.getTileList().size() - 1).toString());
				}
			}
		}
	}
	
	
	/**
	 * This method is used to add the tiles on the main board as dimensions increasing tiles will also adding on the mainboard.
	 */
	public void addTiles() {
		for (int i = 0; i < nm.getTileList().size(); i++) {
			nm.getTileList().get(i).addActionListener(this);
			nv.mainBoard.add(nm.getTileList().get(i));
		}
	}
	
	/**
	 * Resets the game board and place new tiles
	 * @param type - boolean to specify the text input type If set true input type = TEXT else INPUT
	 */
	public void placeTiles(boolean type) {
		nv.createMainBoard((int) nv.getDimensionSelect().getSelectedItem(), (int) nv.getDimensionSelect().getSelectedItem());
		createTiles((int) nv.getDimensionSelect().getSelectedItem(), type);
		setSolution();
		addTiles();
		randomize();
	}
	
	
	/**
	 * saves the arrangement of tiles as designed by the user in an array list called solution
	 */
	public void setSolution() {
		
		nm.getSolution().clear();
		
		for(int i = 0; i < nm.getTileList().size(); i++) {
			nm.getSolution().add(nm.getTileList().get(i).getTileValue());
		}	
		
		for (int i = 0; i < nm.getSolution().size(); i++) {
			System.out.print(nm.getSolution().get(i) + " ");
		}
		System.out.println();
		
		
	}

	
	/**
	 * randomizes the arrangement of tiles
	 */
	public void randomize() {
		

		int max = (int) nv.getDimensionSelect().getSelectedItem() * (int) nv.getDimensionSelect().getSelectedItem();
		
		for (int i = 0; i < max; i++) {
			int randomIndex = (int)Math.floor(Math.random()*(max));
			
			if (nm.getTileList().get(randomIndex).getTileValue() == 0 && randomIndex != i) {
					
				nm.getTileList().get(randomIndex).setTileValue(nm.getTileList().get(i).getTileValue());
				nm.getTileList().get(randomIndex).setText(nm.getTileList().get(i).getText());
				nm.getTileList().get(randomIndex).setBackground(nv.selectedColorSet.tileAccentColor);
				nm.getTileList().get(randomIndex).setEnabled(true);
				nm.getTileList().get(i).setEnabled(false);
				nm.getTileList().get(i).setTileValue(0);
				nm.getTileList().get(i).setText("");
				nm.getTileList().get(i).setBackground(nv.selectedColorSet.accentBackgroundColor);
			
			}
			else if (nm.getTileList().get(i).getTileValue() == 0 && randomIndex != i) {
				nm.getTileList().get(i).setTileValue(nm.getTileList().get(randomIndex).getTileValue());
				nm.getTileList().get(i).setText(nm.getTileList().get(randomIndex).getText());
				nm.getTileList().get(i).setBackground(nv.selectedColorSet.tileAccentColor);
				nm.getTileList().get(i).setEnabled(true);
				nm.getTileList().get(randomIndex).setEnabled(false);
				nm.getTileList().get(randomIndex).setTileValue(0);
				nm.getTileList().get(randomIndex).setText("");
				nm.getTileList().get(randomIndex).setBackground(nv.selectedColorSet.accentBackgroundColor);
			}
			
			else if (nm.getTileList().get(randomIndex).getTileValue() != 0 && randomIndex != i) {
				int tempValue = nm.getTileList().get(i).getTileValue();
				String tempText =   nm.getTileList().get(i).getText();
				nm.getTileList().get(i).setTileValue(nm.getTileList().get(randomIndex).getTileValue());
				nm.getTileList().get(i).setText(nm.getTileList().get(randomIndex).getText());
				
				nm.getTileList().get(randomIndex).setTileValue(tempValue);
				nm.getTileList().get(randomIndex).setText(tempText);
			}
		}
	}
	
	/**
	 * to check number of tiles arranged according to the solution
	 * @return - returns the number of tiles in correct sequence
	 */
	public int checkTiles() {
		
		int matches = 0;
		
		for (int i = 0; i < nm.getTileList().size(); i++) {
			if (nm.getTileList().get(i).getTileValue() == nm.getSolution().get(i)) {
				matches++;
			}
		}
		return matches;
	}
	
	/**
	 * moves a tile passed in the argument by replacing it with the nearest tile with value of 0
	 * @param t - tile object to move
	 */
	public void moveTile(Tile t) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("../bin/sounds/select click.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (Exception e) {
		}
		
		int r = t.getRow();
		int c = t.getCol();
		int dimension = (int) nv.getDimensionSelect().getSelectedItem();
		int dimSq = dimension*dimension;
		int up = -1;
		int down = -1;
		int left = -1;
		int right = -1;
		
		if (r != 0)
			up = (r-1)*dimension + c;
		
		if (r < (dimension-1))
			down = (r+1)*dimension + c;
		
		if (c != 0)
			left = r*dimension + (c-1);
		
		if (c < (dimension - 1))
			right = r*dimension + (c+1);
		
		
		if (up != -1 && (nm.getTileList().get(up).getTileValue() == 0)) {
			nm.getTileList().get(up).setTileValue(t.getTileValue());
			nm.getTileList().get(up).setText(t.getText());
			nm.getTileList().get(up).setBackground(nv.selectedColorSet.tileAccentColor);
			nm.getTileList().get(up).setEnabled(true);
			t.setEnabled(false);
			t.setTileValue(0);
			t.setText("");
			t.setBackground(nv.selectedColorSet.accentBackgroundColor);
			nm.setMoves(nm.getMoves() + 1);
		}
		else if (down != -1 && (nm.getTileList().get(down).getTileValue() == 0)) {
			nm.getTileList().get(down).setTileValue(t.getTileValue());
			nm.getTileList().get(down).setText(t.getText());
			nm.getTileList().get(down).setBackground(nv.selectedColorSet.tileAccentColor);
			nm.getTileList().get(down).setEnabled(true);
			t.setEnabled(false);
			t.setTileValue(0);
			t.setText("");
			t.setBackground(nv.selectedColorSet.accentBackgroundColor);
			nm.setMoves(nm.getMoves() + 1);
		}
		else if (left != -1 && (nm.getTileList().get(left).getTileValue() == 0)) {
			nm.getTileList().get(left).setTileValue(t.getTileValue());
			nm.getTileList().get(left).setText(t.getText());
			nm.getTileList().get(left).setBackground(nv.selectedColorSet.tileAccentColor);
			nm.getTileList().get(left).setEnabled(true);
			t.setEnabled(false);
			t.setTileValue(0);
			t.setText("");
			t.setBackground(nv.selectedColorSet.accentBackgroundColor);
			nm.setMoves(nm.getMoves() + 1);
		}
		else if (right != -1 && (nm.getTileList().get(right).getTileValue() == 0)) {
			nm.getTileList().get(right).setTileValue(t.getTileValue());
			nm.getTileList().get(right).setText(t.getText());
			nm.getTileList().get(right).setBackground(nv.selectedColorSet.tileAccentColor);
			nm.getTileList().get(right).setEnabled(true);
			t.setEnabled(false);
			t.setTileValue(0);
			t.setText("");
			t.setBackground(nv.selectedColorSet.accentBackgroundColor);
			nm.setMoves(nm.getMoves() + 1);
		}
		
		
		nv.movesDisplay.setText(Integer.toString(nm.getMoves()));
	}
	
	/**
	 * sets the properties of the game according to design mode
	 */
	public void setDesignMode() {
		nv.getTypeSelect().setEnabled(true);
		nv.getTypeSelect().setSelectedIndex(0);
		nv.getDimensionSelect().setEnabled(true);
		nv.getRand().setEnabled(true);
		nv.getShow().setEnabled(false);
		nv.getHide().setEnabled(false);
		nv.getLoad().setEnabled(true);
		nv.getSave().setEnabled(true);
		nv.getMainBoard().setBackground(nv.getBackground());
		nv.getTextInput().setEditable(false);
		nv.getTextSubmit().setEnabled(false);
		nv.getDesign().setSelected(true);
		placeTiles(INTEGER);
		nv.getDesign().setEnabled(false);
		nv.getPlay().setEnabled(true);
		gameReset();
	}
	
	/**
	 * sets the properties of the game according to play mode
	 */
	public void setPlayMode() {
		nv.getTypeSelect().setEnabled(false);
		nv.getDimensionSelect().setEnabled(false);
		nv.getRand().setEnabled(false);
		nv.getShow().setEnabled(true);
		nv.getHide().setEnabled(true);
		nv.getLoad().setEnabled(false);
		nv.getSave().setEnabled(true);
		nv.getTextInput().setEditable(false);
		nv.getTextSubmit().setEnabled(false);
		nm.setState(nm.PLAY);
		NumPuzModel.gamesPlayed++;
		nv.getPlay().setSelected(true);
		startTimer();
		nv.getPlay().setEnabled(false);
		nv.getDesign().setEnabled(true);
	}
	
	/**
	 * shows the solution to the user by displaying correct sequence
	 * @param type - whether the selected mode is INTEGER or TEXT
	 */
	public void showSolution(boolean type) {
		
		if (type == TEXT) {
			for (int i = 0; i < nm.getTileList().size(); i++) {
				if (nm.getSolution().get(i) == 0) {
					nm.getTileList().get(i).setText("");
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.accentBackgroundColor);
				}
				else {
					nm.getTileList().get(i).setText((char)((nm.getSolution().get(i)-32) + ' ') + "");
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.tileAccentColor);
				}
				
				if(nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setEnabled(true);
				}
			}
		}
		else {
			for (int i = 0; i < nm.getTileList().size(); i++) {
				if (nm.getSolution().get(i) == 0) {
					nm.getTileList().get(i).setText("");
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.accentBackgroundColor);
				}
				else {
					nm.getTileList().get(i).setText(Integer.toString(nm.getSolution().get(i)));
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.tileAccentColor);
				}
				
				if(nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setEnabled(true);
				}
			}
		}
		
	}
	
	/**
	 * hides the solution of the game
	 * @param type - whether the selected mode is INTEGER or TEXT
	 */
	public void hideSolution(boolean type) {
		
		if (type == TEXT) {
			for (int i = 0; i < nm.getTileList().size(); i++) {
				if (nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setText("");
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.accentBackgroundColor);
				}
				else {
					nm.getTileList().get(i).setText((char)nm.getTileList().get(i).getTileValue() + "");
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.tileAccentColor);
				}
				
				if(nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setEnabled(false);
				}
			}
		}
		else {
			for (int i = 0; i < nm.getTileList().size(); i++) {
				if (nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setText("");
				}
				else {
					nm.getTileList().get(i).setText(Integer.toString(nm.getTileList().get(i).getTileValue()));
					nm.getTileList().get(i).setBackground(nv.selectedColorSet.tileAccentColor);
				}
				
				if(nm.getTileList().get(i).getTileValue() == 0) {
					nm.getTileList().get(i).setEnabled(false);
				}
			}
		}
		
	}
	
	/**
	 * starts the timer
	 */
	public void startTimer() {
		
		if (timer == null) {
			
			timer = new Timer();
			
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					nm.setSecTime(nm.getSecTime() + 1);
					int sec = nm.getSecTime();
					int hours = sec / 3600;
					int minutes = (sec % 3600) / 60;
					int seconds = (sec % 60);
					nv.timerDisplay.setText(hours + "h " + minutes + "m " + seconds + "s");
				}
				
			};
			
			timer.scheduleAtFixedRate(timerTask, 0, 1000);
		}
	}
	
	/**
	 * reset timer
	 */
	public void resetTimer() {
		
		if (timer != null) {
			timer.cancel();
			timer = null;
			nm.setSecTime(0);
		}
		
	}
	
	/**
	 * resets the game and set everything to the default
	 */
	public void gameReset() {
		nm.setState(nm.PAUSED);
		nm.setMoves(0);
		nm.setPoints(0);
		nv.movesDisplay.setText(Integer.toString(nm.getMoves()));
		resetTimer();
		nv.timerDisplay.setText("0s");
		nv.pointsDisplay.setText("0");
	}
	
	/**
	 * defines the interface for color selector
	 */
	public void colorSelector() {
		
		colorSelect = new JFrame();
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(null);
		colorPanel.setBounds(0,0,512,253);
		colorSelect.setTitle("Select Color");
		
		nv.colorOption1 = new JButton("Color 1");
		nv.colorOption2 = new JButton("Color 2");
		nv.colorOption3 = new JButton("Color 3");
		nv.colorOption4 = new JButton("Color 4");
		
		nv.colorOption1.setIcon(nv.colorIcon1);
		nv.colorOption2.setIcon(nv.colorIcon2);
		nv.colorOption3.setIcon(nv.colorIcon3);
		nv.colorOption4.setIcon(nv.colorIcon4);
		
		nv.colorOption1.setBackground(nv.selectedColorSet.accentBackgroundColor);
		nv.colorOption2.setBackground(nv.selectedColorSet.accentBackgroundColor);
		nv.colorOption3.setBackground(nv.selectedColorSet.accentBackgroundColor);
		nv.colorOption4.setBackground(nv.selectedColorSet.accentBackgroundColor);
		
		nv.colorOption1.setBounds(30, 60, 80, 80);
		nv.colorOption2.setBounds(150, 60, 80, 80);
		nv.colorOption3.setBounds(270, 60, 80, 80);
		nv.colorOption4.setBounds(390, 60, 80, 80);
		
		nv.colorOption1.setFocusable(false);
		nv.colorOption1.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		nv.colorOption2.setFocusable(false);
		nv.colorOption2.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		nv.colorOption3.setFocusable(false);
		nv.colorOption3.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		nv.colorOption4.setFocusable(false);
		nv.colorOption4.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		nv.colorOption1.setText("1");
		nv.colorOption2.setText("2");
		nv.colorOption3.setText("3");
		nv.colorOption4.setText("4");
		
		nv.colorOption1.addActionListener(this);
		nv.colorOption2.addActionListener(this);
		nv.colorOption3.addActionListener(this);
		nv.colorOption4.addActionListener(this);
		
		colorPanel.setBackground(nv.selectedColorSet.accentBackgroundColor);
		colorSelect.setResizable(false);
		colorSelect.setSize(512,253);
		colorSelect.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		colorSelect.setLocationRelativeTo(null);
		colorPanel.add(nv.colorOption1);
		colorPanel.add(nv.colorOption2);
		colorPanel.add(nv.colorOption3);
		colorPanel.add(nv.colorOption4);
		colorSelect.add(colorPanel);
		colorSelect.setVisible(true);
	}
	
	/**
	 * creates all the components of the game like mainboard, menu, settings
	 */
	public void createGame() {
		nv.createMenu();
		nv.createMainBoard((int)nv.getDimensionSelect().getSelectedItem(),(int)nv.getDimensionSelect().getSelectedItem());
		nv.createSettings();
		nv.createTextPanel();
		nv.add(nv.mainBoard);
		nv.add(nv.settings);
		nv.add(nv.textPanel);
		createTiles((int)nv.getDimensionSelect().getSelectedItem(), false);
		setSolution();
		
		addTiles();
		randomize();
		nv.setVisible(true);
	}
	
	/**
	 * returns gameDataAndConfig of the game as a string
	 * @return - returned String data
	 */
	public String getUserData() {
		String separator = ";";
		String dim = (int)nv.getDimensionSelect().getSelectedItem() + "";
		String gameType = nv.getTypeSelect().getSelectedItem().toString();
		String gameConfig = "";
		for (int i = 0; i < nm.getTileList().size(); i++) {
			if (nv.getTypeSelect().getSelectedIndex() == 0) {
				gameConfig += nm.getTileList().get(i).getTileValue() + "";
				if (i != nm.getTileList().size() - 1)
					gameConfig += ",";
			}
			else {
				gameConfig += (char)nm.getTileList().get(i).getTileValue() + "";
				if (i != nm.getTileList().size() - 1)
					gameConfig += ";";
			}
		}
		String moves = nm.getMoves() + "";
		String time = nm.getSecTime() + "";
		String points = nm.getPoints() + "";
		String gameDataAndConfig = dim + separator + gameType + separator + gameConfig + separator + moves + separator + points + separator + time;
		return gameDataAndConfig;
	}
	
	/**
	 * performs actions according the event calls by different Components
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nv.getDimensionSelect()) {
			placeTiles(INTEGER);
			nv.getTypeSelect().setSelectedIndex(0);
			nv.setVisible(true);
		}
		
		if (e.getSource() == nv.getLogoButton()) {
				nv.new Popup();
		}
		
		if (e.getSource() == nv.getTypeSelect()) {
			
			if(nv.getTypeSelect().getSelectedIndex() == 0) {
				nv.getTextInput().setEditable(false);
				nv.getTextSubmit().setEnabled(false);
				placeTiles(INTEGER);
				nv.getTypeSelect().setSelectedIndex(0);
				nv.setVisible(true);
			}
			else {
				nv.getTextInput().setEditable(true);
				nv.getTypeSelect().setEditable(true);
				nv.getTextSubmit().setEnabled(true);
			}
			
		}
		
		if (e.getSource() == nv.getDesign()) {
			if (NumPuzModel.gamesPlayed != 0) {
				NumPuzView.SplashScreen.startSplashScreen(nv.errIcon);
			}
			setDesignMode();
		}
		
		if (e.getSource() == nv.getPlay()) {
			setPlayMode();
		}
		
		if (e.getSource() == nv.getTextSubmit()) {
			System.out.println("Success");
			placeTiles(TEXT);
			nv.getTextInput().setText("");
			nv.getTextSubmit().setEnabled(false);
			nv.setVisible(true);
		}
		
		if (e.getSource() == nv.getRand()) {
			randomize();
		}
		
		if (e.getSource() == nv.exitItem) {
			System.exit(0);
		}
		
		if (e.getSource() == nv.colorItem) {
			colorSelector();
		}
		
		if (e.getSource() == nv.newGameItem) {
			
			if (nv.getPlay().isSelected()) {
				NumPuzView.SplashScreen.startSplashScreen(nv.errIcon);
			}
			
			setDesignMode();
			nv.getDesign().setSelected(true);
		}
		
		if (e.getSource() == nv.aboutItem) {
			nv.new Popup();			
		}
		
		if (e.getSource() == nv.getShow()) {
			if (nv.getTypeSelect().getSelectedIndex() == 0) {
				showSolution(INTEGER);
			}
			else {
				showSolution(TEXT);
			}
			nm.setState(nm.PAUSED);
			
		}
		
		if (e.getSource() == nv.getHide()) {
			if (nv.getTypeSelect().getSelectedIndex() == 0) {
				hideSolution(INTEGER);
			}
			else {
				hideSolution(TEXT);
			}
			nm.setState(nm.PLAY);
		}
		
		if (e.getSource() == nv.colorOption1) {
			nv.setSelectedColorSet(nv.set1);	
			createGame();
			setDesignMode();
			colorSelect.dispatchEvent(new WindowEvent(colorSelect, WindowEvent.WINDOW_CLOSING));
		}
		
		if (e.getSource() == nv.colorOption2) {
			nv.setSelectedColorSet(nv.set2);
			createGame();
			setDesignMode();
			colorSelect.dispatchEvent(new WindowEvent(colorSelect, WindowEvent.WINDOW_CLOSING));
		}
		
		if (e.getSource() == nv.colorOption3) {
			nv.setSelectedColorSet(nv.set3);
			createGame();
			setDesignMode();
			colorSelect.dispatchEvent(new WindowEvent(colorSelect, WindowEvent.WINDOW_CLOSING));
		}
		
		if (e.getSource() == nv.colorOption4) {
			nv.setSelectedColorSet(nv.set4);
			createGame();
			setDesignMode();
			colorSelect.dispatchEvent(new WindowEvent(colorSelect, WindowEvent.WINDOW_CLOSING));
		}
		
		if (nm.isState()) {
			for (int i = 0; i < nm.getTileList().size(); i++) {
				if (e.getSource() == nm.getTileList().get(i)) {
					moveTile(nm.getTileList().get(i));
					int matches = checkTiles();
					nm.setPoints(matches*2);
					nv.pointsDisplay.setText(Integer.toString(nm.getPoints()));
					if (((int)nv.getDimensionSelect().getSelectedItem()*(int)nv.getDimensionSelect().getSelectedItem()) == matches) {
						System.out.println("Winner");
						NumPuzView.SplashScreen.startSplashScreen(nv.winnerIcon);
						setDesignMode();
					}
				}
			}
		}
		else {
			
		}
	}
	
}
