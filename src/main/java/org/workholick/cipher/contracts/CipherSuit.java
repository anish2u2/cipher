package org.workholick.cipher.contracts;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public interface CipherSuit {

	public void initJks(String password, File rootDirectory) throws Exception;
	
	public Cipher getEncryptorCipher(File keyFile, String password) throws Exception ;

	public Cipher getDesCipher(File keyFile,String password) throws Exception;

	public CipherOutputStream getOutputStream(Cipher cipher, File file) throws Exception;

	public CipherInputStream getInputStream(Cipher cipher, File file) throws Exception;

}
