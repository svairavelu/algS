package com.me.problems.leetcode;

public class LengthOfLastWord {

	public static void main(String[] args) {
		System.out.println(lengthOfLastWord("a b "));
	}

	public static int lengthOfLastWord1(String s) {
		if(s == null) {
			return 0;
		}
		
		String[] words = s.split("\\s+");
		if(words.length > 0) {
			String lastWord = words[words.length - 1];
			return lastWord.length();
		} else {
			return 0;
		}
	}
//	
	public static int lengthOfLastWord(String s) {
		if(s == null) {
			return 0;
		}
		
		char[] cs = s.toCharArray();
		
		int[][] flow = {
				{0, 1},
				{0, 1}
		};
		
		int len = 0;
		int state = 0;
		for(int i = 0; i < cs.length; i++) {
			char c = cs[i];
			
			int x = 0;
			if(c != ' ') {
				x = 1;
			}
			
			int nstate = flow[state][x];
			if(state == 0 && nstate == 1) {
				len = 0;
			} 
			
			if(nstate == 1) {
				len += 1;
			}
			state = nstate;
		}
		
		return len;
	}
}
