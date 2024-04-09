package cliente;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SeguridadCliente {
	KeyPair par;
	Cipher c;
	SecretKeySpec clavesesion;
	
	
	public SeguridadCliente(ObjectInputStream ois,ObjectOutputStream oos) {
		try {
			KeyPairGenerator keygen=KeyPairGenerator.getInstance("RSA");
			par=keygen.generateKeyPair();
			
			c=Cipher.getInstance("RSA");
			//mando c pub
			oos.writeObject(par.getPublic());
			oos.flush();
			
			//recibo c de sesion
			byte []kcifrada=(byte[])ois.readObject();
			c.init(Cipher.DECRYPT_MODE,par.getPrivate());
			byte [] k=c.doFinal(kcifrada);
			
			clavesesion=new SecretKeySpec(k,"AES");
			
			System.out.println("tengo la clave "+clavesesion.toString());
			
			
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
