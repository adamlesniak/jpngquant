package org.jpngquant.pkg;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DropNBatch extends JPanel {
	
	public DropNBatch() {

		JPanel dropBlue = new DropZone();
		JPanel dropRed = new DropZone();
		
		setLayout(new GridLayout());

		dropBlue.setBackground(Color.BLUE);
		dropRed.setBackground(Color.RED);
		
		((DropZone) dropRed).setTrigger(true);
		
		add(dropBlue);
		add(dropRed);
	}
}
