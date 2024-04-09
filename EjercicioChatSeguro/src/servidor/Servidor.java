package servidor;

import java.io.IOException;   
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

public class Servidor {

	private String chat;
	private ArrayList<ClienteHandler>clientes;
	public void initialize() {
		try {
			ServerSocket soc=new ServerSocket(8882);
			System.out.println("[SERVER] Server iniciado");
			
		 	chat="";
			clientes=new ArrayList<ClienteHandler>();
			
			while(true) {
				Socket cliente=soc.accept();
				
				ClienteHandler handler=new ClienteHandler(cliente,this,chat);
				Thread t=new Thread(handler);
				t.start();
				
				clientes.add(handler);
			}
			
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public synchronized void escribirChat(String s) {
		
		chat=chat+s;
		System.out.println("CHAT ACTUALIZADO:\n"+chat);
		annadirMensaje(s);
		
	}
	private void annadirMensaje(String s){
		Iterator<ClienteHandler>it= clientes.iterator();
		int i=0;
		while(it.hasNext()) {
			ClienteHandler act=it.next();
			i++;
			try {
				act.escribir(s);
			} catch (Exception e) {
				
				clientes.remove(i);
			}
			
		}

	}
	public static void main(String[] args) {
	 	Servidor s=new Servidor();
		s.initialize();
		
	}

}
