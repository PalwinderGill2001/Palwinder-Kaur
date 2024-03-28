package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * Handles the interface of the game
 * @author suraj
 *
 */
public class NumPuzView extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	protected ImageIcon logo = new ImageIcon(NumPuzView.class.getResource("/images/game.png"));
	private ImageIcon numPuzBadge = new ImageIcon(NumPuzView.class.getResource("/images/gamelogo.png"));
	private ImageIcon gameabout = new ImageIcon(NumPuzView.class.getResource("/images/gameabout.png"));
	private ImageIcon newGameIcon = new ImageIcon(NumPuzView.class.getResource("/images/iconnew.png"));
	private ImageIcon exitGameIcon = new ImageIcon(NumPuzView.class.getResource("/images/iconexit.png"));
	private ImageIcon aboutIcon = new ImageIcon(NumPuzView.class.getResource("/images/iconabt.png"));
	private ImageIcon colorIcon = new ImageIcon(NumPuzView.class.getResource("/images/iconcolor.png"));
	public ImageIcon winnerIcon = new ImageIcon(NumPuzView.class.getResource("/images/gamewinner.png"));
	public ImageIcon errIcon = new ImageIcon(NumPuzView.class.getResource("/images/gameerr.png"));
	public ImageIcon colorIcon1 = new ImageIcon(NumPuzView.class.getResource("/images/set1.png"));
	public ImageIcon colorIcon2 = new ImageIcon(NumPuzView.class.getResource("/images/set2.png"));
	public ImageIcon colorIcon3 = new ImageIcon(NumPuzView.class.getResource("/images/set3.png"));
	public ImageIcon colorIcon4 = new ImageIcon(NumPuzView.class.getResource("/images/set4.png"));
	
	public JButton colorOption1;
	public JButton colorOption2;
	public JButton colorOption3;
	public JButton colorOption4;
	
	public JPanel mainBoard = new JPanel();
	
	public JLabel movesLabel = new JLabel("Moves");
	public JLabel timerLabel = new JLabel("Timer");
	public JLabel pointsLabel = new JLabel("Points");
	
	public JTextField movesDisplay  = new JTextField();
	public JTextField timerDisplay = new JTextField();
	public JTextField pointsDisplay = new JTextField();

	public JMenuBar menubar = new JMenuBar();
	public JMenu gameMenu = new JMenu("Game");
	public JMenu helpMenu = new JMenu("Help");
	
	public JMenuItem newGameItem = new JMenuItem("New");
	public JMenuItem exitItem = new JMenuItem("Exit");
	public JMenuItem colorItem = new JMenuItem("Colors");
	
	public JMenuItem aboutItem = new JMenuItem("About");
	
	
	private Color babyPowder = new Color(255,252,249);
	private Color jetBlack = new Color(11,5,0);
	private Color androidGreen = new Color(155, 197, 61);
	private Color minionYellow = new Color(253, 231, 76);
	private Color pictorialCarmine = new Color(182, 23, 75);
	private Color lavander = new Color(207,211,250);
	private Color pinkPurple = new Color(164,103,160);
	private Color deepLavander = new Color(168,161,221);
	private Color slateYellow = new Color(249,141,47);
	private Color vistaraPurple = new Color(62,15,39);
	
	public ColorSet set1 = new ColorSet();
	public ColorSet set2 = new ColorSet(lavander , pinkPurple, deepLavander ,babyPowder);
	public ColorSet set3 = new ColorSet(minionYellow, minionYellow, androidGreen, jetBlack);
	public ColorSet set4 = new ColorSet(jetBlack, slateYellow, pictorialCarmine, vistaraPurple);
	
	public JPanel settings = new JPanel();
	public JPanel settingsTop = new JPanel();
	public JPanel settingsCenter = new JPanel();
	public JPanel settingsBottom = new JPanel();
	
	public ColorSet selectedColorSet = set3;
	
	public JPanel textPanel = new JPanel();
	
	private JTextField textInput = new JTextField();
	
	private JComboBox<Integer> dimensionSelect;
	private JComboBox<String> typeSelect;
	
	private JRadioButton design = new JRadioButton("Design");
	private JRadioButton play = new JRadioButton("Play");
	
	ButtonGroup modeSelect = new ButtonGroup();
	
	//private JTextArea textArea = new JTextArea();
	
	private GameButton logoButton = new GameButton();
	private GameButton textSubmit = new GameButton("Submit");
	private GameButton show = new GameButton("Show");
	private GameButton hide = new GameButton("Hide");
	private GameButton save = new GameButton("Save");
	private GameButton load = new GameButton("Load");
	private GameButton rand = new GameButton("Random");
	
	
	/**
	 * Default constructor for GameController class
	 */
	NumPuzView() {
		
		this.setSize(760, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("NumPuz");
		this.setIconImage(logo.getImage());
		this.setLocationRelativeTo(null);
		//adding all the panel components to the main frame
//		this.add(mainBoard);
//		this.add(settings);
//		this.add(textPanel);
	}

	/**
	 * @return the dimensionSelect
	 */
	public JComboBox<Integer> getDimensionSelect() {
		return dimensionSelect;
	}

	/**
	 * @param dimensionSelect the dimensionSelect to set
	 */
	public void setDimensionSelect(JComboBox<Integer> dimensionSelect) {
		this.dimensionSelect = dimensionSelect;
	}

	/**
	 * @return the typeSelect
	 */
	public JComboBox<String> getTypeSelect() {
		return typeSelect;
	}

	/**
	 * @param typeSelect the typeSelect to set
	 */
	public void setTypeSelect(JComboBox<String> typeSelect) {
		this.typeSelect = typeSelect;
	}
	
	
	
	/**
	 * @return the design
	 */
	public JRadioButton getDesign() {
		return design;
	}

	/**
	 * @return the play
	 */
	public JRadioButton getPlay() {
		return play;
	}

	/**
	 * @return the logoButton
	 */
	public GameButton getLogoButton() {
		return logoButton;
	}

	/**
	 * @return the textSubmit
	 */
	public GameButton getTextSubmit() {
		return textSubmit;
	}

	/**
	 * @return the show
	 */
	public GameButton getShow() {
		return show;
	}

	/**
	 * @return the hide
	 */
	public GameButton getHide() {
		return hide;
	}

	/**
	 * @return the save
	 */
	public GameButton getSave() {
		return save;
	}

	/**
	 * @return the load
	 */
	public GameButton getLoad() {
		return load;
	}

	/**
	 * @return the rand
	 */
	public GameButton getRand() {
		return rand;
	}

	/**
	 * @return the textInput
	 */
	public JTextField getTextInput() {
		return textInput;
	}

	/**
	 * @return the mainBoard
	 */
	public JPanel getMainBoard() {
		return mainBoard;
	}

	/**
	 * @param mainBoard the mainBoard to set
	 */
	public void setMainBoard(JPanel mainBoard) {
		this.mainBoard = mainBoard;
	}
	
	

	/**
	 * @return the selectedColorSet
	 */
	public ColorSet getSelectedColorSet() {
		return selectedColorSet;
	}



	/**
	 * @param selectedColorSet the selectedColorSet to set
	 */
	public void setSelectedColorSet(ColorSet selectedColorSet) {
		this.selectedColorSet = selectedColorSet;
	}



	/**
	 * creates the menu bar for the game
	 */
	public void createMenu() {
		
		UIManager.put("MenuBar.background", new Color(32,32,32));
		UIManager.put("PopupMenu.border", new LineBorder(new Color(32,32,32)));
		UIManager.put("MenuBar.border", new LineBorder(new Color(32,32,32)));
		gameMenu.setForeground(selectedColorSet.foreground);
		helpMenu.setForeground(selectedColorSet.foreground);
		
		newGameItem.setBackground(new Color(32,32,32));
		newGameItem.setForeground(selectedColorSet.foreground);
		newGameItem.setIcon(newGameIcon);
		
		exitItem.setBackground(new Color(32,32,32));
		exitItem.setForeground(selectedColorSet.foreground);
		exitItem.setIcon(exitGameIcon);
		
		aboutItem.setBackground(new Color(32,32,32));
		aboutItem.setForeground(selectedColorSet.foreground);
		aboutItem.setIcon(aboutIcon);
		
		colorItem.setBackground(new Color(32,32,32));
		colorItem.setForeground(selectedColorSet.foreground);
		colorItem.setIcon(colorIcon);
		
		gameMenu.add(newGameItem);
		gameMenu.add(colorItem);
		gameMenu.add(exitItem);
		
		
		helpMenu.add(aboutItem);
		
		menubar.add(gameMenu);
		menubar.add(helpMenu);
		this.setJMenuBar(menubar);
	}

	/**
	 * Creates layout for the main board of the game
	 * @param rows - number of rows
	 * @param col - number columns
	 */
	public void createMainBoard(int rows, int col) {
		this.mainBoard.setBounds(0, 0, 460, 460);
		this.mainBoard.setBackground(selectedColorSet.background);
		this.mainBoard.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		this.mainBoard.removeAll();
		this.mainBoard.setLayout(new GridLayout(rows, col,3,3));
	}
	
	/**
	 * Creates the panel for settings of the game
	 */
	public void createSettings() {
		
		setButtonColors();
		
		//dimensionSelect = new JComboBox<Integer>(dimensionList);
		modeSelect.add(design);
		modeSelect.add(play);
		design.setSelected(true);
		
		design.setForeground(selectedColorSet.foreground);
		design.setBackground(selectedColorSet.accentBackgroundColor);
		design.setFocusable(false);
		//design.addActionListener(this);
		play.setForeground(selectedColorSet.foreground);
		play.setFocusable(false);
		play.setBackground(selectedColorSet.accentBackgroundColor);
		//play.addActionListener(this);
		
		this.settings.setBounds(460, 0, 300, 600);
		this.settings.setBackground(new Color(94,94,94));
		this.settings.setLayout(new BorderLayout());
		
		settingsTop.setBackground(selectedColorSet.accentBackgroundColor);
		settingsTop.setLayout(null);
		settingsCenter.setBackground(selectedColorSet.accentBackgroundColor);
		settingsCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 30));
		settingsBottom.setBackground(selectedColorSet.accentBackgroundColor);
		settingsBottom.setPreferredSize(new Dimension(300, 270));
		settingsBottom.setLayout(null);
		
		settingsTop.setPreferredSize(new Dimension(300, 150));
		this.settings.add(settingsTop, BorderLayout.NORTH);
		this.settings.add(settingsCenter, BorderLayout.CENTER);
		this.settings.add(settingsBottom, BorderLayout.SOUTH);
		
		//dimensionSelect.addActionListener(this);
		this.logoButton.setIcon(numPuzBadge);
		this.logoButton.setBounds(78, 50, 124, 40);
		this.logoButton.setFocusable(false);
		this.logoButton.setBackground(selectedColorSet.accentBackgroundColor);
		//this.logoButton.addActionListener(this);
		
		design.setBounds(70, 110, 65, 20);
		play.setBounds(150, 110, 60, 20);
		
		this.settingsTop.add(logoButton);
		this.settingsTop.add(design);
		this.settingsTop.add(play);
		
		this.settingsCenter.add(dimensionSelect);
		//dimensionSelect.addActionListener(this);
		dimensionSelect.setBackground(selectedColorSet.accentBackgroundColor);
		dimensionSelect.setForeground(selectedColorSet.foreground);
		dimensionSelect.setFocusable(false);
		show.setEnabled(false);
		hide.setEnabled(false);
		
		this.settingsCenter.add(show);
		this.settingsCenter.add(hide);
		this.settingsCenter.add(save);
		this.settingsCenter.add(load);
		this.settingsCenter.add(rand);
		
		//typeSelect = new JComboBox<String>(typeSelections);
		//typeSelect.addActionListener(this);
		typeSelect.setFocusable(false);
		typeSelect.setBackground(selectedColorSet.accentBackgroundColor);
		typeSelect.setForeground(selectedColorSet.foreground);
		typeSelect.setFocusable(false);
		
		this.settingsCenter.add(typeSelect);
		
		//this.settingsCenter.add(finish);	
		//this.textArea.setBounds(60, 5, 180, 190);
		//this.textArea.setBackground(Color.white);
		//this.settingsBottom.add();
		
		movesLabel.setBackground(selectedColorSet.accentBackgroundColor);
		movesLabel.setBounds(58, 30, 50, 20);
		movesLabel.setForeground(selectedColorSet.foreground);
		
		movesDisplay.setBackground(selectedColorSet.accentBackgroundColor);
		movesDisplay.setBounds(60, 5, 34, 20);
		movesDisplay.setForeground(selectedColorSet.foreground);
		movesDisplay.setHorizontalAlignment(JTextField.CENTER);
		movesDisplay.setText("0");
		movesDisplay.setEditable(false);
		movesDisplay.setBorder(BorderFactory.createLineBorder(selectedColorSet.accentBackgroundColor));
		movesDisplay.setFont(new Font("Comic Sans",Font.BOLD, 18));
		
		timerLabel.setBackground(selectedColorSet.accentBackgroundColor);
		timerLabel.setBounds(185, 30, 50, 20);
		timerLabel.setForeground(selectedColorSet.foreground);
		
		timerDisplay.setBackground(selectedColorSet.accentBackgroundColor);
		timerDisplay.setBounds(145, 5, 120, 20);
		timerDisplay.setForeground(selectedColorSet.foreground);
		timerDisplay.setHorizontalAlignment(JTextField.CENTER);
		timerDisplay.setText("0s");
		timerDisplay.setEditable(false);
		timerDisplay.setBorder(BorderFactory.createLineBorder(selectedColorSet.accentBackgroundColor));
		timerDisplay.setFont(new Font("Comic Sans",Font.BOLD, 18));
		
		pointsLabel.setBackground(selectedColorSet.accentBackgroundColor);
		pointsLabel.setBounds(120, 100, 50, 20);
		pointsLabel.setForeground(selectedColorSet.foreground);
		
		pointsDisplay.setBackground(selectedColorSet.accentBackgroundColor);
		pointsDisplay.setBounds(122, 75, 34, 20);
		pointsDisplay.setForeground(selectedColorSet.foreground);
		pointsDisplay.setHorizontalAlignment(JTextField.CENTER);
		pointsDisplay.setText("0");
		pointsDisplay.setEditable(false);
		pointsDisplay.setBorder(BorderFactory.createLineBorder(selectedColorSet.accentBackgroundColor));
		pointsDisplay.setFont(new Font("Comic Sans",Font.BOLD, 18));
		
		settingsBottom.add(movesLabel);
		settingsBottom.add(movesDisplay);
		settingsBottom.add(timerLabel);
		settingsBottom.add(timerDisplay);
		settingsBottom.add(pointsLabel);
		settingsBottom.add(pointsDisplay);
	}
	
	public void setButtonColors() {
		textSubmit.setForeground(selectedColorSet.foreground);
		show.setForeground(selectedColorSet.foreground);
		hide.setForeground(selectedColorSet.foreground);
		save.setForeground(selectedColorSet.foreground);
		load.setForeground(selectedColorSet.foreground);
		rand.setForeground(selectedColorSet.foreground);
		
		textSubmit.setBackground(selectedColorSet.accentBackgroundColor);
		show.setBackground(selectedColorSet.accentBackgroundColor);
		hide.setBackground(selectedColorSet.accentBackgroundColor);
		save.setBackground(selectedColorSet.accentBackgroundColor);
		load.setBackground(selectedColorSet.accentBackgroundColor);
		rand.setBackground(selectedColorSet.accentBackgroundColor);
	}
	
	
	/**
	 * Creates the bottom text panel
	 */
	public void createTextPanel() {
		this.textPanel.setBounds(0, 460, 460, 140);
		this.textPanel.setBackground(selectedColorSet.accentBackgroundColor);
		this.textPanel.setLayout(null);
		
		this.textInput.setFont(new Font("Comic Sans", Font.PLAIN, 15));
		this.textInput.setText("Enter string here!");
		this.textInput.setBounds(20, 30, 345, 30);
		this.textInput.setEditable(false);
		
		this.textSubmit.setBounds(370, 30, 70, 30);
		this.textSubmit.setEnabled(false);
		//this.textSubmit.addActionListener(this);
		
		this.textPanel.add(textInput, BorderLayout.WEST);
		this.textPanel.add(textSubmit, BorderLayout.EAST);
	}
	
	/**
	 * This is a Gamebutton class used to extend JButton 
	 * @author Suraj, Palwinder Kaur
	 *
	 */
	@SuppressWarnings("serial")
	public class GameButton extends JButton {
		
		GameButton() {
			this.setFocusable(false);
			this.setBackground(selectedColorSet.accentBackgroundColor);
			this.setForeground(selectedColorSet.foreground);
			this.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		}
		
		GameButton(String text) {
			this();
			this.setText(text);
		}
		
	}
	
	/**
	 * This is the Tile class which is extending JButton. Tile class contains the method for tiles.
	 * @author Palwinder Kaur Suraj
	 *
	 */
	protected class Tile extends JButton {
		
		/**
		 * Initialized the private variables.
		 */
		private static final long serialVersionUID = 1L;
		private int tileValue;
		private int row;
		private int col;
		
		
		
		/**
		 * @return the tileValue
		 */
		public int getTileValue() {
			return tileValue;
		}

		/**
		 * @param tileValue the tileValue to set
		 */
		public void setTileValue(int tileValue) {
			this.tileValue = tileValue;
		}

		/**
		 * @return the row
		 */
		public int getRow() {
			return row;
		}

		/**
		 * @param row the row to set
		 */
		public void setRow(int row) {
			this.row = row;
		}

		/**
		 * @return the col
		 */
		public int getCol() {
			return col;
		}

		/**
		 * @param col the col to set
		 */
		public void setCol(int col) {
			this.col = col;
		}
		
		
		/**
		 * Default constructor for Tile class
		 */
		public Tile() {
			this.setFont(new Font("Comic Sans", Font.BOLD, 35));
			this.setFocusable(false);
			this.setBackground(selectedColorSet.tileAccentColor);
			this.setBorder(BorderFactory.createEmptyBorder());
		}
		
		/**
		 * This is the tile method which is taking integer value as a parameter and designing the integer value for the tiles by using conditionals statements.
		 * @param value - value to be set for tile 
		 * @param type - Type of the value passed, TEXT if true else INTEGER
		 */
		protected Tile(int value, boolean type) {
			this();
			this.setFont(new Font("Comic Sans", Font.BOLD, 30));
			this.tileValue = value;
			if (this.tileValue == 0) {
				this.setBackground(Color.BLACK);
				this.setText("");
				this.setEnabled(false);
			}
			else {
				if (type) {
					String converted = "" + (char)value;
					this.setText(converted);
				}
				else {
					this.setText(Integer.toString(tileValue));
				}
				
			}
		}
		
		public String toString() {
			return "Row: " + row + " Column: " + col;
		}
	}
	
	/**
	 * This popup class is used to create the popup window while clicking on NumPuzzBadge in logo Button.
	 * @author Palwinder Kaur, Suraj
	 *
	 */
	public class Popup {
		
		JFrame popup = new JFrame();
		
		Popup() {
			
			popup.setIconImage(logo.getImage());
			popup.setLayout(new BorderLayout());
			popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			popup.setSize(500,500);
			popup.setContentPane(new JLabel(gameabout));
			popup.setResizable(false);
			popup.setLocationRelativeTo(null);
			popup.setVisible(true);
		}
		
		
	}
	
	/**
	 * 
	 * @author suraj
	 *
	 */
	public class ColorSet {
		
		public Color background; 
		public Color foreground; 
		public Color tileAccentColor; 
		public Color accentBackgroundColor; 
		
		ColorSet() {
			this.background = new Color(255,234,236);
			this.foreground = new Color(239, 45, 86);
			this.tileAccentColor = new Color(239, 45, 86);
			this.accentBackgroundColor = new Color(0,0,0);
		}
		
		ColorSet(Color background, Color foreground, Color tileAccentColor, Color accentBackgroundColor) {
			this.background = background;
			this.foreground = foreground;
			this.tileAccentColor = tileAccentColor;
			this.accentBackgroundColor = accentBackgroundColor;
		}
		
		
	}
	
	/**
	 * static class for splash screen
	 * @author suraj
	 *
	 */
	public static class SplashScreen {
		
		/**
		 * static method to start a splash screen
		 */
		public static void startSplashScreen() {
			JProgressBar pb = new JProgressBar();
			JPanel content = new JPanel();
			JLabel banner = new JLabel(new ImageIcon(NumPuzView.class.getResource("/images/banner.png")));
			JWindow window = new JWindow();
			window.setBounds(500, 250, 512, 253);
			
			banner.setBounds(0, 0, 512, 228);
			
			content.setLayout(null);
			content.setSize(512, 228);
			//content;
			pb.setSize(500,25);
            pb.setBorderPainted(false);
            pb.setStringPainted(true);
            pb.setBackground(new Color(36,36,36));
            pb.setForeground(new Color(230, 83, 25));
            pb.setValue(0);
            pb.setBounds(0, 228, 512, 25);
			
			content.add(banner);
			content.add(pb);
			window.getContentPane().add(content);
			window.setVisible(true);
			try {
				
				for(int i = 0; i < 101; i++) {
					Thread.sleep(10);
					pb.setValue(i);
				}
			    
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			//window.setVisible(false);
			window.dispose();
		}
		
		/**
		 * starts a splash screen for 3 seconds
		 * @param icon - image to display on the splash screen
		 */
		public static void startSplashScreen(ImageIcon icon) {
			
			JFrame screen = new JFrame();
			JLabel banner = new JLabel(icon);
			banner.setBounds(0, 0, 500, 500);
			screen.add(banner);
			screen.setSize(500,500);
			screen.setUndecorated(true);
			screen.setLocationRelativeTo(null);
			screen.setResizable(false);
			screen.setAlwaysOnTop(true);
			screen.setVisible(true);
			
			Timer timer = new Timer();
			
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					screen.setVisible(false);
				}
			};
			
			timer.schedule(task, 3000);
		}
		
	}
	
	
	
}
