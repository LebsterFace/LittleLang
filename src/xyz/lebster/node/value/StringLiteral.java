package xyz.lebster.node.value;

import xyz.lebster.exception.NotImplemented;
import xyz.lebster.interpreter.Interpreter;

public class StringLiteral extends Primitive<String> {
	public StringLiteral(String value) {
		super(value, Type.String);
	}

	@Override
	public String toString() {
		return '"' + value + '"';
	}

	@Override
	public StringLiteral toStringLiteral() {
		return this;
	}

	@Override
	public NumericLiteral toNumericLiteral() {
		throw new NotImplemented("StringLiteral -> NumericLiteral");
	}

	@Override
	public BooleanLiteral toBooleanLiteral() {
		return new BooleanLiteral(value.length() > 0);
	}

	@Override
	public Dictionary toDictionary() {
		return new StringWrapper(this);
	}

	@Override
	public String typeOf() {
		return "string";
	}
}