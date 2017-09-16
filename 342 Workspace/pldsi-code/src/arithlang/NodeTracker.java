package arithlang;

import java.util.List;

import arithlang.AST.*;

public class NodeTracker implements Visitor<String> {

	public NodeTracker() {
	}

	public String track(Program p) {
		// String result = (String) p.accept(this);
		// result = " (Note: " + result;
		// return result;
		return (String) p.accept(this);
	}

	@Override
	public String visit(NumExp e) {
		return "NumExp, ";
	}

	@Override
	public String visit(AddExp e) {
		return "AddExp, " + iterThruExps(e);
	}

	@Override
	public String visit(SubExp e) {
		return "SubExp, " + iterThruExps(e);
	}

	@Override
	public String visit(MultExp e) {
		return "MultExp, " + iterThruExps(e);
	}

	@Override
	public String visit(DivExp e) {
		return "DivExp, " + iterThruExps(e);
	}

	@Override
	public String visit(Program p) {
		String result = " (Note: Program, ";
		result += p._e.accept(this);
		result = result.substring(0, result.length() - 2);
		result += ")";
		return result;
	}

	@Override
	public String visit(PrimeExp e) {
		return "PrimeExp, " + iterThruExps(e);
	}

	private String iterThruExps(CompoundArithExp e) {
		String result = "";
		List<Exp> operands = e.all();
		for (Exp exp : operands)
			result += (String) exp.accept(this);
		return result;
	}

	@Override
	public String visit(MrecExp e) {
		return "MrecExp, ";
	}

	@Override
	public String visit(MclrExp e) {
		return "MclrExp, ";
	}

	@Override
	public String visit(MaddExp e) {
		return "MaddExp, " + iterThruExps(e);
	}

	@Override
	public String visit(MsubExp e) {
		return "MsubExp, " + iterThruExps(e);
	}
}