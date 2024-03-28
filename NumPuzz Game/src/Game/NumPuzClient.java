package Game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class NumPuzClient extends Thread implements ActionListener {
	
	boolean paused = true;
	protected Button connectB;
	protected Button endB;
	protected Button sendB;
	protected Button newB;
	protected Button receiveB;
	protected Button playB;
	protected JTextField userText;
	protected JTextField portText;
	protected JTextField serverText;
	protected JTextArea console;
	
	protected Socket sock;
	protected NumPuzController nc;
	protected NumPuzView nv;
	protected String messageFromServer;
	protected BufferedReader in;
	protected PrintStream out;
	
	/**
	 * Default port.
	 */
	static int PORT = 3000;
	
	/**
	 * Number of port.
	 */
	static int portNumber = 0;
	
	/**
	 * Default hostname.
	 */
	static String HOSTNAME = "localhost";
	
	/**
	 * Variable for hostname.
	 */
	static String hostName = "";

	/**
	 * Default constructor.
	 * @param nc - NumPuzController object
	 * @param nv - NumPuzView object
	 */
	public NumPuzClient(NumPuzController nc, NumPuzView nv) {
		this.nc = nc;
		this.nv = nv;
	}
	
	/**
	 * established connection with server on the given port
	 * @param PORT - port number
	 */
	public void connectToServer(int PORT) {
		hostName = HOSTNAME;
		portNumber = PORT;
		
		System.out.println("Connecting with server on " + hostName + " at port " + portNumber);
		console.append("\nConnecting with server on " + hostName + " at port " + portNumber);

		try {
			sock = new Socket(hostName, portNumber);
			console.append("\nConnection successful");
			connectB.setEnabled(false);
			endB.setEnabled(true);
			sendB.setEnabled(true);
			newB.setEnabled(true);
			playB.setEnabled(true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream(sock.getOutputStream());
			
			startCommunication();
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			console.append("\nError: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * starts the communication 
	 */
	public void startCommunication() {
		
		System.out.println("starting communication");
		
	}
	
	/**
	 * closes the connection with server
	 */
	public void disconnectFromServer() {

		try {
			out.println("closing");
			String reply = in.readLine();
			if (reply.equalsIgnoreCase("ok")) {
				in.close();
				out.close();
				sock.close();
				console.append("\nDisconnected from the server");
				System.out.println("Disconnected from the server");
			}
			else {
				console.append("\n could not disconnect");
				System.out.println("Could not disconnect");
			}
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
	}
	
	/**
	 * sends client data to the server
	 */
	public void sendDataToServer() {
		String username = userText.getText();
		String separator = "#";
		String gameDataAndConfig = username  + separator + nc.getUserData();
		console.append("\nData generated! sending to the server...\nString: " + gameDataAndConfig);
		out.println(gameDataAndConfig);
	}
	
	/**
	 * requests back the client data
	 */
	public void requestDataFromServer() {
		out.println("request");
		try {
			String requestedData = in.readLine();
			console.append("\nReceived requested data from the server!\nString: " + requestedData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Overridden action performed method for ActionListener interface
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == connectB) {
			int portInputNumber = 3000;
			System.out.println(portText.getText());
			String portInputText = portText.getText();
			if (!portInputText.equals("")) {
				portInputNumber = Integer.parseInt(portInputText);
				connectToServer(portInputNumber);
			}
			else {
				console.append("\nEnter a valid port number.");
			}
		}
		
		if (e.getSource() == sendB) {
			sendDataToServer();
			sendB.setEnabled(false);
			receiveB.setEnabled(true);
		}
		
		if (e.getSource() == newB) {
			
			if (nv.getPlay().isSelected()) {
				NumPuzView.SplashScreen.startSplashScreen(nv.errIcon);
			}
			
			nc.setDesignMode();
			nv.getDesign().setSelected(true);
		}
		
		if (e.getSource() == receiveB) {
			sendB.setEnabled(true);
			receiveB.setEnabled(false);
			requestDataFromServer();
		}
		
		if (e.getSource() == endB) {
			disconnectFromServer();
//			connectB.setEnabled(true);
//			receiveB.setEnabled(false);
//			endB.setEnabled(false);
//			sendB.setEnabled(false);
//			newB.setEnabled(false);
//			playB.setEnabled(false);
			System.exit(0);
		}
		
		if (e.getSource() == playB) {
			nc.setPlayMode();
		}
		
	}
	
	/**
	 * Button class for defining specific JButtons
	 * @author suraj
	 *
	 */
	class Button extends JButton {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		Button(String text) {
			this.setFocusable(false);
			this.setBackground(Color.black);
			this.setForeground(Color.white);
			this.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
			this.setText(text);
		}
		
	}
	
	/**
	 * Overridden run method for Thread class
	 */
	@Override
	public void run() {
		
		JFrame serverWindow = new JFrame();
		JPanel serverCenter = new JPanel();
		JPanel serverTop = new JPanel();
		JPanel serverBottom = new JPanel();
		console = new JTextArea();
		
		serverTop.setBackground(new Color(235, 124, 21));
		serverTop.setLayout(null);
		serverTop.setBounds(0,0, 400, 25);
		
		JLabel userL = new JLabel("User: ");
		JLabel serverL = new JLabel("Server: ");
		JLabel portL = new JLabel("Port: ");
		
		portL.setBackground(Color.black);
		portL.setForeground(Color.WHITE);
		userL.setBackground(Color.black);
		userL.setForeground(Color.WHITE);
		serverL.setBackground(Color.black);
		serverL.setForeground(Color.WHITE);
		
		serverCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		serverCenter.setBounds(0, 25, 400, 150);
		serverCenter.setBackground(Color.black);
		
		serverBottom.setLayout(null);
		serverBottom.setBounds(0, 175, 400, 325);
		serverBottom.setBackground(Color.black);
		
		console.setBackground(Color.black);
		console.setForeground(Color.green);
		console.setBounds(0,0,400,325);
		console.setEditable(false);
		serverBottom.add(console);
		
		
		userText = new JTextField(7);
		serverText = new JTextField(13);
		portText = new JTextField(7);
		portText.setBackground(Color.WHITE);
		portText.setForeground(Color.black);
		portText.setText(PORT+"");
		userText.setBackground(Color.WHITE);
		userText.setForeground(Color.black);
		userText.setText("client1");
		serverText.setBackground(Color.WHITE);
		serverText.setForeground(Color.black);
		serverText.setText(HOSTNAME);
		
		// Buttons
		connectB = new Button("Connect");
		endB = new Button("End");
		endB.setEnabled(false);
		sendB = new Button("Send Game");
		sendB.setEnabled(false);
		receiveB = new Button("Receive Game");
		receiveB.setEnabled(false);
		newB = new Button("New Game");
		newB.setEnabled(false);
		playB = new Button("Play Game");
		playB.setEnabled(false);
		connectB.addActionListener(this);
		endB.addActionListener(this);
		sendB.addActionListener(this);
		receiveB.addActionListener(this);
		playB.addActionListener(this);
		newB.addActionListener(this);
		endB.addActionListener(this);
		
			
		serverCenter.add(userL);
		serverCenter.add(userText);
		serverCenter.add(serverL);
		serverCenter.add(serverText);
		serverCenter.add(portL);
		serverCenter.add(portText);
		serverCenter.add(connectB);
		serverCenter.add(sendB);
		serverCenter.add(receiveB);
		serverCenter.add(newB);
		serverCenter.add(playB);
		serverCenter.add(endB);
		
		
		serverWindow.setSize(400, 500);
		serverWindow.setLayout(null);
		serverWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		serverWindow.setTitle("Client");
		serverWindow.setResizable(false);
		serverWindow.add(serverTop);
		serverWindow.add(serverCenter);
		serverWindow.add(serverBottom);
		serverWindow.setVisible(true);
	}
	
}
