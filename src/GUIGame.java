package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class GUIGame {
	
	private static JLabel gameTitleLabel;
	private static JLabel gameImageLabel;
	private static JEditorPane sysRequireText;
	private static JEditorPane descriptionText;
	private static JButton reviewGameButton;
	
	//private static JButton criticReviewButton;
	private static JLabel criticReviewLabel;
	
	public GUIGame(JPanel cardPane) {
		
		JPanel gamePane = new JPanel();
		gamePane.setBackground(Color.DARK_GRAY);
		cardPane.add(gamePane, "game");
		gamePane.setLayout(new BorderLayout(0, 0));
		
		JPanel gameHeaderPane = new JPanel();
		gameHeaderPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameHeaderPane, BorderLayout.NORTH);
		gameHeaderPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameHeaderPane.setOpaque(false);
		
		gameTitleLabel = new JLabel("GLOOBERLANTIS");
		gameTitleLabel.setForeground(Color.WHITE);
		gameTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		gameHeaderPane.add(gameTitleLabel);
		
		JPanel gameFooterPane = new JPanel();
		gameFooterPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameFooterPane, BorderLayout.SOUTH);
		gameFooterPane.setOpaque(false);
		
		sysRequireText = new JEditorPane();
		sysRequireText.setContentType("text/html");
		sysRequireText.setBackground(Color.WHITE);
		sysRequireText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		sysRequireText.setEditable(false);
		
		JScrollPane sysRequireScrollPane = new JScrollPane(sysRequireText);
		sysRequireScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sysRequireScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sysRequireScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		sysRequireScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameFooterPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameFooterPane.add(sysRequireScrollPane);
		sysRequireScrollPane.setBorder(BorderFactory.createEmptyBorder());
		sysRequireScrollPane.setPreferredSize(new Dimension( 10, 200));
		
		JPanel gameImagePane = new JPanel();
		gameImagePane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameImagePane, BorderLayout.WEST);
		gameImagePane.setLayout(new BoxLayout(gameImagePane, BoxLayout.X_AXIS));
		gameImagePane.setOpaque(false);
		
		ImageIcon image = new ImageIcon("src/gloober.png");
		gameImageLabel = new JLabel(image);
		gameImagePane.add(gameImageLabel);
		
		JPanel gameOptionsPane = new JPanel();
		gameOptionsPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gamePane.add(gameOptionsPane, BorderLayout.EAST);
		gameOptionsPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameOptionsPane.setOpaque(false);
		
		JPanel gameOptionsNorthPane = new JPanel();
		gameOptionsNorthPane.setBackground(Color.BLACK);
		gameOptionsPane.add(gameOptionsNorthPane);
		gameOptionsNorthPane.setLayout(new BorderLayout(0, 0));
		
		//unfinished buttons
		reviewGameButton = new JButton("Review Game");
		reviewGameButton.setFocusable(false);
		reviewGameButton.setForeground(Color.WHITE); // only here for temp critic review
		reviewGameButton.setBackground(Color.BLACK);
		gameOptionsNorthPane.add(reviewGameButton, BorderLayout.CENTER);
		
		JPanel gameOptionsSouthPane = new JPanel();
		gameOptionsSouthPane.setBackground(Color.BLACK);
		gameOptionsPane.add(gameOptionsSouthPane);
		gameOptionsSouthPane.setLayout(new BorderLayout(0, 0));
		
		/*criticReviewButton = new JButton("Critic Reviews");
		criticReviewButton.setFocusable(false);
		criticReviewButton.setForeground(Color.WHITE);
		criticReviewButton.setBackground(Color.BLACK);
		gameOptionsSouthPane.add(criticReviewButton, BorderLayout.CENTER);*/
		
		//temp
		criticReviewLabel = new JLabel();
		criticReviewLabel.setForeground(Color.WHITE);
		criticReviewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gameOptionsSouthPane.add(criticReviewLabel);
		
		JPanel gameDescriptionPane = new JPanel();
		gameDescriptionPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		gameDescriptionPane.setOpaque(false);
		gamePane.add(gameDescriptionPane, BorderLayout.CENTER);
		
		descriptionText = new JEditorPane();
		descriptionText.setBackground(Color.WHITE);
		descriptionText.setContentType("text/html");
		descriptionText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		descriptionText.setEditable(false);
		
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionText);
		descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		descriptionScrollPane.getVerticalScrollBar().setPreferredSize( new Dimension(0,0) );
		descriptionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		gameDescriptionPane.setLayout(new GridLayout(0, 1, 0, 0));
		gameDescriptionPane.add(descriptionScrollPane);
		descriptionScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		
		
	}
	
	public static void loadGame(Game game) {
		gameTitleLabel.setText(game.getName());

		String sysRequireHtml = "<html>" + game.getSysRequire() + "</html>";
		String descriptionHtml = "<html>" + game.getDescription() + "</html>";
		
		sysRequireText.setText(sysRequireHtml);
		sysRequireText.setCaretPosition(0);
		descriptionText.setText(descriptionHtml);
		descriptionText.setCaretPosition(0);
		
		criticReviewLabel.setText("Critic Score: " + game.getCriticScore());
		
		//ToDo Get image from database and set
		
		URL url = null;
	    try {
	        url = new URL(game.getHeaderImageUrl());
	        gameImageLabel.setIcon(new ImageIcon(ImageIO.read(url)));
	    } catch (MalformedURLException ex) {
	        System.out.println("Malformed URL");
	    } catch (IOException iox) {
	        System.out.println("Can not load file");
	    }
	}
}
