package xyz.lebster.core.value;

import xyz.lebster.core.node.Expression;
import xyz.lebster.core.runtime.Interpreter;
import xyz.lebster.core.value.prototype.ObjectPrototype;

abstract public class Value<JType> implements Expression {
	public final Type type;
	public final JType value;

	public Value(Type type, JType value) {
		this.type = type;
		this.value = value;
	}

	public StringLiteral toStringLiteral() {
		return new StringLiteral(String.valueOf(value));
	}

	public abstract BooleanLiteral toBooleanLiteral();

	public abstract NumericLiteral toNumericLiteral();

	public abstract Function toFunction();

	public abstract Dictionary toDictionary();

	public Dictionary getPrototype() {
		return ObjectPrototype.instance;
	}

	@Override
	public void dump(int indent) {
		Interpreter.dumpIndent(indent);
		System.out.print(type);
		System.out.print(": ");
		System.out.println(value);
	}

	@Override
	public Value<JType> execute(Interpreter interpreter) {
		return this;
	}
}
