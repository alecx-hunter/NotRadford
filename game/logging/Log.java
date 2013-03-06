package game.logging;

import game.graphics.Text;

import java.awt.*;

import javax.swing.JOptionPane;

public class Log {

	public static void debug(Graphics2D g, String str, int x, int y) {
        g.drawString(str, x, y);
	}
	
	public static void console(String info) {
		System.out.println(info);
	}
	
	public static void error(String errorMessage) {
		JOptionPane.showConfirmDialog(null, errorMessage);
	}
	
}
