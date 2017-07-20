package lesson170713;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Messenger {

	static Communicator chat;
	private static JTextArea textArea;
	private static JScrollPane sp;
	private static List userList;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Чат");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		LayoutManager manager = new BorderLayout();

		JPanel panel = new JPanel(manager);
		panel.setPreferredSize(new Dimension(400, 400));

		textArea = new JTextArea();
		textArea.setEditable(false);

		sp = new JScrollPane(textArea);

		panel.add(sp, BorderLayout.CENTER);

		JPanel inputPanel = new JPanel();

		JTextField textField = new JTextField(20);

		textField.addActionListener(e -> sendText(textField));
		inputPanel.add(textField);

		JButton sendButton = new JButton("Отправить");
		inputPanel.add(sendButton);

		sendButton.addActionListener(e -> sendText(textField));

		panel.add(inputPanel, BorderLayout.SOUTH);

		userList = new List(10, false);

		userList.addActionListener(e -> {
			textField.setText(e.getActionCommand() + " ");
		});

		panel.add(userList, BorderLayout.WEST);

		frame.add(panel);

		// frame.setSize(400, 400);

		frame.pack();

		frame.setVisible(true);

		chat = new Communicator();

		chat.init(Messenger::processServerMessage);

	}

	private static void sendText(JTextField textField) {
		String text = textField.getText();
		textField.setText("");
		chat.sendTextToServer(text);
	}

	private static void processServerMessage(String text) {
		if (text.startsWith("/name")) {
			String[] words = text.split(" ");
			String userName = words[1];
			textArea.append("Добро пожаловать в чат, " + userName + "\n");
			return;
		}
		if (text.startsWith("/list")) {
			String[] names = text.split(" ");
			for (int i = 1; i < names.length; i++) {
				userList.add(names[i]);
			}
			return;
		}
		if (text.startsWith("/add")) {
			String[] words = text.split(" ");
			String userName = words[1];
			userList.add(userName);
			return;
		}
		if (text.startsWith("/remove")) {
			String[] words = text.split(" ");
			String userName = words[1];
			userList.remove(userName);
			return;
		}
		textArea.append(text + '\n');
		// textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
