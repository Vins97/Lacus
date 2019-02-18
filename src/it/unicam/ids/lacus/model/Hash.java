package it.unicam.ids.lacus.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	public static String getMd5(String input) {
		try {
			//Static getInstance mthod is colled with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//digest() method is called to calculate message digest
			//of  an input digest() return array of byte.
			byte[] messageDigest = md.digest(input.getBytes());
			//Convert byte array into sigma representation
			BigInteger no = new BigInteger(1, messageDigest);
			//convert message digest into hex value
			StringBuilder hashtext = new StringBuilder(no.toString(16));
			while (hashtext.length()<32) {
				hashtext.insert(0, "0");
			}
			return hashtext.toString();
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}

