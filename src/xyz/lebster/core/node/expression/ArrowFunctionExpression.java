package xyz.lebster.core.node.expression;

import xyz.lebster.core.DumpBuilder;
import xyz.lebster.core.Dumper;
import xyz.lebster.core.interpreter.Interpreter;
import xyz.lebster.core.interpreter.StringRepresentation;
import xyz.lebster.core.node.ASTNode;
import xyz.lebster.core.node.declaration.AssignmentTarget;
import xyz.lebster.core.node.statement.BlockStatement;
import xyz.lebster.core.value.function.ArrowFunction;

public final class ArrowFunctionExpression implements Expression {
	public final AssignmentTarget[] arguments;
	public final BlockStatement body;
	public final Expression implicitReturnExpression;
	public final boolean hasFullBody;

	public ArrowFunctionExpression(BlockStatement body, AssignmentTarget... arguments) {
		this.arguments = arguments;
		this.body = body;
		this.hasFullBody = true;
		this.implicitReturnExpression = null;
	}

	public ArrowFunctionExpression(Expression implicitReturnExpression, AssignmentTarget... arguments) {
		this.body = null;
		this.arguments = arguments;
		this.hasFullBody = false;
		this.implicitReturnExpression = implicitReturnExpression;
	}

	@Override
	public ArrowFunction execute(Interpreter interpreter) {
		return new ArrowFunction(interpreter, this, interpreter.executionContext());
	}

	@Override
	public void dump(int indent) {
		DumpBuilder.begin(indent)
			.self(this)
			.children("Arguments", arguments);

		if (this.hasFullBody) {
			assert body != null;
			for (final ASTNode child : body.children()) {
				child.dump(indent + 1);
			}
		} else {
			assert this.implicitReturnExpression != null;
			Dumper.dumpName(indent + 1, "ImplicitReturnStatement");
			this.implicitReturnExpression.dump(indent + 2);
		}
	}

	@Override
	public void represent(StringRepresentation representation) {
		if (arguments.length == 1) {
			representation.append(arguments[0]);
		} else {
			representation.append('(');
			if (arguments.length > 0) {
				representation.append(arguments[0]);
				for (int i = 1; i < arguments.length; i++) {
					representation.append(", ");
					representation.append(arguments[i]);
				}
			}
			representation.append(')');
		}

		representation.append(" => ");

		if (this.hasFullBody) {
			assert body != null;
			body.represent(representation);
		} else {
			assert implicitReturnExpression != null;
			implicitReturnExpression.represent(representation);
		}
	}
}