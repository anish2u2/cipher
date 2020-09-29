package org.workholick.cipher.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.workholick.cipher.abstracts.AbstractCipherSuit;
import org.workholick.cipher.contracts.CipherSuit;

public class CipherFactory extends AbstractCipherSuit {
	
	private static CipherSuit cipherSuit=new CipherFactory();
	
	private CipherFactory() {
		
	}
	
	public static CipherSuit getInstance() {
		return cipherSuit;
	}
	
	@Override
	public CipherOutputStream getOutputStream(Cipher cipher, File file) throws Exception {

		return new CipherOutputStream(new FileOutputStream(file), cipher);
	}

	@Override
	public CipherInputStream getInputStream(Cipher cipher, File file) throws Exception {

		return new CipherInputStream(new FileInputStream(file), cipher);
	}

}
