package cliente;

import java.io.IOException;





public class ClienteToServerListener implements Runnable{
	Cliente c;
	String chat;
	boolean interrupted=false;
	public boolean novedades;
	
	public ClienteToServerListener(Cliente c) {
		this.c=c;
		chat="";
		novedades=false;
	}
	@Override
	public void run() {
		
		do {
			chat=chat+leer();
			novedades=true;
			
		}while(!interrupted);
		
	}
	public String consultar() {
		interrupted=true;
		String s="";
		s=chat;
		novedades=false;
		interrupted =false;		
		return s;
	}
	public String leer() {		
		try {
			byte [] msgCifrado=(byte[])c.ois.readObject();
			
			return c.sec.desEncriptar(msgCifrado);
		} catch (Exception e) {			
			
			return null;
		}	
	}
	public void close() {
		interrupted=true;
	}
}
