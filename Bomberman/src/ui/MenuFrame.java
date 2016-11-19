package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constants.Constants;
import game.Game;

public class MenuFrame extends JFrame implements ActionListener{
	private Game game; /* Connection to game */
	
	private JPanel menuPane;
	private JPanel hostPane;
	private JPanel joinPane;
	
	private JButton hostButton;
	private JButton joinButton;
	
	private JTextField usernameHost;
	private JTextField usernameJoin;
	private JTextField ip;
	
	private Pattern ipPattern, usernamePattern;
	
	public MenuFrame(Game game) {
		super("BOMBERMAN - menu");
		this.game = game;
		
		// initialize all objects
		initWindow(800, 600);
		initComponents();
		addButtonListeners();
		prepareCheckers();
		this.setLocationRelativeTo(null);
		showMenuPane();
	}

	private void initWindow(int width, int height) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));
	}

	private void initComponents() {
		initMenuPane();
		initHostPane();
		initJoinPane();
	}
	
	private void initJoinPane() {
		GridBagConstraints gbc = new GridBagConstraints();
		JButton backButton = new JButton("Back");
		usernameJoin = new JTextField();
		usernameJoin.setPreferredSize(new Dimension(200, 20));
		ip = new JTextField();
		ip.setPreferredSize(new Dimension(200, 20));
		
		joinPane = new JPanel();
		joinPane.setSize(new Dimension(800, 600));
		joinPane.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		JButton joinGame = new JButton("Join");
		gbc.gridx = 0;
		gbc.gridy = 0;
		joinPane.add(new JLabel("Username "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		joinPane.add(usernameJoin, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		joinPane.add(new JLabel("IP Address "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		joinPane.add(ip, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		joinPane.add(joinGame, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		joinPane.add(backButton, gbc);
		
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		joinGame.setActionCommand("do_join");
		joinGame.addActionListener(this);
	}

	private void initHostPane() {
		GridBagConstraints gbc = new GridBagConstraints();
		JButton backButton = new JButton("Back");
		usernameHost = new JTextField();
		usernameHost.setPreferredSize(new Dimension(200, 20));
		
		hostPane = new JPanel();
		hostPane.setSize(new Dimension(800, 600));
		hostPane.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		JButton hostGame = new JButton("Start Hosting");
		gbc.gridx = 0;
		gbc.gridy = 0;
		hostPane.add(new JLabel("Username "), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		hostPane.add(usernameHost, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		hostPane.add(hostGame, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		hostPane.add(backButton, gbc);
		
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		hostGame.setActionCommand("do_host");
		hostGame.addActionListener(this);
	}

	private void initMenuPane() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		menuPane = new JPanel();
		menuPane.setSize(new Dimension(800, 600));
		hostButton = new JButton("Host Game");
		joinButton = new JButton("Join Game");
		menuPane.setLayout(new GridBagLayout());
		gbc.gridy = 0;
		menuPane.add(hostButton, gbc);
		gbc.gridy = 1;
		menuPane.add(joinButton, gbc);
	}

	private void addButtonListeners() {
		joinButton.addActionListener(this);
		joinButton.setActionCommand("join");
		hostButton.addActionListener(this);
		hostButton.setActionCommand("host");
	}
	
	private void prepareCheckers() {
		ipPattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		usernamePattern = Pattern.compile("^[a-zA-Z_0-9]+$");
	}
	
	private void showHostPane(){
		setContentPane(hostPane);
	}
	
	private void showJoinPane(){
		setContentPane(joinPane);
	}
	
	private void showMenuPane(){
		setContentPane(menuPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "host": showHostPane(); break;
			case "join": showJoinPane(); break;
			case "do_host": host(); break;
			case "do_join": join(); break;
			case "back": showMenuPane(); break;
		}
	}
	
	private void join() {
		if(!usernameJoin.getText().isEmpty() && !ip.getText().isEmpty()){
			// check if ip is valid
			if(!usernamePattern.matcher(usernameJoin.getText().trim()).matches()){
				JOptionPane.showMessageDialog(this, "Username not valid!");
			}
			else if(ipPattern.matcher(ip.getText()).matches() || ip.getText().trim().toLowerCase().equals("localhost")){
				game.join(Constants.TCP_PORT, usernameJoin.getText().trim(), ip.getText().trim());
			}
			else{
				 JOptionPane.showMessageDialog(this, "IP Address is invalid!");
			}
		}
	}

	private void host() {
		String username = usernameHost.getText();
		if(!username.isEmpty() && !username.trim().isEmpty()){
			game.hostGame(Constants.TCP_PORT, usernameHost.getText());
		}
	}
	
	
	
	
	

}
