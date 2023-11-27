package Lap10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class MyLIFO_App {
	// This method reserves the given array
	public static <E> void reserve(E[] array) {
		Stack<E> arr = new Stack<>();
		for (int i = 0; i < array.length; i++) {
			arr.push(array[i]);
		}
		ArrayList<E> arr1 = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			arr1.add(arr.pop());
		}
		System.out.println(arr1.toString());
	}

	// This method checks the correctness of the given input
	// i.e. ()(())[]{(())} ==> true, ){[]}() ==> false
	public static boolean isCorrect(String input) {
		Stack<Character> ip = new Stack<>();
		for (int i = 0; i < input.length(); i++) {
			char t = input.charAt(i);
			switch (t) {
			case ']': {
				if (ip.isEmpty() || ip.pop() != '[')
					return false;
				else
					break;
			}
			case '}': {
				if (ip.isEmpty() || ip.pop() != '{')
					return false;
				else
					break;
			}
			case ')': {
				if (ip.isEmpty() || ip.pop() != '(')
					return false;
				else
					break;
			}
			default:
				ip.push(t);
			}
		}
		return ip.isEmpty();
	}

	// This method evaluates the value of an expression
	// i.e. 51 + (54 *(3+2)) = 321
	public static int evaluateExpression(String expression) {
		Stack<Integer> operandStack = new Stack<>();
		Stack<Character> operatorStack = new Stack<>();
		String num = "";
		for (int i = 0; i < expression.length(); i++) {
			char currentChar = expression.charAt(i);
			if (!isOpearator(currentChar)) {
				num += currentChar;
				if (i == expression.length() - 1)
					operandStack.push(Integer.parseInt(num));
			} else {
				if (!num.equals("")) {
					operandStack.push(Integer.parseInt(num));
				}
				switch (currentChar) {
				case ')': {
					while (operatorStack.peek() != '(') {
						operandStack.push(calc(operandStack.pop(), operatorStack.pop(), operandStack.pop()));
					}
					operatorStack.pop();
					break;
				}
				case '+', '-': {
					if (!operatorStack.isEmpty() && operandStack.size() > 1 && operatorStack.peek() != '(') {
						int test = calc(operandStack.pop(), operatorStack.pop(), operandStack.pop());
						operandStack.push(test);
					}
					operatorStack.push(currentChar);
					break;
				}
				case '*', '/': {
					if (!operatorStack.isEmpty())
						if (operatorStack.peek() == '*' || operatorStack.peek() == '/') {
							operandStack.push(calc(operandStack.pop(), operatorStack.pop(), operandStack.pop()));
						}
					operatorStack.push(currentChar);
					break;
				}
				default:
					operatorStack.push(currentChar);
				}
				num = "";
			}
		}
		while (!operatorStack.isEmpty()) {
			operandStack.push(calc(operandStack.pop(), operatorStack.pop(), operandStack.pop()));
		}
		return operandStack.pop();
	}

	public static boolean isOpearator(char input) {
		return input == '+' || input == '-' || input == '*' || input == '/' || input == '(' || input == ')';
	}

	public static int calc(int a, char expression, int b) {
		switch (expression) {
		case '+':
			return a + b;
		case '-':
			return b - a;
		case '*':
			return a * b;
		case '/':
			return a / b;
		}
		return 0;
	}

	public static void main(String[] args) {
		Integer[] arr = { 1, 2, 3 };
		reserve(arr);
		String test = "(()){}[]";
		System.out.println(isCorrect(test));
		String test2 = "21+12*3*(9+(2*3))-11";
		System.out.println(evaluateExpression(test2));
	}
}
