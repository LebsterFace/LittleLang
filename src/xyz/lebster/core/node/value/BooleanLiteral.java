package xyz.lebster.core.node.value;

import xyz.lebster.core.ANSI;
import xyz.lebster.core.exception.NotImplemented;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.StringRepresentation;

public final class BooleanLiteral extends Primitive<Boolean> {
	public BooleanLiteral(boolean value) {
		super(value, Type.Boolean);
	}

	@Override
	public void represent(StringRepresentation representation) {
		representation.append(ANSI.BRIGHT_YELLOW);
		representation.append(value);
		representation.append(ANSI.RESET);
	}

	@Override
	public NumericLiteral toNumericLiteral(Interpreter interpreter) {
		return new NumericLiteral(value ? 1.0 : 0.0);
	}

	@Override
	public BooleanLiteral toBooleanLiteral(Interpreter interpreter) {
		return this;
	}

	@Override
	public Dictionary toDictionary(Interpreter interpreter) {
		throw new NotImplemented("BooleanWrapper");
	}

	public BooleanLiteral not() {
		return new BooleanLiteral(!value);
	}

	@Override
	public String typeOf(Interpreter interpreter) {
		return "boolean";
	}
}