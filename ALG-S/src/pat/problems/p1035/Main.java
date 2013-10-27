package pat.problems.p1035;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			int n = Integer.valueOf(br.readLine());
			Record[] rs = new Record[n];
			int size = 0;
			for(int i = 0; i < n; i++) {
				String[] line = br.readLine().split("\\s+");
				Record r = new Record(line[0], line[1]);
				if(r.modified) {
					rs[size++] = r;
				}
			}
		
			if(size == 0) {
				System.out.println("There " + (n == 1 ? "is 1 account" : "are " + n + " accounts") +  " and no account is modified");
			} else {
				System.out.println(size);
				for(int i = 0; i < size; i++) {
					Record r = rs[i];
					System.out.println(r.name + " " + r.modifiedPwd);
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}


class Record {
	final String name;
	final String password;
	String modifiedPwd;
	boolean modified;
	public Record(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		char[] cs = this.password.toCharArray();
		for(int i = 0; i < cs.length; i++){
			char c = cs[i];
			if(c == '1') {
				modified = true;
				cs[i] = '@';
			} else if(c == 'l') {
				modified = true;
				cs[i] = 'L';
			} else if(c == '0') {
				modified = true;
				cs[i] = '%';
			} else if(c == 'O') {
				modified = true;
				cs[i] = 'o';
			}
		}
		this.modifiedPwd = new String(cs);
	}
}