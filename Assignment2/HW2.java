import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
// BEGIN SOLUTION
// please import only standard libraries and make sure that your code compiles and runs without unhandled exceptions 
// END SOLUTION
 
public class HW2 {    
  static void P1() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher1.bmp"));
    
    // BEGIN SOLUTION
    Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");

    byte[] key = new byte[] { 1, 2, 3, 4, 
                              5, 6, 7, 8, 
                              9, 10, 11, 12, 
                              13, 14, 15, 16 };

    byte[] iv = new byte[] { 0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0 };

    SecretKeySpec secret_key = new SecretKeySpec(key, "AES");
    IvParameterSpec cipher_iv = new IvParameterSpec(iv);

    cipher.init(Cipher.DECRYPT_MODE, secret_key, cipher_iv);

    byte[] plainBMP = cipher.doFinal(cipherBMP);
    // END SOLUTION
    
    Files.write(Paths.get("plain1.bmp"), plainBMP);
  }

  static void P2() throws Exception {
    byte[] cipher = Files.readAllBytes(Paths.get("cipher2.bin"));
    // BEGIN SOLUTION
    Cipher c = Cipher.getInstance("AES/CBC/NoPadding");

    byte[] key = new byte[] { 1, 2, 3, 4, 
                              5, 6, 7, 8, 
                              9, 10, 11, 12, 
                              13, 14, 15, 16 };

    byte[] iv = new byte[] { 0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0, 
                              0, 0, 0, 0 };

    SecretKeySpec secret_key = new SecretKeySpec(key, "AES");
    IvParameterSpec cipher_iv = new IvParameterSpec(iv);

    c.init(Cipher.DECRYPT_MODE, secret_key, cipher_iv);


    byte[] modifiedCipher = new byte[48];
    for(int i = 0; i < 16; i++) {
      modifiedCipher[0+i] = cipher[32+i];
    }
    for(int i = 0; i < 16; i++) {
      modifiedCipher[16+i] = cipher[16+i];
    }
    for(int i = 0; i < 16; i++) {
      modifiedCipher[32+i] = cipher[0+i];
    }

    byte[] plain = c.doFinal(modifiedCipher);
    // END SOLUTION
    
    Files.write(Paths.get("plain2.txt"), plain);
  }

  static void P3() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher3.bmp"));
    byte[] otherBMP = Files.readAllBytes(Paths.get("plain1.bmp"));
    
    // BEGIN SOLUTION
    byte[] modifiedBMP = cipherBMP;
    for(int i = 0; i < 50; i++) {
      modifiedBMP[i] = otherBMP[i];
    }
    // END SOLUTION
    
    Files.write(Paths.get("cipher3_modified.bmp"), modifiedBMP);
  }

  static void P4() throws Exception {
    byte[] plainA = Files.readAllBytes(Paths.get("plain4A.txt"));
    byte[] cipherA = Files.readAllBytes(Paths.get("cipher4A.bin"));
    byte[] cipherB = Files.readAllBytes(Paths.get("cipher4B.bin"));
    
    // BEGIN SOLUTION
    byte[] xor1 = new byte[43];
    byte[] xor2 = new byte[43];
    for(int i = 0; i < 43; i++) { // XOR of two ciphers which will give XOR of 2 plain text
      xor1[i] = (byte)(cipherA[i] ^ cipherB[i]);
    }
    for(int i = 0; i < 43; i++) { // XOR of both plain texts and plain text A will give plain text B
      xor2[i] = (byte)(xor1[i] ^ plainA[i]);
    }
    byte[] plainB = xor2;
    // END SOLUTION
    
    Files.write(Paths.get("plain4B.txt"), plainB);
  }

  static void P5() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher5.bmp"));
    
    // BEGIN SOLUTION
    byte[] plainBMP = new byte[cipherBMP.length];
    byte[] key = new byte[] {   0,   0,    0,   0, 
                                0,   0,    0,   0,
                                0,   0,    0,   0,
                                0,   0,    0,   0 }; 
    byte[] iv = new byte[] {    0,   0,    0,   0, 
                                0,   0,    0,   0,
                                0,   0,    0,   0,
                                0,   0,    0,   0 }; 


    byte[] plain1 = Files.readAllBytes(Paths.get("plain1.bmp"));
    byte[] compare = new byte[6];
    for(int i = 0; i < 6; i++) {
      compare[i] = plain1[i];
    }

    outerloop:
    for(int y = 0; y <= 99; y++) {
      for(int m = 1; m <= 12; m++) {
        for(int d = 1; d <= 31; d++) {
          key[0] = (byte)y;
          key[1] = (byte)m;
          key[2] = (byte)d;
          SecretKeySpec secret_key = new SecretKeySpec(key, "AES");
          IvParameterSpec cipher_iv = new IvParameterSpec(iv);
          Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
          cipher.init(Cipher.DECRYPT_MODE, secret_key, cipher_iv);
          try {
            plainBMP = cipher.doFinal(cipherBMP);
            if(plainBMP[0] == compare[0] && plainBMP[1] == compare[1] && plainBMP[2] == compare[2] && plainBMP[3] == compare[3] && plainBMP[4] == compare[4] && plainBMP[5] == compare[5] ) {
              break outerloop;
            }
          }
          catch (BadPaddingException e) {

          }
        }
      }
    }


    // END SOLUTION
    
    Files.write(Paths.get("plain5.bmp"), plainBMP);
  }

  public static void main(String [] args) {
    try {  
      P1();
      P2();
      P3();
      P4();
      P5();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
