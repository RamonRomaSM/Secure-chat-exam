package servidor;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;





public class ClienteHandler implements Runnable{

	static int cont=0;
	private boolean parar;
	private Socket cliente;
	private BufferedReader isr;
	private PrintWriter osw;
	private Servidor s;
	private String nombre;
	private SeguridadServer sec;
	private String msgAct;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	
	public ClienteHandler(Socket cliente,Servidor s,String chat) {
		msgAct="";
		this.s=s;
		this.cliente=cliente;
		this.nombre="Cliente "+cont;
		cont++;
		
		try {	
			isr=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			osw=new PrintWriter(cliente.getOutputStream());
			
			oos=new ObjectOutputStream(cliente.getOutputStream());
			ois=new ObjectInputStream(cliente.getInputStream());
			sec=new SeguridadServer(ois,oos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		escribir(chat);
	}
	@Override
	public void run() {
		escribir("-----------Te has conectado, eres: "+nombre+"-----------\n");
		s.escribirChat(nombre+": Me conecte\n");
	 	parar=false;
	
		while(!parar) {
	 		s.escribirChat(nombre+": "+leer()+"\n");
	 		
	 	}
			
	}
		
	public String leer() {
		try {
			byte [] msgCifrado=(byte[])ois.readObject();		
			return sec.desEncriptar(msgCifrado);
			
			
		} catch (Exception e) {			
			parar=true;
			return "Me desconecte\n";
		}	
	}
	public void escribir(String s) {
		
		try {
			byte [] msg=sec.encriptar(s);
			oos.writeObject(msg);
			oos.flush();
		} catch (Exception e) {
			
		}
		
		
	}

}
