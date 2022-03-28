package xyz.lebster.core.node.expression;

import xyz.lebster.core.DumpBuilder;
import xyz.lebster.core.SpecificationURL;
import xyz.lebster.core.interpreter.AbruptCompletion;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.StringRepresentation;
import xyz.lebster.core.runtime.value.Value;

public record LogicalExpression(Expression left, Expression right, LogicOp op) implements Expression {
	@Override
	public void dump(int indent) {
		DumpBuilder.begin(indent).binaryExpression(this, left, op, right);
	}

	@Override
	@SpecificationURL("https://tc39.es/ecma262/multipage#sec-binary-logical-operators")
	public Value<?> execute(Interpreter interpreter) throws AbruptCompletion {
		final Value<?> left_value = left.execute(interpreter);

		return switch (op) {
			case And -> left_value.isTruthy(interpreter) ? right.execute(interpreter) : left_value;
			case Or -> left_value.isTruthy(interpreter) ? left_value : right.execute(interpreter);
			case Coalesce -> left_value.isNullish() ? right.execute(interpreter) : left_value;
		};
	}

	@Override
	public void represent(StringRepresentation representation) {
		left.represent(representation);
		representation.append(' ');
		representation.append(switch (op) {
			case And -> "&&";
			case Or -> "||";
			case Coalesce -> "??";
		});
		representation.append(' ');
		right.represent(representation);
	}

	public enum LogicOp {
		And, Or, Coalesce
	}
}