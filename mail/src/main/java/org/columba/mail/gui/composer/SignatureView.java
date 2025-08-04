package org.columba.mail.gui.composer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import org.columba.core.config.Config;
import org.columba.core.gui.base.RoundedBorder;
import org.columba.core.gui.util.FontProperties;
import org.columba.core.xml.XmlElement;
import org.columba.mail.config.AccountItem;
import org.columba.mail.gui.config.account.EditSignatureAction;

public class SignatureView extends JPanel implements MouseListener,
		ItemListener, Observer {

	private ComposerController controller;

	private AccountItem item;

	private JTextComponent textPane;

	private JButton button;

	public SignatureView(ComposerController controller) {
		super();

		this.controller = controller;

		setLayout(new BorderLayout());

		setBackground(UIManager.getColor("TextArea.background"));

		setBorder(BorderFactory
				.createEmptyBorder(8,8,8,8));

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(UIManager.getColor("TextArea.background"));
		
		centerPanel.setBorder(BorderFactory.createCompoundBorder( new RoundedBorder(new Color(
						220, 220, 220)), BorderFactory
						.createEmptyBorder(8,8,8,8)));
		centerPanel.setLayout(new BorderLayout());
		
		add(centerPanel, BorderLayout.CENTER);
		
		// setBorder(BorderFactory.createCompoundBorder(BorderFactory
		// .createEmptyBorder(5, 5, 5, 5), BorderFactory
		// .createCompoundBorder(new RoundedBorder(
		// new Color(220, 220, 220)), BorderFactory
		// .createEmptyBorder(5, 5, 5, 5))));

		button = new JButton("Edit Signature...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				new EditSignatureAction(SignatureView.this.controller, item)
						.actionPerformed(null);
			}
		});

		JPanel topPanel = new JPanel();

		topPanel.setBackground(UIManager.getColor("TextArea.background"));
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(0,0,8,0));

		topPanel.add(button, BorderLayout.WEST);

		centerPanel.add(topPanel, BorderLayout.NORTH);

		textPane = new JTextArea();

		centerPanel.add(textPane, BorderLayout.CENTER);

		textPane.setEditable(false);
		textPane.setBackground(UIManager.getColor("TextArea.background"));
		// textPane.setEnabled(false);
		textPane.addMouseListener(this);

		Font font = FontProperties.getTextFont();
		setFont(font);

		XmlElement options = Config.getInstance().get("options").getElement(
				"/options");
		XmlElement gui = options.getElement("gui");
		XmlElement fonts = gui.getElement("fonts");

		if (fonts == null) {
			fonts = gui.addSubElement("fonts");
		}

		// register interest on configuration changes
		fonts.addObserver(this);

		item = (AccountItem) controller.getAccountController().getView()
				.getSelectedItem();

		if (item.getIdentity().getSignature() != null)
			readSignature(item.getIdentity().getSignature());

		// if account selection changes, reload signature file
		controller.getAccountController().getView().addItemListener(this);

		// add listener to changes
		item.getIdentity().addObserver(this);

	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {

			// remove listener from old account selection
			item.getIdentity().removeObserver(this);

			item = (AccountItem) controller.getAccountController().getView()
					.getSelectedItem();
			if (item.getIdentity().getSignature() != null) {
				readSignature(item.getIdentity().getSignature());
			} else {
				textPane.setText("");
			}

			// add listener to changes
			item.getIdentity().addObserver(this);

		}

	}

	/**
	 * Read signature from file.
	 * 
	 * @param signatureFile
	 *            signature file
	 */
	private void readSignature(File signatureFile) {
		StringBuffer strbuf = new StringBuffer();

		try {
			BufferedReader in = new BufferedReader(
					new FileReader(signatureFile));
			String str;

			while ((str = in.readLine()) != null) {
				strbuf.append(str + "\n");
			}

			in.close();

			textPane.setText(strbuf.toString());

		} catch (IOException ex) {
			textPane.setText("");
		}
	}

	public void mouseClicked(MouseEvent event) {
		if ((event.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
				&& item != null) {
			if (event.getClickCount() >= 2) {
				new EditSignatureAction(controller, item).actionPerformed(null);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println("SATD ID: 126");

	}

	public void mouseExited(MouseEvent e) {
		System.out.println("SATD ID: 177");

	}

	public void mousePressed(MouseEvent e) {
		System.out.println("SATD ID: 150");

	}

	public void mouseReleased(MouseEvent e) {
		System.out.println("SATD ID: 0");

	}

	/**
	 * 
	 * @see org.columba.mail.gui.config.general.MailOptionsDialog
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		Font font = FontProperties.getTextFont();
		setFont(font);

		readSignature(item.getIdentity().getSignature());
	}

}
