package com.br.miniaut.miniautorizador.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class EncriptacaoUtil {

	public static String encriptarMd5(String dado) {
		
		MessageDigest md;
		String myHash = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(dado.getBytes());
			byte[] digest = md.digest();
			myHash = DatatypeConverter
					.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return myHash;
	}
}
