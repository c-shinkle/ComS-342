package arithlang;

import static arithlang.AST.*;
import arithlang.Value.NumVal;
import java.util.List;

/*
 * I need to overhaul this and another class. I basically have to 
 * add int i to every visit method being implemented.
 */
public class Evaluator implements Visitor<Value> {

	Printer.Formatter ts = new Printer.Formatter();

	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this);
	}

	@Override
	public Value visit(AddExp e) {
		List<Exp> operands = e.all();
		double result = 0;
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic
																// type-checking
			result += intermediate.v(); // Semantics of AddExp in terms of the
										// target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v();
		for (int i = 1; i < operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MultExp e) {
		List<Exp> operands = e.all();
		double result = 1;
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic
																// type-checking
			result *= intermediate.v(); // Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p) {
		return (Value) p.e().accept(this);
	}

	@Override
	public Value visit(SubExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v();
		for (int i = 1; i < operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(PrimeExp e) {
		List<Exp> operands = e.all();
		if (operands.size() != 1) {
			// throw some exception
			return null;
		}
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double d = lVal.v();
		if (d < 0) {
			throw new IllegalArgumentException("negative.");
		}
		if (d % 1 != 0) {
			throw new IllegalArgumentException("a fraction.");
		}

		return isPrime(lVal);
	}

	@Override
	public Value visit(MrecExp e) {
		if (Interpreter.getMemory() == null) {
			throw new NullPointerException("There is no memory to recall.");
		}
		return Interpreter.getMemory();
	}

	@Override
	public Value visit(MclrExp e) {
		Interpreter.setMemory(null);
		return new NumVal(1);
	}

	@Override
	public Value visit(MaddExp e) {
		AddExp a = new AddExp(e.all());
		NumVal n = (NumVal) a.accept(this);
		NumVal current = Interpreter.getMemory();
		NumVal result = current == null ? new NumVal(n.v()) : new NumVal(current.v() + n.v());
		Interpreter.setMemory(result);
		return Interpreter.getMemory();
	}

	@Override
	public Value visit(MsubExp e) {
		AddExp a = new AddExp(e.all());
		NumVal n = (NumVal) a.accept(this);
		NumVal current = Interpreter.getMemory();
		NumVal result = current == null ? new NumVal(n.v()) : new NumVal(current.v() - n.v());
		Interpreter.setMemory(result);
		return Interpreter.getMemory();
	}

	private Value isPrime(NumVal lVal) {
		int n = (int) lVal.v();
		if (n <= 1)
			return new NumVal(0);
		else if (n <= 3)
			return new NumVal(1);
		else if (n % 2 == 0 || n % 3 == 0)
			return new NumVal(0);
		int i = 5;
		while (i * i <= n) {
			if (n % i == 0 || n % (i + 2) == 0)
				return new NumVal(0);
			i += 6;
		}
		return new NumVal(1);
	}
}
