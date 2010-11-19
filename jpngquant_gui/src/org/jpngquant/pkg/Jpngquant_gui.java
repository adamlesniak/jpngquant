package org.jpngquant.pkg;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Jpngquant_gui extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2880954946583627080L;
	Preferences prefs = Preferences.userNodeForPackage(Jpngquant_gui.class);
	
	/**
	 * @param args
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				new Jpngquant_gui();
			}
		});
	}

	private Jpngquant_gui() {

		DropNBatch dropPanel = new DropNBatch();

		setEtatInitial();
		addListeners();

		setVisible(true);
		setTitle("jPNGQuant");
		setSize(500, 250);
		setResizable(false);
		setContentPane(dropPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/*
	 * Saves the window position. Thanks to these sources :
	 * ftp://ftp-developpez.com/java/faq/java/fichiers/TestPreferences.java
	 */
	public void setEtatInitial() {
		int x = prefs.getInt("x", 200);
		int y = prefs.getInt("y", 200);
		setLocation(x, y);
	}

	public void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				Rectangle bounds = getBounds();
				prefs.putInt("x", (int) bounds.getX());
				prefs.putInt("y", (int) bounds.getY());
			}
		});
	}
}
