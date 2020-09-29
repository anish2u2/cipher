package org.workholick.cipher.abstracts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.workholick.cipher.contracts.CipherSuit;

public abstract class AbstractCipherSuit implements CipherSuit {
	
	private String keyFile="cipher.key.spi";
	
	private Cipher encryptor;

	private Cipher decipher;

	private Key key;
	
	@Override
	public void initJks(String password, File rootDirectory) throws Exception{
		/*String command="keytool -genseckey  -alias DocManager  -keypass "+password+"  -keyalg AES  -keysize 256  -keystore "+rootDirectory.getAbsolutePath()+File.separator+"DocManager.p12  -storepass "+password+" -storetype PKCS12";
		
		Runtime.getRuntime().exec(command).waitFor();*/
		File file=new File(rootDirectory,keyFile);
		if(!file.exists()) {
		System.out.println("File does not exists..");
		KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		key=keyGenerator.generateKey();
		
		try (ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(new File(rootDirectory,this.keyFile)))){
			outputStream.writeObject(key);
			outputStream.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}}else {
			System.out.println("File exists..");
			try (ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(new File(rootDirectory,this.keyFile)))){
				key=(Key)inputStream.readObject();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Cipher getEncryptorCipher(File keyFile, String password) throws Exception {
		if (encryptor != null)
			return encryptor;
		initKeyStore();
		if (key == null)
			key = getKey(keyFile, password);
		encryptor = Cipher.getInstance(key.getAlgorithm());
		encryptor.init(Cipher.ENCRYPT_MODE, key);
		return encryptor;
	}

	@Override
	public Cipher getDesCipher(File keyFile, String password) throws Exception {
		if (decipher != null)
			return decipher;
		initKeyStore();
		if (key == null)
			key = getKey(keyFile, password);
		decipher = Cipher.getInstance(key.getAlgorithm());
		decipher.init(Cipher.DECRYPT_MODE, key);
		return decipher;
	}

	private Key getKey(File keyFile, String password) throws Exception {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(keyFile,"DocManager.p12"));

			KeyStore keyStore = initKeyStore();
			keyStore.load(inputStream, password.toCharArray());
			return keyStore.getKey("DocManager", password.toCharArray());

		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	private KeyStore initKeyStore() throws Exception {

		return KeyStore.getInstance("PKCS12");
		
	}

}
