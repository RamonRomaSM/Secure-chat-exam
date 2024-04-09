package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import cliente.Cliente;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ClienteAppWindow {
	private Cliente c;
	private JFrame frame;
	private JTextField textField;
	private Thread clienteThread;
	private Thread actualizarChatThread;
	private ActualizarChat act;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteAppWindow window = new ClienteAppWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClienteAppWindow() {
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 583, 577);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(382, 447, 129, 61);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(55, 447, 289, 61);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 35, 450, 359);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		c=new Cliente();
		clienteThread=new Thread(c);
		act=new ActualizarChat(textArea, c);
		actualizarChatThread=new Thread(act);		
		clienteThread.start();
		actualizarChatThread.start();
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("")) {
					c.escribir(textField.getText()+"\r");
					textField.setText("");
				}
				
			}
		});
		
		
	}
}
