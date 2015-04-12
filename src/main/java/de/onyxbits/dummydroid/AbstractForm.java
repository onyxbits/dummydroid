package de.onyxbits.dummydroid;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class AbstractForm extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FormData formData;

	protected NavigateAction forwardAction;
	protected NavigateAction backwardAction;

	public AbstractForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		this.forwardAction = forwardAction;
		this.backwardAction = backwardAction;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	/**
	 * Tell the user what this form does
	 * 
	 * @return HTML formated string
	 */
	public String getFormDescription() {
		try {
			return Messages.getString(getClass().getSimpleName() + ".description");
		}
		catch (Exception e) {
			return "No description";
		}
	}

	/**
	 * Called when the user switches to this form
	 * 
	 * @param builder
	 *          the builder to populate
	 */
	public void edit(FormData formData) {
		this.formData = formData;
		backwardAction.setEnabled(true);
		forwardAction.setEnabled(true);
	}

	/**
	 * Called when the form's settings need to be persisted in the builder.
	 * 
	 * @throws RuntimeException
	 *           if the form can't be made sense of.
	 */
	public abstract void commitForm() throws RuntimeException;
}
