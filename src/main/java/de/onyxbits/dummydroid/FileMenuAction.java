package de.onyxbits.dummydroid;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class FileMenuAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileMenuAction() {
		putValue(NAME,Messages.getString("FileMenuAction.0")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY,KeyStroke.getKeyStroke(Messages.getString("FileMenuAction.1")).getKeyCode()); //$NON-NLS-1$
	}
	
	public void actionPerformed(ActionEvent arg0) {

	}

}
