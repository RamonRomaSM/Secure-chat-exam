package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SeguridadServer {
	SecretKey clavesesion;
	Cipher c;
	PublicKey pk;
	
	public SeguridadServer(ObjectInputStream ois,ObjectOutputStream oos) {
		
		KeyGenerator keygen;
		try {
			keygen = KeyGenerator.getInstance("AES");
			clavesesion=keygen.generateKey();
			c=Cipher.getInstance("RSA");
			
			//recibo c pub
			pk=(PublicKey) ois.readObject();
			c.init(Cipher.ENCRYPT_MODE,pk);
			
			//mando c de sesion
			byte [] msgCifrado=c.doFinal(clavesesion.getEncoded());
			oos.writeObject(msgCifrado);
			oos.flush();
			System.out.println("clave mandada "+clavesesion.toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	public String desEncriptar(byte[]obj) throws Exception {
		c=Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE,clavesesion);
		return new String(c.doFinal(obj));

	}
	
	public byte[] encriptar(String msg) throws Exception{
		c=Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE,clavesesion);
		
		return c.doFinal(msg.getBytes());
		
	}
	
	
}
