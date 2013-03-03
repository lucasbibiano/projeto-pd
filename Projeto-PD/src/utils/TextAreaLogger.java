package utils;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class TextAreaLogger extends JFrame {
	
	private static TextAreaLogger instance;
	
	private JTextArea textArea;
	
	public TextAreaLogger() {
		super("Log");
				
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		setSize(new Dimension(700, 300));
		setLayout(new BorderLayout());
		
		setPreferredSize(getSize());
		
		JScrollPane scroll = new JScrollPane(textArea);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		add(scroll, BorderLayout.CENTER);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	public void log(String str) {
		textArea.append(str + "\n");
	}
	
	public static TextAreaLogger getInstance() {
		if (instance == null)
			instance = new TextAreaLogger();
		
		return instance;
	}
}
