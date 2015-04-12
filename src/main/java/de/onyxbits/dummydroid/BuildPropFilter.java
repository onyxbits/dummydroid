package de.onyxbits.dummydroid;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class BuildPropFilter extends FileFilter {

	public boolean accept(File arg0) {
		return arg0.isDirectory() || arg0.getName().toLowerCase().endsWith(".prop");
	}

	@Override
	public String getDescription() {
		return "Android Build Properties";
	}

}
