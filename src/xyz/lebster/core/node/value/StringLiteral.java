package xyz.lebster.core.node.value;

import xyz.lebster.core.ANSI;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.StringRepresentation;

public final class StringLiteral extends Primitive<String> {
	public StringLiteral(String value) {
		super(value, Type.String);
	}

	public StringLiteral(Object value) {
		super(String.valueOf(value), Type.String);
	}

	@Override
	public String toStringForLogging() {
		return value;
	}

	@Override
	public StringLiteral toStringLiteral(Interpreter interpreter) {
		return this;
	}

	@Override
	public void represent(StringRepresentation representation) {
		final char quoteType = this.value.contains("'") ? '"' : '\'';
		representation.append(ANSI.GREEN);
		representation.append(quoteType);
		representation.append(value);
		representation.append(quoteType);
		representation.append(ANSI.RESET);
	}

	@Override
	public NumericLiteral toNumericLiteral(Interpreter interpreter) {
//		FIXME: Follow spec
		if (value.isBlank())
			return new NumericLiteral(0);

		try {
			return new NumericLiteral(Double.parseDouble(value));
		} catch (NumberFormatException e) {
			return new NumericLiteral(Double.NaN);
		}
	}

	@Override
	public BooleanLiteral toBooleanLiteral(Interpreter interpreter) {
		return new BooleanLiteral(value.length() > 0);
	}

	@Override
	public Dictionary toDictionary(Interpreter interpreter) {
		return new StringWrapper(this);
	}

	@Override
	public String typeOf() {
		return "string";
	}
}