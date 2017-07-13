package lesson170711;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Messenger {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Чат");
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		LayoutManager manager = new BorderLayout(); 
		
		JPanel panel = new JPanel(manager);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		
		panel.add(textArea, BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		
		JTextField textField = new JTextField(20);
		
		textField.addActionListener((e) -> {
			placeText(textArea, textField);
		});
		inputPanel.add(textField);
		
		JButton sendButton = new JButton("Отправить");
		inputPanel.add(sendButton);
		
		sendButton.addActionListener((e) -> {
			placeText(textArea, textField);
		});
		
		panel.add(inputPanel, BorderLayout.SOUTH);
		
		panel.setPreferredSize(new Dimension(400, 400));
		
		frame.add(panel);
		
//		frame.setSize(400, 400);
		
		frame.pack();
		
		frame.setVisible(true);
		
	}

	private static void placeText(JTextArea textArea, JTextField textField) {
		String text = textField.getText();
		textField.setText("");
		textArea.append(text + '\n');
	}

}
