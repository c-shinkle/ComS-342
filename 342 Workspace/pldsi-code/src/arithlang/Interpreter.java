package arithlang;
import java.io.IOException;
import arithlang.AST.*;
import arithlang.Value.NumVal;

/**
 * This main class implements the Read-Eval-Print-Loop of the interpreter with
 * the help of Reader, Evaluator, and Printer classes. 
 * 
 * @author hridesh
 *
 */
public class Interpreter {
	private static NumVal memory;
	public static void main(String[] args) {
		System.out.println("Type a program to evaluate and press the enter key," + 
							" e.g. (+ (* 3 100) (/ 84 (- 279 277))) \n" + 
							"Press Ctrl + C to exit.");
		memory = null;
		Reader reader = new Reader();
		Evaluator eval = new Evaluator();
		Printer printer = new Printer();
		NodeTracker nt = new NodeTracker();
		ASTCounter counter = new ASTCounter();
		
		REPL: while (true) { // Read-Eval-Print-Loop (also known as REPL)
			Program p = null;
			try {
				p = reader.read();
				if(p._e == null) continue REPL;
				Value val = eval.valueOf(p);
				printer.print(val);
				System.out.println(counter.count(p) + nt.track(p));
			} catch (IOException e) {
				System.out.println("Error reading input:" + e.getMessage());
			} catch (NullPointerException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("Prime operand should not be " + e.getMessage());
			} 
		}
	}
	
	public static NumVal getMemory() {
		return memory;
	}
	
	public static void setMemory(NumVal givenMemory) {
		memory = givenMemory;
	}
}
