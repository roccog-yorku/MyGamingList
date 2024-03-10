package src;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import database.GameData;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;

public class GUIMain extends JFrame{
	
	private static JPanel basePane;
	private static JPanel mainPane;
	private static JPanel cardPane;
	public static JButton loginButton;
	
	public GUIMain() {
		
		setTitle("MyGamingList");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 800);
		
		//instantiate base pane
		basePane = new JPanel();
		basePane.setLayout(new CardLayout());
		setContentPane(basePane);
		
		//load login page
		GUILogin login = new GUILogin();
		basePane.add(login.getMainPane(), "login");
		//((CardLayout) basePane.getLayout()).show(basePane, "login");
		
		//instantiate main pane
		mainPane = new JPanel();
		mainPane.setBackground(Color.BLACK);
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new BorderLayout(0, 0));
		basePane.add(mainPane, "main");
		((CardLayout) basePane.getLayout()).show(basePane, "main");
		
		//instantiate components for main pane
		JPanel headerPane = new JPanel();
		headerPane.setBackground(Color.BLACK);
		headerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.add(headerPane, BorderLayout.NORTH);
		headerPane.setLayout(new BorderLayout(0, 0));
		
		JPanel headerOptionsPane = new JPanel();
		headerPane.add(headerOptionsPane, BorderLayout.WEST);
		headerOptionsPane.setOpaque(false);
		JButton homeButton = new JButton("Home");
		homeButton.setFocusable(false);
		headerOptionsPane.add(homeButton);
		
		homeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, "mainMenu");
			}
		});
		
		
		JButton myReviewsButton = new JButton("My Reviews");
		myReviewsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) cardPane.getLayout()).show(cardPane, "myReviewedGames");
			}
		});
		
		myReviewsButton.setFocusable(false);
		headerOptionsPane.add(myReviewsButton);
		JButton friendsButton = new JButton("Friends");
		friendsButton.setFocusable(false);
		headerOptionsPane.add(friendsButton);
		
		JPanel headerSearchPane = new JPanel();
		headerPane.add(headerSearchPane, BorderLayout.EAST);
		headerSearchPane.setOpaque(false);
		
		JTextField headerSearchBox = new JTextField();
		String searchPrompt = "Search";
		
		loginButton = new JButton("Log in");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loginButton.getText() != null && loginButton.getText().equals("Log out"))
				{
					loginButton.setText("Log in");
				}
				else
				{
					((CardLayout) basePane.getLayout()).show(basePane, "login");
				}
			}
		});
		
		headerSearchPane.add(loginButton);
		headerSearchBox.setText(searchPrompt);
		headerSearchPane.add(headerSearchBox);
		headerSearchBox.setColumns(15);
		
		headerSearchBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Search function (case in-sensitive)
				System.out.println(headerSearchBox.getText());
				
				//Filtering
				Pattern namePattern = Pattern.compile("^" + headerSearchBox.getText() + "$",
						Pattern.CASE_INSENSITIVE);
				Bson filter = regex("name", namePattern);
				FindIterable<Document> result = GameData.games.find(filter);
				System.out.println(result.first());
				
				if (result.first() != null) {
					try {
						Game gameResult = GameData.map.readValue(result.first().toJson(),Game.class);
						GUIGame.loadGame(gameResult);
						((CardLayout) cardPane.getLayout()).show(cardPane, "game");
					} catch (JsonMappingException e1) {
						System.out.println("JsonMappingException");
					} catch (JsonProcessingException e1) {
						System.out.println("JsonProcessingException");
					}
				} else {
					headerSearchBox.setText("Invalid Name");
				}
				headerSearchBox.getRootPane().requestFocus();
			}
		});

		//focus management
		headerSearchBox.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				headerSearchBox.setText("");
			}
			public void focusLost(FocusEvent e) {
				headerSearchBox.setText(searchPrompt);
			}
		});

		mainPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainPane.requestFocus();
			}
		});
		
		//order matters
		PopReleases popReleases = new PopReleases();
		MostPlayed mostPlayed = new MostPlayed();
		
		//instantiate card pane
		cardPane = new JPanel();
		mainPane.add(cardPane, BorderLayout.CENTER);
		cardPane.setLayout(new CardLayout());
		
		//instantiate all GUI elements
		GUIMainMenu mainMenu = new GUIMainMenu(cardPane, mostPlayed, popReleases);
		GUIMyReviewedGames myReviewedGames = new GUIMyReviewedGames(cardPane);
		GUIGame game = new GUIGame(cardPane);
		
	}
}
