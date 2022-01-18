package xyz.lebster.core.node.expression;

import xyz.lebster.core.Dumper;
import xyz.lebster.core.exception.NotImplemented;
import xyz.lebster.core.interpreter.AbruptCompletion;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.Reference;
import xyz.lebster.core.interpreter.StringRepresentation;
import xyz.lebster.core.runtime.value.primitive.NumberValue;
import xyz.lebster.core.runtime.value.primitive.StringValue;
import xyz.lebster.core.runtime.value.primitive.UndefinedValue;
import xyz.lebster.core.runtime.value.Value;
import xyz.lebster.core.runtime.value.error.LanguageError;

public record UnaryExpression(Expression expression, UnaryExpression.UnaryOp op) implements Expression {
	@Override
	public Value<?> execute(Interpreter interpreter) throws AbruptCompletion {
		return switch (op) {
			case UnaryPlus -> expression.execute(interpreter).toNumberValue(interpreter);
			case UnaryMinus -> expression.execute(interpreter).toNumberValue(interpreter).unaryMinus();
			case LogicalNot -> expression.execute(interpreter).toBooleanValue(interpreter).not();
			case Typeof -> {
				// https://tc39.es/ecma262/multipage#sec-typeof-operator-runtime-semantics-evaluation
				if (expression instanceof final LeftHandSideExpression lhs) {
					final Reference reference = lhs.toReference(interpreter);
					if (reference.isResolvable()) {
						yield new StringValue(reference.getValue(interpreter).typeOf(interpreter));
					} else {
						yield new StringValue("undefined");
					}
				} else {
					yield new StringValue(expression.execute(interpreter).typeOf(interpreter));
				}
			}

			// https://tc39.es/ecma262/multipage#sec-postfix-increment-operator
			case PostIncrement, PostDecrement, PreIncrement, PreDecrement -> {
				if (!(expression instanceof final LeftHandSideExpression lhs)) {
					throw AbruptCompletion.error(new LanguageError("Invalid left-hand side expression in postfix/prefix operation"));
				}

				final Reference left_reference = lhs.toReference(interpreter);
				final NumberValue oldValue = lhs.execute(interpreter).toNumberValue(interpreter);
				final NumberValue newValue = new NumberValue(switch (op) {
					case PostIncrement, PreIncrement -> oldValue.value + 1.0;
					case PostDecrement, PreDecrement -> oldValue.value - 1.0;
					default -> throw new IllegalStateException("Unexpected value: " + op);
				});

				left_reference.setValue(interpreter, newValue);
				yield switch (op) {
					case PostIncrement, PostDecrement -> oldValue;
					case PreIncrement, PreDecrement -> newValue;
					default -> throw new IllegalStateException("Unexpected value: " + op);
				};
			}


			// https://tc39.es/ecma262/multipage#sec-void-operator-runtime-semantics-evaluation
			case Void -> {
				// UnaryExpression : void UnaryExpression
				// 1. Let expr be the result of evaluating UnaryExpression.
				// 2. Perform ? GetValue(expr).
				expression.execute(interpreter);
				// 3. Return undefined.
				yield UndefinedValue.instance;
			}

			case Delete -> throw new NotImplemented("The `delete` operator");
			case BitwiseNot -> throw new NotImplemented("The `~` operator");
			case Await -> throw new NotImplemented("The `await` operator");
		};
	}

	@Override
	public void dump(int indent) {
		Dumper.dumpName(indent, "UnaryExpression");
		Dumper.dumpIndicated(indent + 1, "Expression", expression);
		Dumper.dumpEnum(indent + 1, "Operator", op);
	}

	@Override
	public void represent(StringRepresentation representation) {
		representation.append(switch (op) {
			case UnaryMinus -> '-';
			case LogicalNot -> '!';
			case UnaryPlus -> '+';
			case Delete -> "delete ";
			case Void -> "void ";
			case Typeof -> "typeof ";
			case PreDecrement -> "--";
			case PreIncrement -> "++";
			case PostIncrement, PostDecrement -> "";
			case BitwiseNot -> '~';
			case Await -> "await ";
		});

		expression.represent(representation);

		switch (op) {
			case PostDecrement -> representation.append("--");
			case PostIncrement -> representation.append("++");
		}
	}

	public enum UnaryOp {
		// FIXME: Split into UpdateExpression
		PostIncrement, PostDecrement, PreIncrement, PreDecrement,
		Delete, Void, Typeof, UnaryPlus, UnaryMinus, BitwiseNot, LogicalNot, Await
	}
}