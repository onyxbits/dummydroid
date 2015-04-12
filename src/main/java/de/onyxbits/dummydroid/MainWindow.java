package de.onyxbits.dummydroid;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MainWindow extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		super("Dummy Droid");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar mbar = new JMenuBar();
		JMenu fileMenu = new JMenu(new FileMenuAction());
		fileMenu.add(new JMenuItem(new QuitAction()));
		mbar.add(fileMenu);
		setJMenuBar(mbar);

		FormData formData = new FormData();

		JEditorPane description = new HypertextPane("");
		description.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		description.addHyperlinkListener(new BrowseUtil());
		JPanel formContainer = new JPanel();
		formContainer.setLayout(new CardLayout());

		NavigateAction forward = new NavigateAction(description, formContainer, NavigateAction.FORWARD,
				formData);
		NavigateAction backward = new NavigateAction(description, formContainer, NavigateAction.BACK,
				formData);

		formContainer.add(new LoadForm(forward, backward), LoadForm.class.getName());
		formContainer.add(new HardwareForm(forward, backward), HardwareForm.class.getName());
		formContainer.add(new SoftwareForm(forward, backward), SoftwareForm.class.getName());
		formContainer.add(new MiscForm(forward, backward), MiscForm.class.getName());
		formContainer.add(new NativeForm(forward, backward), NativeForm.class.getName());
		formContainer.add(new SharedlibForm(forward, backward), SharedlibForm.class.getName());
		formContainer.add(new FeaturesForm(forward, backward), FeaturesForm.class.getName());
		formContainer.add(new LocalesForm(forward, backward), LocalesForm.class.getName());
		formContainer.add(new CredentialsForm(forward,backward),CredentialsForm.class.getName());
		formContainer.add(new CheckinForm(forward, backward), CheckinForm.class.getName());

		JButton next = new JButton(forward);
		JButton previous = new JButton(backward);
		JLabel content = new JLabel("");
		JPanel buttonBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonBar.add(previous);
		buttonBar.add(next);
		content.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(description), formContainer);
		splitPane.setResizeWeight(1);
		content.add(splitPane, BorderLayout.CENTER);
		content.add(buttonBar, BorderLayout.SOUTH);
		setContentPane(content);
		forward.toScreen();
	}

	public void run() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
