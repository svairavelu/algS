package com.me.problems.leetcode;

public class ValidNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static enum InputType {
		INVALID, // 0
		SPACE, // 1
		SIGN, // 2
		DIGIT, // 3
		DOT, // 4
		EXPONENT, // 5
		NUM_INPUTS // 6
	};

	static int[][] transitionTable = { { -1, 0, 3, 1, 2, -1 }, // next state for
																// state 0
			{ -1, 8, -1, 1, 4, 5 }, // next states for state 1
			{ -1, -1, -1, 4, -1, -1 }, // next states for state 2
			{ -1, -1, -1, 1, 2, -1 }, // next states for state 3
			{ -1, 8, -1, 4, -1, 5 }, // next states for state 4
			{ -1, -1, 6, 7, -1, -1 }, // next states for state 5
			{ -1, -1, -1, 7, -1, -1 }, // next states for state 6
			{ -1, 8, -1, 7, -1, -1 }, // next states for state 7
			{ -1, 8, -1, -1, -1, -1 }, // next states for state 8
	};

	public static boolean isNumber(String s) {
		char[] xs = s.toCharArray();
		int state = 0;
		for (char x : xs) {
			InputType tpe = InputType.INVALID;
			switch (x) {
			case ' ':
				tpe = InputType.SPACE;
				break;
			case '.':
				tpe = InputType.DOT;
				break;
			case 'e':
			case 'E':
				tpe = InputType.EXPONENT;
				break;
			case '+':
			case '-':
				tpe = InputType.SIGN;
				tpe = InputType.SIGN;
				break;
			default:
				tpe = InputType.DIGIT;
				break;
			}

			if (tpe == InputType.DIGIT && !isDigit(x)) {
				tpe = InputType.INVALID;
			}

			state = transitionTable[state][tpe.ordinal()];

			if (state == -1) {
				break;
			}
		}

		return state == 1 || state == 4 || state == 7 || state == 8;
	}

	private static boolean isDigit(char x) {
		return x >= '0' && x <= '9';
	}
}
