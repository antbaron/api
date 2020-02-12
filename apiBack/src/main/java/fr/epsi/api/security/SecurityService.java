package fr.epsi.api.security;

import java.io.UnsupportedEncodingException;

public interface SecurityService {

	String encryptPassword(String strToEncrypt, String secret) throws UnsupportedEncodingException ;

	String decryptPassword(String strToDecrypt, String secret) ;
}
