package fr.epsi.api.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AESSecurityService implements SecurityService {

	private static final String ENCODING = "UTF-8";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5PADDING";

	private SecretKeySpec setKey(String myKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] key = myKey.getBytes(ENCODING);
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16);
		return new SecretKeySpec(key, "AES");
	}

	private byte[] initCipher(String secret, int cipherMode, byte[] stringToTransform) {
		try {
			SecretKeySpec secretKey = setKey(secret);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			return cipher.doFinal(stringToTransform);
		} catch (Exception e) {
			return null;
		}
	}

	public String encryptPassword(String strToEncrypt, String secret) throws UnsupportedEncodingException {
		byte[] cipher = initCipher(secret, Cipher.ENCRYPT_MODE, strToEncrypt.getBytes(ENCODING));
		return Base64.getEncoder().encodeToString(cipher);

	}

	public String decryptPassword(String strToDecrypt, String secret) {
		byte[] cipher = initCipher(secret, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(strToDecrypt));
		return new String(cipher);
	}
}
