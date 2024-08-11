package org.screenprog.service;

import org.mindrot.jbcrypt.BCrypt;

public class Encryption {
    static String encrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    static boolean decrypt(String password, String encryptedPass){
        return BCrypt.checkpw(password, encryptedPass);
    }
}
