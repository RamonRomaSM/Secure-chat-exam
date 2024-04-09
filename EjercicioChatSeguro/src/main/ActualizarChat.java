package main;



import javax.swing.JTextArea;



import cliente.Cliente;
import cliente.ClienteToServerListener;

public class ActualizarChat implements Runnable{
	private JTextArea a;
	private Cliente c;
	private String s;
	
	public ActualizarChat(JTextArea a,Cliente c) {
		this.a=a;
		this.c=c;
		s="";
		
	}
	@Override
	public void run() {
		while(true) {
			
				try {
					String s=a.getText()+c.getNovedades();
					if(c.getNovedades()) {
						a.setText(c.consultar());
					}
				} catch (Exception e) {
					a.setText("Conexion no establecida\n");
				}
			
			
		}
		
	}

}
