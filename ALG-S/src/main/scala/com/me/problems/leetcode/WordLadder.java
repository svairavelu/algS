package com.me.problems.leetcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class WordLadder {

	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				"src/main/scala/com/me/problems/leetcode/word_ladder_dict_3.txt"))) {
			HashSet<String> dict = new HashSet<String>();
			String line = "";
			while ((line = reader.readLine()) != null) {
				dict.add(line);
			}
			long start = System.currentTimeMillis();
//			ArrayList<ArrayList<String>> ladders = findLadders("charge",
//					"comedo", dict);
//			ArrayList<ArrayList<String>> ladders = findLadders("hit", "cog",
//					dict);
			
//			ArrayList<ArrayList<String>> ladders = findLadders("nape", "mild",
//					dict);
			ArrayList<ArrayList<String>> ladders = findLadders("red", "tax",
					dict);
			long period = System.currentTimeMillis() - start;
			System.out.println("it taks [" + period
					+ "] milliseconds to process");
			for (ArrayList<String> ladder : ladders) {
				String sep = "";
				for (String word : ladder) {
					System.out.print(sep + word);
					if (sep == "") {
						sep = "->";
					}
				}
				System.out.println();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public int ladderLength(String start, String end, HashSet<String> dict) {
		Queue<String> queue = new LinkedList<String>();
		Map<String, String> paths = new HashMap<String, String>();
		Map<String, Integer> dists = new HashMap<String, Integer>();
		dists.put(start, 0);
		Set<String> checked = new HashSet<String>();

		queue.offer(start);
		while (!queue.isEmpty()) {
			String v = queue.poll();
			if (v.equals(end)) {
				break;
			}
			checked.add(v);
			// find all next tranformable strings;
			char[] cs = v.toCharArray();
			for (int i = 0; i < cs.length; i++) {
				char x = cs[i];
				for (char c = 'a'; c <= 'z'; c++) {
					if (c == x) {
						continue;
					}

					cs[i] = c;
					String s = String.valueOf(cs);

					if (checked.contains(s) || !dict.contains(s)) {
						continue;
					}

					int distv = dists.get(v);
					if (!dists.containsKey(s) || distv + 1 < dists.get(s)) {
						queue.offer(s);
						dists.put(s, distv + 1);
						paths.put(s, v);
					} 

				}
				cs[i] = x;
			}
		}

		if (paths.containsKey(end)) {
			int len = 1;
			String s = end;
			while (paths.containsKey(s)) {
				len += 1;
				s = paths.get(s);
			}
			return len;
		} else {
			return 0;
		}
	}

	public static ArrayList<ArrayList<String>> findLadders(String start,
			String end, HashSet<String> dict) {
		dict.add(end);
		Queue<String> queue = new LinkedList<String>();
		Map<String, List<String>> parents = new HashMap<String, List<String>>();
		Map<String, Integer> dists = new HashMap<String, Integer>();
		dists.put(start, 0);
		Set<String> checked = new HashSet<String>();
		queue.offer(start);

		ArrayList<ArrayList<String>> all = new ArrayList<ArrayList<String>>();

		while (!queue.isEmpty()) {
			String v = queue.poll();
			checked.add(v);
			if (v.equals(end)) {
				break;
			}
			// find all next tranformable strings;
			char[] cs = v.toCharArray();
			for (int i = 0; i < cs.length; i++) {
				char x = cs[i];
				for (char c = 'a'; c <= 'z'; c++) {
					if (c == x) {
						continue;
					}

					cs[i] = c;
					String s = String.valueOf(cs);

					if (checked.contains(s) || !dict.contains(s)) {
						continue;
					}

					int distv = dists.get(v);
					if (!dists.containsKey(s) || distv + 1 < dists.get(s)) {
						queue.offer(s);
						dists.put(s, distv + 1);
						
						List<String> path = new ArrayList<String>();
						path.add(v);
						parents.put(s, path);
					} else if(dists.containsKey(s) && distv + 1 == dists.get(s)) {
						List<String> path = parents.get(s);
						path.add(v);
					}
				}
				cs[i] = x;
			}
		}
		
		if(checked.contains(end)) {
			findPath(new Stack<String>(), end, parents, all);
		}
		
		return all;
	}

	private static void findPath(Stack<String> path, String v, Map<String, List<String>> parents, ArrayList<ArrayList<String>> all) {
		if(parents.containsKey(v)) {
			path.push(v);
			List<String> ps = parents.get(v);
			for(String p : ps) {
				findPath(path, p, parents, all);
			}
			path.pop();
		} else {
			//v has to be the start
			path.push(v);
			
			ArrayList<String> vs = new ArrayList<String>();
			
			for(String s : path) {
				vs.add(s);
			}
			
			Collections.reverse(vs);
			all.add(vs);
			
			path.pop();
		}
	}
}
