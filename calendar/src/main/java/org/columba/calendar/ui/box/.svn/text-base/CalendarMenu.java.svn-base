package org.columba.calendar.ui.box;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.columba.calendar.base.api.ICalendarItem;
import org.columba.calendar.config.CalendarList;
import org.columba.calendar.config.api.ICalendarList;

public class CalendarMenu extends JPopupMenu {

	private ButtonGroup group = new ButtonGroup();

	public CalendarMenu(ActionListener l) {

		ICalendarList list = CalendarList.getInstance();
		Enumeration<ICalendarItem> e = list.getElements();
		while (e.hasMoreElements()) {
			ICalendarItem folder = e.nextElement();

			JRadioButtonMenuItem item = createMenuItem(folder);
			item.addActionListener(l);
			group.add(item);
			add(item);
		}

		// select first item
		JRadioButtonMenuItem item = (JRadioButtonMenuItem) getComponent(0);
		item.setSelected(true);
	}

	private JRadioButtonMenuItem createMenuItem(ICalendarItem folder) {
		JRadioButtonMenuItem item = new JRadioButtonMenuItem();
		item.setActionCommand(folder.getId());
		item.setText(folder.getName());
		item.setIcon(createIcon(folder.getColor()));
		return item;
	}

	private Icon createIcon(Color color) {
		int width = 16;
		int height = 16;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(darker(color));
		graphics.drawRect(1, 1, width - 3, height - 3);
		graphics.setColor(color);
		graphics.fillRect(2, 2, width - 4, height - 4);
		graphics.dispose();

		return new ImageIcon(image);
	}

	private final static double FACTOR = 0.90;

	private Color darker(Color c) {
		return new Color(Math.max((int) (c.getRed() * FACTOR), 0), Math.max(
				(int) (c.getGreen() * FACTOR), 0), Math.max(
				(int) (c.getBlue() * FACTOR), 0));
	}
	
}
