package org.jpngquant.pkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class jpngquant {

	// imprime sur la sortie standard les fichiers dès qu'il les trouves.
	static final Boolean DEBUG = false;
	// rends le programme verbeux et lui fait cracher le nom du fichier à chaque
	// traitement.
	static Boolean VERBOSE = false;
	static Boolean RECURSIVE = false;
	// nombre de couleurs pour pngquant, defaut = 256
	static private int couleurs = 256;
	// chemin des fichiers à traiter.
	static private String filesPath = null;
	// chemin de pngquant
	static private String pngquantPath = null;

	public static void main(String[] args) throws IOException,
			InterruptedException, FileNotFoundException {
		try{
		// menu console
		for (int argus = 0; argus < args.length; argus++) {
			if (args[argus].contains("-h") || args[argus].contains("--help")) {
				System.out
						.println("Usage : jpngquant [ARGUMENTS...] run with arguments listed below\n"
								+ "\t -c/--colors [Int.] \t\t Select number of colors according to pngquant doc\n"
								+ "\t -p/--path \t\t Path to files\n"
								+ "\t -v/--verbose \t\t Turn Verbos mode on\n"
								+ "\t -r/--recursive \t Turn Recursive mode on\n"
								+ "\t -h/--help \t\t Brings you here !!\n");
				return;
			}
			if (args[argus].contains("-c") || args[argus].contains("--colors")) {
				try {
					couleurs = Integer.parseInt(args[argus + 1]);
				} catch (Exception e) {
					couleurs = 256;
				}
				System.out.println(couleurs);
			}
			if (args[argus].contains("-p") || args[argus].contains("--path")) {
				try {
					filesPath = args[argus + 1].toString();
					System.out.println(filesPath);
				} catch (Exception e) {
					System.out.println("No path submitted, now exiting...");
					return;
				}
			}
			if (args[argus].contains("-v") || args[argus].contains("--verbose")) {
				VERBOSE = true;
			}
			if (args[argus].contains("-r")
					|| args[argus].contains("--recursive")) {
				RECURSIVE = true;
			}
		}

		pngquantPath = jpngquant.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();

		if (System.getProperties().getProperty("os.name").equals("Linux"))
			pngquantPath = jpngquant.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("jpngquant.jar", "") + "/pngquant";
		if (System.getProperties().getProperty("os.name").matches("^Windows.*"))
			pngquantPath = jpngquant.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("jpngquant.jar", "") + "\\pngquant.exe";

		// début du traitement
		if (filesPath != null) {
			File rep = new File(filesPath);
			ArrayList<String> concatRec = new ArrayList<String>();

			Runtime rt = Runtime.getRuntime();
			Process pr = null;

			// liste de tous les fichiers portant l'extension dans le string.
			if (RECURSIVE)
				getListFilesRec(rep.listFiles(), concatRec, "png");
			if (!RECURSIVE)
				getListFiles(rep.listFiles(), concatRec, "png");

			// Passage de la liste en tableau
			Object[] array = concatRec.toArray();

			// Conversion i/o
			for (int round = 0; round < array.length; round++) {
				if (VERBOSE) // Display current file.
					System.out.println("Processing : " + array[round]);
				pr = rt.exec(pngquantPath + " " + couleurs + " "
						+ array[round].toString());

				int exitVal = pr.waitFor();
				if (VERBOSE) {
					if (exitVal == 0)
						System.out.println("Successfully processed : "
								+ array[round]);
					else if (DEBUG)
						System.out.println("Exit errror : " + exitVal);
				}
			}
		} else
			System.out.println("-h for help");
		}catch (Exception e) {
			if(DEBUG)
				e.printStackTrace();
			else
				System.out.println("Error");
		}
	}

	private static void getListFiles(File[] listFiles, ArrayList<String> Array,
			String ext) {
		for (int count = 0; count < listFiles.length; count++) {
			if (listFiles[count].isFile()
					&& listFiles[count].toString().matches("^.*(" + ext + ")$")
					&& listFiles[count].canRead()) {
				Array.add(listFiles[count].getAbsolutePath());
				if (DEBUG)
					System.out.println(" File : "
							+ listFiles[count].getAbsolutePath());
			}
		}
	}

	private static void getListFilesRec(File[] listFiles,
			ArrayList<String> Array, String ext) {
		for (int count = 0; count < listFiles.length; count++) {
			if (listFiles[count].isFile()
					&& listFiles[count].toString().matches("^.*(" + ext + ")$")
					&& listFiles[count].canRead()) {
				Array.add(listFiles[count].getAbsolutePath());
				if (DEBUG)
					System.out.println(" File : "
							+ listFiles[count].getAbsolutePath());
			}
			if (listFiles[count].isDirectory() && listFiles[count].canRead())
				getListFilesRec(listFiles[count].listFiles(), Array, ext);
		}
	}
}
