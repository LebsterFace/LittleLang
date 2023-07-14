package xyz.lebster.core.node.expression.literal;

import xyz.lebster.core.interpreter.StringRepresentation;
import xyz.lebster.core.value.globals.Null;

public final class NullLiteral implements Literal<Null> {
	@Override
	public Null value() {
		return Null.instance;
	}

	@Override
	public void represent(StringRepresentation representation) {
		representation.append("null");
	}
}
