import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
// BEGIN SOLUTION
// please use only standard libraries
// END SOLUTION

public class HW1 {
  @SuppressWarnings("serial")
  
  /* Problem 1 */
    
  static void Problem1() {
    String cipherText = "ROYQWH KQXXJYQ: N LQGNQAQ HDJH FO. VW NX J KQKLQO VZ J XQMOQH MONKQ VOYJWNSJHNVW MJGGQF U.D.J.W.H.V.K., IDVXQ YVJG NX HVHJG IVOGF FVKNWJHNVW. HDQNO UGJW NX HV JMBRNOQ J XRUQOIQJUVW JWF HV DVGF HDQ IVOGF OJWXVK. N ZQJO HDJH IQ FV WVH DJAQ KRMD HNKQ LQZVOQ HDQT XRMMQQF.\nN DJAQ OQMQWHGT NWHQOMQUHQF JW QWMOTUHQF KQXXJYQ (JHHJMDKQWH MNUDQO2.HCH) HDJH IJX XQWH LT FO. VW HV VWQ VZ DNX MVWXUNOJHVOX, HDQ NWZJKVRX KO. LGVIZNQGF. N KJWJYQF HV FNXMVAQO HDJH HDQ KQXXJYQ IJX QWMOTUHQF RXNWY HDQ PJMEJG MNUDQO (XQQ XVROMQ MVFQ), LRH N IJX WVH JLGQ FNXMVAQO HDQ XQMOQH EQT, JWF HDQ MNUDQO XQQKX HV LQ RWLOQJEJLGQ. N JK JZOJNF HDJH FQMOTUHNWY HDNX KQXXJYQ NX HDQ VWGT IJT HV XHVU FO. VW'X VOYJWNSJHNVW.\nUGQJXQ XQWF OQNWZVOMQKQWHX NKKQFNJHQGT! N HONQF HV JMH MJRHNVRXGT, LRH N DJAQ J ZQQGNWY HDJH FO. VW'X DQWMDKQW JOQ VWHV KQ. N FVW'H EWVI DVI GVWY N DJAQ LQZVOQ HDQT FNXMVAQO KT OQJG NFQWHNHT JWF KT XQMOQH DNFNWY UGJ";
    // BEGIN SOLUTION
    HashMap<Character, Double> frequencies = new HashMap<Character, Double>();
    
    int length = cipherText.length();
    for (char c = 'A'; c <= 'Z'; c++) {
      double count = 0;
      for (int i = 0; i < length; i++) {
        if (cipherText.charAt(i) == c) {
          count++;
        }
      }
      frequencies.put(c, count);
      System.out.printf("%c: %f\n", c, frequencies.get(c));
    }
    HashMap<Character, Character> key = new HashMap<Character, Character>() {
      {
        put('A', 'V');
        put('B', 'Q');
        put('C', 'X');
        put('D', 'H');
        put('E', 'K');
        put('F', 'D');
        put('G', 'L');
        put('H', 'T');
        put('I', 'W');
        put('J', 'A');
        put('K', 'M');
        put('L', 'B');
        put('M', 'C');
        put('N', 'I');
        put('O', 'R');
        put('P', 'J');
        put('Q', 'E');
        put('R', 'U');
        put('S', 'Z');
        put('T', 'Y');
        put('U', 'P');
        put('V', 'O');
        put('W', 'N');
        put('X', 'S');
        put('Y', 'G');
        put('Z', 'F');
      }
    };
    for (char c : cipherText.toCharArray()) {
      if (key.containsKey(c))
        System.out.print(key.get(c));
      else
        System.out.print(c);
    }
    System.out.println();
    // END SOLUTION
  }
  
  
  /* Problem 2 */
  
  public static byte[] JACKAL_Decrypt(byte firstKeyByte, byte secondKeyByte, byte[] cipherText) {byte x=(byte)(firstKeyByte+31);byte y=(byte)(secondKeyByte*=3);byte[]p=new byte[cipherText.length];for(int z=0;z<p.length;z++){x+=29;y*=19;p[z]=(byte)(cipherText[z]^x^y);}return(p);}

  public static boolean isEnglishText(byte[] bytes) {
    String punctuations = ".,'-:{}";
    for (char chr : new String(bytes).toCharArray())
      if (!(Character.isLetterOrDigit(chr) || Character.isWhitespace(chr) || punctuations.contains(String.valueOf(chr))))
        return false;
    return true;
  } 

  static void Problem2() throws IOException {
    byte[] cipherText = Files.readAllBytes(Paths.get("cipher2.txt"));
    // BEGIN SOLUTION
    byte[] plainText = JACKAL_Decrypt((byte)0, (byte)0, cipherText);
    outerloop:
    for(int i = 0; i < 10000; i++) {
      for(int j = 0; j < 10000; j++) {
        plainText = JACKAL_Decrypt((byte)i, (byte)j, cipherText);
        if(isEnglishText(plainText)) {
          break outerloop;
        }
      }
    }
    // END SOLUTION
    System.out.println(new String(plainText));
  }
  
  
  /* Problem 3 */
    
  static void Problem3() throws IOException {
    byte[] cipherText = Files.readAllBytes(Paths.get("cipher3.txt"));
    // BEGIN SOLUTION
    byte[] key = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 }; 
    byte[] plainText = new byte[cipherText.length];
    for(int i = 0; i < cipherText.length; i++) {
      plainText[i] = (byte)(cipherText[i] ^ key[i % 11]);
    }
    // END SOLUTION
    System.out.println(new String(plainText));
  }
  
  public static void main(String [] args) throws IOException {
    Problem1();
    Problem2();
    Problem3();
  }  
}
