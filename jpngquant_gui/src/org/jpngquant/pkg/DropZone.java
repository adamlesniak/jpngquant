package org.jpngquant.pkg;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class DropZone extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> path = new ArrayList<String>();

	public boolean trigger;

	public boolean isTrigger() {
		return trigger;
	}

	public void setTrigger(boolean trigger) {
		this.trigger = trigger;
	}

	public DropZone() {
		this.setTransferHandler(new TransferHandler() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean canImport(TransferHandler.TransferSupport info) {
				if (!info.isDrop()) {
					return false;
				}
				if (info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					return true;
				}
				if (info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					return true;
				}
				return false;
			}

			public boolean importData(TransferHandler.TransferSupport info) {
				if (!canImport(info))
					return false;
				Transferable trans = info.getTransferable();

				if (System.getProperties().getProperty("os.name")
						.matches("^Windows.*")) {
					if (canImport(info)) {
						try {
							@SuppressWarnings("unchecked")
							List<File> files = (List<File>) trans
									.getTransferData(DataFlavor.javaFileListFlavor);
							for (File f : files)
								path.add(f.getAbsolutePath());
						} catch (UnsupportedFlavorException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (System.getProperties().getProperty("os.name")
						.equals("Linux")) {
					if (canImport(info)) {
						String s = new String();
						try {
							s = (String) trans
									.getTransferData(DataFlavor.stringFlavor);
							String[] pieces = s.split("\n");
							for (int loop = 0; loop < pieces.length; loop++)
								pieces[loop] = pieces[loop].substring(7,
										pieces[loop].length());
							for (String top : pieces)
								path.add(top);
						} catch (UnsupportedFlavorException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				if (!path.isEmpty()) {
					if (!trigger)
						processFiles();
					if (trigger)
						processFiles8Bit();
				}
				return true;
			}
		});
	}

	private void processFiles() {
		for (String s : path)
			System.out.println("Bleu" + s);
	}

	private void processFiles8Bit() {
		for (String s : path)
			System.out.println("Rouge" + s);
	}
}
