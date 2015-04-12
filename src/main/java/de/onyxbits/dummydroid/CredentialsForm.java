package de.onyxbits.dummydroid;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class CredentialsForm extends AbstractForm implements CaretListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField password;

	public CredentialsForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
		username = new JTextField(20);
		password = new JPasswordField(20);
		JPanel content = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 5, 5);
		content.setLayout(new GridBagLayout());
		content.add(new JLabel(Messages.getString("CredentialsForm.username")), gbc);
		gbc.gridx++;
		content.add(username, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		content.add(new JLabel(Messages.getString("CredentialsForm.password")), gbc);
		gbc.gridx++;
		gbc.weightx = 1;
		gbc.weighty = 1;
		content.add(password, gbc);
		content
				.setBorder(BorderFactory.createTitledBorder(Messages.getString("CredentialsForm.title")));
		// setLayout(new BorderLayout());
		add(content);
	}

	@Override
	public void edit(FormData formData) {
		super.edit(formData);
		username.setText(formData.getUsername());
		password.setText(formData.getPassword());
		username.removeCaretListener(this);
		username.addCaretListener(this);
		password.removeCaretListener(this);
		password.addCaretListener(this);
		stopIfEmpty();
	}

	@Override
	public void commitForm() throws RuntimeException {
		formData.setPassword(new String(password.getPassword()));
		formData.setUsername(username.getText());
	}

	private void stopIfEmpty() {
		forwardAction
				.setEnabled(username.getText().length() > 0 && password.getPassword().length > 0);
	}

	public void caretUpdate(CaretEvent arg0) {
		stopIfEmpty();
	}

}
