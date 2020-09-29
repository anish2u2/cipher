package org.workholick.cipher.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.workholick.cipher.contracts.CipherSuit;
import org.workholick.cipher.factory.CipherFactory;

public class Main {
	
	public static void main1(String args[]) throws Exception{
		KeyStore keyStore=KeyStore.getInstance("PKCS12");
		char[] keyStorePassword="119905".toCharArray();
		try (FileInputStream inputStream=new FileInputStream("C:\\workspace\\eclipse\\x-projects\\DocManager.p12")){
			keyStore.load(inputStream, keyStorePassword);
			Key key=keyStore.getKey("DocManager",keyStorePassword);
			System.out.println(key.getAlgorithm());
			Cipher cipher=Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			CipherOutputStream cipherOutputStream=new CipherOutputStream(new FileOutputStream("C:\\workspace\\eclipse\\x-projects\\one.txt"), cipher);
			cipherOutputStream.write("This is my encoded text!".getBytes());
			cipherOutputStream.flush();
			cipherOutputStream.close();
			Cipher decrypto=Cipher.getInstance(key.getAlgorithm());
			decrypto.init(Cipher.DECRYPT_MODE, key);
			CipherInputStream inputStream2=new CipherInputStream(new FileInputStream("C:\\workspace\\eclipse\\x-projects\\one.txt"), decrypto);
			
			byte[] data=new byte[4096];int length=0;
			ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
			while((length=inputStream2.read(data))!=-1) {
				arrayOutputStream.write(data, 0, length);
			}
			System.out.println(new String(arrayOutputStream.toByteArray()));
			arrayOutputStream.close();
			inputStream2.close();
		}
	}
	
	public static void main(String args[]) throws Exception {
		CipherSuit cipherSuit=CipherFactory.getInstance();
		System.out.println(System.getProperty("user.dir"));
		File file=new File(System.getProperty("user.dir"));
		cipherSuit.initJks("Anish123", file);
		Cipher cipher=cipherSuit.getEncryptorCipher(file, "Anish123");
		
	}
	
}
