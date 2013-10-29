import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	private String target = "67ae1a64661ac8b4494666f58c4822408dd0a3e4"; //real target
//	private String target = "429b2adadcbd158271ecaed1e1575baf120188f6"; //test target
	//Starts with 20 different characters
	private char[] letters = new char[]{'Q', 'q', '@', 'W', 'w', '5', '%', '(', '8', '[', 
										'=', '0', '}', 'i', 'I', '*', '+', '~', 'N', 'n'};
	private boolean found = false;
	private int len;
	private long searchSpace, counting;
	
	private ArrayList<String> words;
	private int numberOfThreads = 1000;
	
	private void run() throws NoSuchAlgorithmException {
		time = System.currentTimeMillis();
		
		loadingFile();
		System.out.println("Loading time: " + (System.currentTimeMillis() - time) / 1000.0 + " seconds");
		//set length of the password from 3 to 8.
		
		final int k = words.size() / numberOfThreads;
		len = 7;
		
		Thread[] threads = new Thread[numberOfThreads];
		for (int i = 0; i < numberOfThreads; i++) {
			final int t = i;
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < k; j++) {
						try {
							searchPassword(words.get(t * k + j));
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
					}
				}
			});
			threads[i].start();
		}
		
		/*
		for (int i = 6; i <= 6; i++) {
			searchSpace = (long) Math.pow(20, i);
			counting = 0;
			System.out.println("Password length: " + i);
			System.out.println("Search space: " + searchSpace);
			
			len = i;
			searchPassword("");
		}
		*/
		//qW5%N@
		//qW5%N@((%
		
		System.out.println("Run time: " + (System.currentTimeMillis() - time) / 60000.0 + " minutes");
	}
	
	private void loadingFile() {
		words = new ArrayList<String>();
		String inputFile = "file3.txt";
		File file = new File(inputFile);
		BufferedReader reader = null;
		
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    while ((text = reader.readLine()) != null) {
		    	words.add(text);
		    }
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
		    }
		}
	}
	
	private void searchPassword(String pw) throws NoSuchAlgorithmException {
		if (found) return;
		if (pw.length() == len) {
			counting++;
			if (counting % 500000 == 0) {
				System.out.println(counting);
			}
			String h = hashSHA1(pw);
//			System.out.println(pw);
			if (target.endsWith(h)) {
				System.out.println("PASSWORD is cracked: ");
				System.out.println(pw);
				found = true;
				System.out.println("Run time: " + (System.currentTimeMillis() - time) / 60000.0 + " minutes");
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
