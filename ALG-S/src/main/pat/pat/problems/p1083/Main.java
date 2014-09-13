package pat.problems.p1083;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String line = br.readLine();
			int n = Integer.parseInt(line);
			Student[] students = new Student[n];
			for (int i = 0; i < n; i++) {
				line = br.readLine();
				String[] splitted = line.split(" ");
				students[i] = new Student(splitted[0], splitted[1],
						Integer.parseInt(splitted[2]));
			}

			line = br.readLine();
			String[] xy = line.split(" ");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);

			Arrays.sort(students);

			if (x > y) {
				int t = x;
				x = y;
				y = t;
			}
			boolean has = false;
			for (Student it : students) {
				if (it.grade >= x && it.grade <= y) {
					has = true;
					System.out.println(it);
				}
			}

			if (!has) {
				System.out.println("NONE");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class Student implements Comparable<Student> {
		final String name;
		final String id;
		final int grade;

		public Student(String name, String id, int grade) {
			super();
			this.name = name;
			this.id = id;
			this.grade = grade;
		}

		public int compareTo(Student that) {
			if (this.grade < that.grade) {
				return 1;
			} else if (this.grade > that.grade) {
				return -1;
			}
			return 0;
		}

		public String toString() {
			return name + " " + id;
		}
	}
}
