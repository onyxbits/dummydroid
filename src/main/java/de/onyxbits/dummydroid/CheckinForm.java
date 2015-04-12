package de.onyxbits.dummydroid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JEditorPane;
import javax.swing.JProgressBar;

import com.akdeniz.googleplaycrawler.GooglePlay.AndroidCheckinRequest;

public class CheckinForm extends AbstractForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JEditorPane result;
	private JProgressBar progress;
	private String cached;

	public CheckinForm(NavigateAction forward, NavigateAction backward) {
		super(forward, backward);

		result = new HypertextPane("");
		result.setPreferredSize(new Dimension(300, 200));
		result.setOpaque(false);
		progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);

		setLayout(new BorderLayout());
		add(result, BorderLayout.CENTER);
		add(progress, BorderLayout.NORTH);
	}

	@Override
	public void edit(FormData formData) {
		super.edit(formData);
		backwardAction.setEnabled(true);
		forwardAction.setEnabled(false);
		AndroidCheckinRequest acr = formData.assemble();

		// Don't needlessly request new GSF IDs if nothing has changed.
		String tmp = acr.toString();
		if (!tmp.equals(cached)) {
			progress.setVisible(true);
			result.setText("");
			new CheckinWorker(this, formData).execute();
			cached = tmp;
		}
	}

	@Override
	public void commitForm() {

	}

	public void finished(Exception e) {
		result.setText(e.getMessage());
		progress.setVisible(false);
		cached=null;
	}

	public void finished(String gsf) {
		result.setText("<h1 align=\"center\">"+gsf+"</h1>");
		progress.setVisible(false);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(new StringSelection(gsf), null);
	}

}
