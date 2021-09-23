import java.util.Stack;
import java.util.Scanner;
import java.util.Stack;
import java.util.Stack;

import java.util.Stack;

class RPN {
	private Stack<String> evaluationStack = new Stack<String>();
	private int numOperands = 0;

	// Main input loop
	public void loop() {
		Scanner in = new Scanner(System.in);

		final String prompt = "> ";
		System.out.print(prompt);

		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("quit") || s.equals("q")) {
				System.out.println("Quitting");
				break;
			} else if (isDouble(s)) {
				evaluationStack.push(s);
				numOperands++;
			} else if (isMathOperand(s) && evaluationStack.size() - numOperands + 1 < numOperands) {
				try {
					evaluationStack.push(s);
					evaluate();
				} catch (Exception e) {
					evaluationStack.pop();
				}
			} else {
				System.out.println("Invalid input: " + s + "!");
			}

			System.out.println(toString());
			System.out.print(prompt);

		}
		in.close();
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isMathOperand(String str) {
		if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
			return true;
		} else {
			return false;
		}
	}

	public void evaluate() {
		if (evaluationStack.size() < 3) {
			return;
		}
		evaluationStack.push(recursiveEvaluate() + "");
	}

	private double recursiveEvaluate() {
		String token = evaluationStack.pop();
		double x;
		double y;
		try {
			x = Double.parseDouble(token);
		} catch (Exception e) {
			y = recursiveEvaluate();
			x = recursiveEvaluate();
			numOperands--;
			if (token.equals("+")) {
				x += y;
			} else if (token.equals("-")) {
				x -= y;
			} else if (token.equals("*")) {
				x *= y;
			} else if (token.equals("/")) {
				x /= y;
			}
		}
		return x;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		if (evaluationStack.size() > 0) {
			res.append(evaluationStack.firstElement());
			for (int i = 1; i < evaluationStack.size(); i++) {
				res.append(" ");
				res.append(evaluationStack.get(i));
			}
		}
		return res.toString();
	}

	// For unit test purposes
	public void addToken(String token) {
		evaluationStack.add(token);
	}

	// For unit test purposes
	public Stack<String> getEvaluationStack() {
		Stack<String> copy = new Stack<String>();
		copy.addAll(evaluationStack);
		return copy;
	}

	public static void main(String[] args) {
		RPN calc = new RPN();
		calc.loop();
	}
}
