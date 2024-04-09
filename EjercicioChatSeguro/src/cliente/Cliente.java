package cliente;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;





public class Cliente implements Runnable{
	BufferedReader isr;
	PrintWriter osw;
	Socket soc;
	ClienteToServerListener listener;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	SeguridadCliente sec;
	boolean salir=false;
	
	public Cliente() {
		super();
	}

	
	public String consultar() {
		return listener.consultar();
	}
	
	public void escribir(String s){
		
		try {
			byte [] msg=sec.encriptar(s);
			oos.writeObject(msg);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void run() {
		try {
				
			soc=new Socket("localhost", 8882);
			isr=new BufferedReader(new InputStreamReader(soc.getInputStream()));
			osw=new PrintWriter(soc.getOutputStream());
			
			oos=new ObjectOutputStream(soc.getOutputStream());
			ois=new ObjectInputStream(soc.getInputStream());
			
			sec =new SeguridadCliente(ois, oos);
			
			listener=new ClienteToServerListener(this);
			
			
			Thread t1=new Thread(listener);
			t1.start();
			
			t1.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		Cliente c1=new Cliente();
		
		c1.run();		
	}
	public boolean getNovedades() {
		return listener.novedades;
	}
}
