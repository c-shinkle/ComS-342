package arithlang;

import java.util.List;

import arithlang.AST.*;

public class ASTCounter implements Visitor<Integer> {

	public Integer count(Program p) {
		return (Integer) p.accept(this);
	}

	@Override
	public Integer visit(NumExp e) {
		return 1;
	}

	@Override
	public Integer visit(AddExp e) {
		return 1 + iterThruExps(e);
	}

	@Override
	public Integer visit(SubExp e) {
		return 1 + iterThruExps(e);
	}

	@Override
	public Integer visit(MultExp e) {
		return 1 + iterThruExps(e);
	}

	@Override
	public Integer visit(DivExp e) {
		return 1 + iterThruExps(e);
	}

	@Override
	public Integer visit(Program p) {
		return 1 + (Integer) p._e.accept(this);
	}

	@Override
	public Integer visit(PrimeExp e) {
		return 1 + iterThruExps(e);
	}

	private Integer iterThruExps(CompoundArithExp e) {
		Integer result = 0;
		List<Exp> operands = e.all();
		for (Exp exp : operands)
			result += (Integer) exp.accept(this);
		return result;
	}

	@Override
	public Integer visit(MrecExp e) {
		return 1;
	}

	@Override
	public Integer visit(MclrExp e) {
		return 1;
	}

	@Override
	public Integer visit(MaddExp e) {
		return 1 + iterThruExps(e);
	}

	@Override
	public Integer visit(MsubExp e) {
		return 1 + iterThruExps(e);
	}
}