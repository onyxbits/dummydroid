package de.onyxbits.dummydroid;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * One per line string lists.
 * 
 * @author patrick
 * 
 */
public abstract class ListForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea list;

	public ListForm(NavigateAction forwardAction, NavigateAction backwardAction) {
		super(forwardAction, backwardAction);
		list = new JTextArea();
		list.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		setLayout(new BorderLayout());
		add(new JScrollPane(list),BorderLayout.CENTER);
	}

	protected abstract List<String> getItems();

	protected List<String> getContent() {
		return Arrays.asList(list.getText().split("\n"));
	}

	@Override
	public void edit(FormData formData) {
		super.edit(formData);
		StringBuilder sb = new StringBuilder();
		for (String s : getItems()) {
			sb.append(s);
			sb.append("\n");
		}
		list.setText(sb.toString());
		list.setCaretPosition(0);
	}

}
