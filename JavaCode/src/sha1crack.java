import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class sha1crack {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		sha1crack sha = new sha1crack();
		sha.run();
	}

	private long time;
//	private String target = "67ae1a64661ac8b4494666f58c4822408dd0a3e4"; //real target
	private String target = "429b2adadcbd158271ecaed1e1575baf120188f6";
	//Starts with 20 different characters
	private char[] letters = new char[]{'Q', 'q', '@', 'W', 'w', '5', '%', '(', '8', '[', 
										'=', '0', '}', 'i', 'I', '*', '+', '~', 'N', 'n'};
	private boolean found = false;
	private int len;
	private long searchSpace, counting;
	
	private void run() throws NoSuchAlgorithmException {
		time = System.currentTimeMillis();
		//set length of the password from 3 to 8. 
		for (int i = 3; i <= 7; i++) {
			searchSpace = (long) Math.pow(20, i);
			counting = 0;
			System.out.println("i");
			System.out.println("Search space: " + searchSpace);
			
			len = i;
			searchPassword("");
		}
		
		//qW5%N@(
		System.out.println("Run time: " + (System.currentTimeMillis() - time) / 60000.0 + " minutes");
	}
	
	private void searchPassword(String pw) throws NoSuchAlgorithmException {
		if (found) return;
		if (pw.length() == len) {
			counting++;
			if (counting % 100000 == 0) {
				System.out.println(counting);
			}
			String h = hashSHA1(pw);
//			System.out.println(pw + ": " + h);
			if (target.endsWith(h)) {
				System.out.println("PASSWORD is cracked: ");
				System.out.println(pw);
				found = true;
			}
			return;
		}
		
		for (int i = 0; i < letters.length; i++)
			searchPassword(pw + letters[i]);
	}
	
	private String hashSHA1(String s) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(s.getBytes());
        
        byte byteData[] = md.digest();
        
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();
	}

}
