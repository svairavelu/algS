package com.me.problems.leetcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IntervalJ {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"src/main/scala/com/me/problems/leetcode/intervals.txt"))) {
			ArrayList<Interval> list = fromStr(br.readLine());
			IntervalJ app = new IntervalJ();
			list = app.merge(list);
			for (Interval it : list) {
				System.out.println(it);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static final ArrayList<Interval> fromStr(String str) {
		ArrayList<Interval> list = new ArrayList<Interval>();
		str = str.substring(1, str.length() - 1);
		String[] ranges = str.split("\\],\\[");

		for (int i = 0; i < ranges.length; i++) {
			String range = ranges[i];
			if (i == 0) {
				range = range.substring(1);
			} else if (i == ranges.length - 1) {
				range = range.substring(0, range.length() - 1);
			}

			String[] ss = range.split(",");
			Interval it = new Interval(Integer.valueOf(ss[0]),
					Integer.valueOf(ss[1]));
			list.add(it);
		}

		return list;
	}

	static class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
	}

	public static ArrayList<Interval> insert(ArrayList<Interval> intervals,
			Interval newInterval) {
		ArrayList<Interval> list = new ArrayList<Interval>();
		boolean newIntervalProcessed = false;
		for (Interval interval : intervals) {
			if (newIntervalProcessed) {
				list.add(interval);
				continue;
			}

			if (interval.start > newInterval.end) {
				if (!newIntervalProcessed) {
					list.add(newInterval);
					newIntervalProcessed = true;
				}
				list.add(interval);
			} else if (newInterval.start <= interval.start) {
				newInterval = new Interval(newInterval.start, Math.max(
						newInterval.end, interval.end));
			} else if (newInterval.start <= interval.end) {
				newInterval = new Interval(interval.start, Math.max(
						newInterval.end, interval.end));
			} else {
				list.add(interval);
			}
		}

		if (!newIntervalProcessed) {
			list.add(newInterval);
		}
		return list;
	}

	public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
		if (intervals == null || intervals.size() == 0) {
			return intervals;
		}
		Collections.sort(intervals, new Comparator<Interval>() {

			@Override
			public int compare(Interval x, Interval y) {
				if (x.start < y.start) {
					return -1;
				} else if (x.start == y.start) {
					return x.end < y.end ? -1 : (x.end == y.end ? 0 : 1);
				} else {
					return 1;
				}
			}
		});

		ArrayList<Interval> merged = new ArrayList<Interval>();
		Interval lastInterval = null;
		for (Interval interval : intervals) {
			if (lastInterval == null) {
				lastInterval = interval;
				continue;
			}

			if (interval.start > lastInterval.end) {
				merged.add(lastInterval);
				lastInterval = interval;
			} else {
				lastInterval.end = Math.max(lastInterval.end, interval.end);
			}
		}
		merged.add(lastInterval);
		return merged;
	}

}
