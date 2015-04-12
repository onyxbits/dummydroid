package de.onyxbits.dummydroid;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * A key/value editor form.
 * 
 * @author patrick
 * 
 */
public abstract class PropertyForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 4, 4);

		for (String name : listProperties()) {
			gbc.gridx = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(new JLabel(getDisplayName(name)), gbc);

			gbc.gridx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			JTextField entry = new JTextField(20);
			entry.setName(name);
			add(entry, gbc);
			gbc.gridy++;
		}
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		gbc.gridy++;
		gbc.gridx = 0;
		add(new JLabel(""), gbc);
	}

	private JTextField editorFor(String name) {
		Component[] all = getComponents();
		for (Component c : all) {
			if (name.equals(c.getName())) {
				return (JTextField) c;
			}
		}
		return null;
	}

	@Override
	public void edit(FormData formData) {
		super.edit(formData);
		for (String name : listProperties()) {
			editorFor(name).setText(getInitialValue(name));
		}
	}

	/**
	 * List all the properties that are supposed to be edited with the form
	 * 
	 * @return list of property names
	 */
	protected abstract String[] listProperties();

	/**
	 * Query the default value of a property
	 * 
	 * @param pn
	 *          name of the property
	 * @return what to put into the editor
	 */
	protected abstract String getInitialValue(String p);

	/**
	 * Override the name that is shown for a property
	 * 
	 * @param pn
	 *          the name of the property
	 * @return the string to show to the user. by default, the propertyName is
	 *         returned.
	 */
	protected String getDisplayName(String pn) {
		return pn;
	}

	/**
	 * Query the value of a property
	 * 
	 * @param name
	 *          property name
	 * @return editor contents
	 */
	protected String getProperty(String name) {
		return editorFor(name).getText();
	}

	/**
	 * Validate that a set of properties is either empty or can be parsed as
	 * integers.
	 * 
	 * @param propertyName
	 *          the properties to check
	 * @throws RuntimeException
	 *           if a property contains something thats not a number.
	 */
	protected void validateIntOrEmpty(String... propertyName) throws RuntimeException {
		for (String pn : propertyName) {
			String val = getProperty(pn);
			if (val.length() > 0) {
				Integer.parseInt(val);
			}
		}
	}

	/**
	 * Check if a field does not contain a value
	 * 
	 * @param propertyName
	 *          name of the property
	 * @return true if nothing is set.
	 */
	protected boolean isEmpty(String propertyName) {
		return getProperty(propertyName).length() == 0;
	}
}
