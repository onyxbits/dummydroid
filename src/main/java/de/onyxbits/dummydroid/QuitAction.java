package de.onyxbits.dummydroid;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public class QuitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuitAction() {
		putValue(NAME, Messages.getString("QuitAction.0")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, KeyStroke.getKeyStroke(Messages.getString("QuitAction.1")).getKeyCode()); //$NON-NLS-1$
		putValue(ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}

	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
	}

}
