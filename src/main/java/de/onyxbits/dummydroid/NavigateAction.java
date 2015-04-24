package de.onyxbits.dummydroid;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * An action for a forward/backward button that replaces the currently showing
 * form.
 * 
 * @author patrick
 * 
 */
class NavigateAction extends AbstractAction {

	public static final int BACK = 1;

	public static final int FORWARD = 2;

	public static final int RESET = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel formContainer;
	private JEditorPane description;
	private JLabel step;
	private int type;
	private FormData formData;

	/**
	 * 
	 * @param description
	 *          the textfield that holds the forms description
	 * @param formContainer
	 *          the container in which the editor form is sitting
	 * @param step
	 * @param type
	 *          BACK or FORWARD.
	 */
	public NavigateAction(JEditorPane description, JPanel formContainer, JLabel step, int type,
			FormData formData) {
		this.formContainer = formContainer;
		this.type = type;
		this.step = step;
		this.formData = formData;
		if (type == BACK) {
			putValue(NAME, "back");
		}
		if (type == FORWARD) {
			putValue(NAME, "next");
		}
		this.description = description;
	}

	public void actionPerformed(ActionEvent e) {
		CardLayout layout = (CardLayout) formContainer.getLayout();
		if (type == FORWARD) {
			try {
				getCurrent().commitForm();
				layout.next(formContainer);
			}
			catch (Exception exp) {
				JOptionPane.showMessageDialog(null, exp.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				exp.printStackTrace();
				return;
			}
		}
		if (type == BACK) {
			layout.previous(formContainer);
		}
		toScreen();
	}

	private AbstractForm getCurrent() {
		for (Component comp : formContainer.getComponents()) {
			if (comp.isVisible() == true) {
				return (AbstractForm) comp;
			}
		}
		return null;
	}

	private int getCurrentIndex() {
		int i = 0;
		for (Component comp : formContainer.getComponents()) {
			i++;
			if (comp.isVisible() == true) {
				return i;
			}
		}
		return -1;
	}

	public void toScreen() {
		AbstractForm form = getCurrent();
		step.setText(getCurrentIndex() + " / " + formContainer.getComponentCount());
		description.setText(form.getFormDescription());
		form.edit(formData);
	}
}
