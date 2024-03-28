package Game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class NumPuzServer implements ActionListener {
	
	protected ServerThread serverThread;
	protected JFrame serverWindow = new JFrame();
	protected Button startB;
	protected Button resultB;
	protected Button endB;
	boolean paused = true;
	protected JTextField portText;
	protected JTextArea console;
	
	/**
	 * Socket variable.
	 */
	Socket sock;
	
	/**
	 * Variables for number clients.
	 */
	static int nclient = 0, nclients = 0;
	
	/**
	 * Server socket.
	 */
	static ServerSocket servsock;
	
	/**
	 * Default port.
	 */
	static int PORT = 3000;
	
	/**
	 * Number of port.
	 */
	static int portNumber = 0;

	/**
	 * Default constructor.
	 */
	public NumPuzServer() {
		
		JPanel serverCenter = new JPanel();
		JPanel serverTop = new JPanel();
		JPanel serverBottom = new JPanel();
		console = new JTextArea();
		
		serverTop.setBackground(new Color(235, 124, 21));
		serverTop.setLayout(null);
		serverTop.setBounds(0,0, 400, 25);
		
		JLabel portL = new JLabel("Port: ");
		portL.setBackground(Color.black);
		portL.setForeground(Color.WHITE);
		
		serverCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
		serverCenter.setBounds(0, 25, 400, 100);
		serverCenter.setBackground(Color.black);
		
		serverBottom.setLayout(null);
		serverBottom.setBounds(0, 125, 400, 375);
		serverBottom.setBackground(Color.black);
		
		console.setBackground(Color.black);
		console.setForeground(Color.green);
		console.setBounds(0,0,400,375);
		console.setEditable(false);
		serverBottom.add(console);
		
		portText = new JTextField(9);
		portText.setBackground(Color.WHITE);
		portText.setForeground(Color.black);
		portText.setText("3000");
		
		// Buttons
		startB = new Button("Start");
		resultB = new Button("Results");
		endB = new Button("End");
		startB.addActionListener(this);
		resultB.addActionListener(this);
		endB.addActionListener(this);
		endB.setEnabled(false);
		
		serverCenter.add(portL);
		serverCenter.add(portText);
		serverCenter.add(startB);
		serverCenter.add(resultB);
		serverCenter.add(endB);
		
		
		serverWindow.setSize(400, 500);
		serverWindow.setLayout(null);
		serverWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		serverWindow.setTitle("Server");
		serverWindow.setResizable(false);
		serverWindow.add(serverTop);
		serverWindow.add(serverCenter);
		serverWindow.add(serverBottom);
		serverWindow.setVisible(true);
	}
	
	/**
	 * main method for the NumPuzServer class
	 * @param args - arguments
	 */
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		NumPuzServer server = new NumPuzServer();
		
	}
	
	/**
	 * starts the server on the given port
	 * @param PORT - port number to start the server on
	 */
	public void startServer(int PORT) {
		portNumber = PORT;  
		console.append("\nStarting Server on port " + portNumber);
		System.out.println("Starting Server on port " + portNumber);
		try {
			servsock = new ServerSocket(portNumber);
			serverThread = new ServerThread();
			serverThread.start();
			console.append("\nServer running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!");
			System.out.println("Server running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!");
		} catch (Exception e) {
			console.append("\nError: " + e.toString());
			System.out.println("Error: " + e.toString());
		}
	}
	
	/**
	 * stops the server and closes the socket
	 */
	public void stopServer() {
		
		if (nclients == 0) {
			try {
				
				System.out.println("Closing server...");
				serverThread.interrupt();
				servsock.close();
				System.out.println("Server closed");
				console.append("\nServer Closed");
				System.exit(0);
			} catch (Exception e) {
				console.append("\n" + e.toString());
				System.out.println(e.toString());
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Clients are still connected!", "Error", JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	
	/**
	 * Overriden actionPerformed method for ActionListener interface
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == startB) {
			int portInputNumber = 3000;
			String portInputText = portText.getText();
			if (!portInputText.equals("")) {
				portInputNumber = Integer.parseInt(portInputText);
				startServer(portInputNumber);
				startB.setEnabled(false);
				endB.setEnabled(true);
			}
			else {
				console.append("\nEnter a valid port number.");
			}
		}
		
		if (e.getSource() == resultB) {
			serverThread.displayResults();
		}
		
		if (e.getSource() == endB) {
			stopServer();
		}
		
	}
	
	/**
	 * Class for defining buttons
	 * @author suraj
	 *
	 */
	class Button extends JButton {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * parametric constructor
		 * @param text
		 */
		Button(String text) {
			this.setFocusable(false);
			this.setBackground(Color.black);
			this.setForeground(Color.white);
			this.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
			this.setText(text);
		}
		
	}
	
	class ServerThread extends Thread {
		
		ArrayList<Client> clients = new ArrayList<Client>();
		
		ServerThread() {
			
		}
		
		/**
		 * overriden run method for the thread
		 */
		@Override
		public void run() {
			while(!Thread.interrupted()) {
				try {
					sock = servsock.accept();		
					nclient += 1;
					nclients += 1;
					System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
					console.append("\nConnecting Client " + nclient + sock.getInetAddress() + " at port " + sock.getPort() + ".");
					Client w = new Client(sock, nclient);
					clients.add(w);
					console.append("\nConnection to Client " + nclient + " is sucessful!");
					console.append("\nNumber of connected clients: " + nclients);
					w.start();
				} catch (IOException ioe) {
					//System.out.println(ioe);
				}
				
			}
			
		}
		
		/**
		 * displays the results of all clients
		 */
		public void displayResults() {
			String results = "";
			
			if (nclients != 0) {
				for (int i = 0; i < clients.size(); i++) {
					String clientData = clients.get(i).getClientData();
					String[] dataChunks = clientData.split(";");
					
					results += "Client " + clients.get(i).getUsername() + ":\n" + "Moves: " + dataChunks[3] + " Points: " + dataChunks[4] + " Time: " + dataChunks[5] + "s\n";
					System.out.println(dataChunks.length);
				}
			}
			else {
				results = "no clients to display!";
			}
			JOptionPane.showMessageDialog(null, results, "Results", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Inner class for Theads.
	 * @author sousap
	 *
	 */
	class Client extends Thread {
		
		/**
		 * Socket variable.
		 */
		Socket sock;
		
		boolean connected = true;
		
		/**
		 * Integers for client and positions.
		 */
		int clientid, posProtocolSep, score, time;
		
		/**
		 * String for data.
		 */
		String strcliid, dataFromClient, username;
		
		PrintStream out;
		BufferedReader in;
		
		/**
		 * Default constructor.
		 * @param s Socket
		 * @param nclient Number of client.
		 */
		public Client(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}
		
		

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}



		/**
		 * Run method.
		 */
		@Override
		public void run() {
			String messageFromClient;
			out = null;
			try {
				out = new PrintStream(sock.getOutputStream());
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				while (connected) {
					messageFromClient = in.readLine();
					handleMessageFromClient(messageFromClient);
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
				System.out.println("Client: " + clientid + " disconnected");
				nclients -= 1; 
				
				console.append("\nClient: " + clientid + " disconnected");
				console.append("\nNumber of clients: " + nclients);
			}
		}
		
		/**
		 * handles the messages received from the clients
		 * @param message
		 */
		public void handleMessageFromClient(String message) {
			if (message.equals("closing")) {
				out.println("ok");
				connected = false;
				console.append("\nDisconecting client " + clientid + " at "+ sock.getInetAddress() + "!");
				System.out.println("Disconecting client " + clientid + " at "+ sock.getInetAddress() + "!");
				nclients -= 1;
				console.append("\nClient " + clientid + " disconnected");
				console.append("\nNumber of connected clients: " + nclients);
				System.out.println("Number of connected clients: " + nclients);
			}
			else if (message.equals("request")){
				console.append("\nClient with username: " + username + " requested back data!\nSending data...");
				out.println(dataFromClient);
			}
			else {
				int septIndex = message.indexOf("#");
				username = message.substring(0, septIndex);
				
				console.append("\nData received from client with username: " + username + "\nString: " + message);
				dataFromClient = message;
			}
		}
		
		/**
		 * returns client data as a string
		 * @return - dataFromClient
		 */
		public String getClientData() {
			return dataFromClient;
		}
		
		/**
		 * stops the connection with the client
		 */
		public void stopClientConnection() {
			@SuppressWarnings("unused")
			String reply = "";
			
			try {
				out.println("closing");
				console.append("\nSending closing request to the client :" + clientid);
				System.out.println("Sending closing request to the client : " + clientid);
				reply = in.readLine();
				if (reply.equalsIgnoreCase("ok")) {
					out.close();
					in.close();
					//sock.close();
					console.append("\nConnection to the Client " + clientid + " has been closed.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
