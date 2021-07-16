package xyz.lebster.parser;

import xyz.lebster.exception.CannotParse;
import xyz.lebster.exception.NotImplemented;
import xyz.lebster.exception.SyntaxError;
import xyz.lebster.node.*;
import xyz.lebster.node.expression.*;
import xyz.lebster.node.value.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static xyz.lebster.parser.Associativity.*;

public class Parser {
	private static final HashMap<TokenType, Integer> precedence = new HashMap<>();
	private static final HashMap<TokenType, Associativity> associativity = new HashMap<>();

	static {
//		https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Operator_Precedence#table
		precedence.put(TokenType.LParen, 21);
		precedence.put(TokenType.RParen, 21);

		precedence.put(TokenType.LBracket, 20);
		precedence.put(TokenType.RBracket, 20);
		precedence.put(TokenType.Period, 20);

//		Unary Expressions:
		precedence.put(TokenType.Bang, 17);
		precedence.put(TokenType.Typeof, 17);
//		FIXME: Postfix should have higher precedence than prefix
		precedence.put(TokenType.Increment, 17);
		precedence.put(TokenType.Decrement, 17);

		precedence.put(TokenType.Star, 15);
		precedence.put(TokenType.Slash, 15);

		precedence.put(TokenType.Plus, 14);
		precedence.put(TokenType.Minus, 14);

		precedence.put(TokenType.LessThan, 12);
		precedence.put(TokenType.GreaterThan, 12);

		precedence.put(TokenType.StrictEqual, 11);
		precedence.put(TokenType.StrictNotEqual, 11);

		precedence.put(TokenType.LogicalAnd, 7);
		precedence.put(TokenType.LogicalOr, 6);
		precedence.put(TokenType.NullishCoalescing, 5);

		precedence.put(TokenType.UnsignedRightShiftEquals, 3);
		precedence.put(TokenType.LeftShiftEquals, 3);
		precedence.put(TokenType.MinusEquals, 3);
		precedence.put(TokenType.MultiplyEquals, 3);
		precedence.put(TokenType.DivideEquals, 3);
		precedence.put(TokenType.AmpersandEquals, 3);
		precedence.put(TokenType.PercentEquals, 3);
		precedence.put(TokenType.CaretEquals, 3);
		precedence.put(TokenType.PlusEquals, 3);
		precedence.put(TokenType.PipeEquals, 3);
		precedence.put(TokenType.NullishCoalescingEquals, 3);
		precedence.put(TokenType.ExponentEquals, 3);
		precedence.put(TokenType.LogicalAndEquals, 3);
		precedence.put(TokenType.RightShiftEquals, 3);
		precedence.put(TokenType.LogicalOrEquals, 3);
		precedence.put(TokenType.Equals, 3);

//		FIXME: Switch statement method (when all operators are implemented)
		associativity.put(TokenType.LParen, NA);
		associativity.put(TokenType.RParen, NA);

		associativity.put(TokenType.LBracket, Left);
		associativity.put(TokenType.RBracket, Left);
		associativity.put(TokenType.Star, Left);
		associativity.put(TokenType.Slash, Left);
		associativity.put(TokenType.Plus, Left);
		associativity.put(TokenType.Minus, Left);
		associativity.put(TokenType.Period, Left);
		associativity.put(TokenType.StrictEqual, Left);
		associativity.put(TokenType.StrictNotEqual, Left);
		associativity.put(TokenType.LessThan, Left);
		associativity.put(TokenType.GreaterThan, Left);
		associativity.put(TokenType.LogicalOr, Left);
		associativity.put(TokenType.LogicalAnd, Left);
		associativity.put(TokenType.NullishCoalescing, Left);

		associativity.put(TokenType.Bang, Right);
		associativity.put(TokenType.Typeof, Right);
		associativity.put(TokenType.Increment, Right);
		associativity.put(TokenType.Decrement, Right);

		associativity.put(TokenType.UnsignedRightShiftEquals, Right);
		associativity.put(TokenType.LeftShiftEquals, Right);
		associativity.put(TokenType.MinusEquals, Right);
		associativity.put(TokenType.MultiplyEquals, Right);
		associativity.put(TokenType.DivideEquals, Right);
		associativity.put(TokenType.AmpersandEquals, Right);
		associativity.put(TokenType.PercentEquals, Right);
		associativity.put(TokenType.CaretEquals, Right);
		associativity.put(TokenType.PlusEquals, Right);
		associativity.put(TokenType.PipeEquals, Right);
		associativity.put(TokenType.NullishCoalescingEquals, Right);
		associativity.put(TokenType.ExponentEquals, Right);
		associativity.put(TokenType.LogicalAndEquals, Right);
		associativity.put(TokenType.RightShiftEquals, Right);
		associativity.put(TokenType.LogicalOrEquals, Right);
		associativity.put(TokenType.Equals, Right);
	}

	public final Token[] tokens;
	private Token currentToken;
	private int index = -1;

	public Parser(Token[] tokens) {
		this.tokens = tokens;
	}

	private void expected(TokenType t) throws SyntaxError {
		throw new SyntaxError("Unexpected token " + currentToken.type + ". Expected " + t);
	}

	private Token consume() {
		final Token oldToken = currentToken;
		if (index + 1 != tokens.length) index++;
		currentToken = tokens[index];
		return oldToken;
	}

	private Token require(TokenType t) throws SyntaxError {
		if (currentToken.type != t) expected(t);
		return consume();
	}

	private Token accept(TokenType t) {
		return currentToken.type == t ? consume() : null;
	}

	private void consumeAll(TokenType t) {
		while (currentToken.type == t) consume();
	}

	public Program parse() throws SyntaxError, CannotParse {
		final Program program = new Program();
		consume();

		while (index < tokens.length) {
			if (currentToken.type == TokenType.EOF) {
				break;
			} else {
				program.append(parseLine());
			}
		}

		return program;
	}

	private Statement parseLine() throws SyntaxError, CannotParse {
		consumeAll(TokenType.Terminator);
		final Statement result = parseAny();
		consumeAll(TokenType.Semicolon);
		consumeAll(TokenType.Terminator);
		return result;
	}

	private Statement parseAny() throws SyntaxError, CannotParse {
		if (matchDeclaration()) {
			return parseDeclaration();
		} else if (matchStatementOrExpression()) {
			return parseStatementOrExpression();
		} else {
			throw new CannotParse("Token '" + currentToken.type + "'");
		}
	}

	private BlockStatement parseBlockStatement() throws SyntaxError, CannotParse {
		require(TokenType.LBrace);
		final BlockStatement result = new BlockStatement();

		while (index < tokens.length && currentToken.type != TokenType.RBrace) {
			if (currentToken.type == TokenType.EOF) {
				break;
			} else {
				result.append(parseLine());
			}
		}

		require(TokenType.RBrace);
		return result;
	}

	private Statement parseStatementOrExpression() throws SyntaxError, CannotParse {
		if (matchExpression()) {
			return new ExpressionStatement(parseExpression(0, Left));
		} else {
			return switch (currentToken.type) {
				case Function -> parseFunctionDeclaration();
				case Semicolon -> new EmptyStatement();
				case LBrace -> parseBlockStatement();
				case While -> parseWhileStatement();
				case Do -> parseDoWhileStatement();
				case For -> parseForStatement();
				case If -> parseIfStatement();
				case Try -> parseTryStatement();

				case Return -> {
					consume();
//					FIXME: Proper automatic semicolon insertion
					final Expression val = matchExpression() ? parseExpression(0, Left) : new Undefined();
					yield new ReturnStatement(val);
				}

				case Break -> {
					consume();
					yield new BreakStatement();
				}

				case Throw -> {
					consume();
					yield new ThrowStatement(parseExpression(0, Left));
				}

				default -> throw new CannotParse(currentToken, "Statement");
			};
		}
	}

	private ForStatement parseForStatement() throws SyntaxError, CannotParse {
		require(TokenType.For);
		require(TokenType.LParen);

		Statement init = null;
		if (matchExpression() || matchDeclaration()) init = parseAny();
		require(TokenType.Semicolon);

		final Expression test = matchExpression() ? parseExpression(0, Left) : null;
		require(TokenType.Semicolon);

		final Expression update = matchExpression() ? parseExpression(0, Left) : null;
		require(TokenType.RParen);

		final Statement body = parseLine();
		return new ForStatement(init, test, update, body);
	}

	private WhileStatement parseWhileStatement() throws SyntaxError, CannotParse {
		require(TokenType.While);
		require(TokenType.LParen);
		final Expression condition = parseExpression(0, Left);
		require(TokenType.RParen);
		final Statement body = parseLine();
		return new WhileStatement(condition, body);
	}

	private DoWhileStatement parseDoWhileStatement() throws SyntaxError, CannotParse {
		require(TokenType.Do);
		final Statement body = parseLine();
		require(TokenType.While);
		require(TokenType.LParen);
		final Expression condition = parseExpression(0, Left);
		require(TokenType.RParen);
		return new DoWhileStatement(body, condition);
	}

	private TryStatement parseTryStatement() throws SyntaxError, CannotParse {
		require(TokenType.Try);
		final BlockStatement body = parseBlockStatement();
		require(TokenType.Catch);
		require(TokenType.LParen);
		final Identifier parameter = new Identifier(require(TokenType.Identifier).value);
		require(TokenType.RParen);
		final BlockStatement catchBody = parseBlockStatement();
		return new TryStatement(body, new CatchClause(parameter, catchBody));
	}

	private IfStatement parseIfStatement() throws SyntaxError, CannotParse {
		require(TokenType.If);
		require(TokenType.LParen);
		final Expression condition = parseExpression(0, Left);
		require(TokenType.RParen);
		final Statement consequence = parseLine();
		final Statement elseStatement = currentToken.type == TokenType.Else ? parseElseStatement() : null;
		return new IfStatement(condition, consequence, elseStatement);
	}

	public Statement parseElseStatement() throws SyntaxError, CannotParse {
		require(TokenType.Else);
		return parseLine();
	}

	private Declaration parseDeclaration() throws SyntaxError, CannotParse {
		return switch (currentToken.type) {
			case Let, Var, Const -> parseVariableDeclaration();
			case Function -> parseFunctionDeclaration();
			default -> throw new CannotParse(currentToken, "Declaration");
		};
	}

	private VariableDeclaration parseVariableDeclaration() throws SyntaxError, CannotParse {
		consume();
		final Token identifier = require(TokenType.Identifier);
		require(TokenType.Equals);
		final Expression value = parseExpression(0, Left);
		return new VariableDeclaration(new VariableDeclarator(new Identifier(identifier.value), value));
	}

	private List<Identifier> parseFunctionArguments() throws SyntaxError {
		require(TokenType.LParen);
		final List<Identifier> arguments = new ArrayList<>();

		while (currentToken.type == TokenType.Identifier) {
			arguments.add(new Identifier(consume().value));
			if (accept(TokenType.Comma) == null) break;
		}

		require(TokenType.RParen);
		return arguments;
	}

	private FunctionDeclaration parseFunctionDeclaration() throws SyntaxError, CannotParse {
		require(TokenType.Function);
		final Identifier name = new Identifier(require(TokenType.Identifier).value);
		final List<Identifier> arguments = parseFunctionArguments();
		return new FunctionDeclaration(parseBlockStatement(), name, arguments.toArray(new Identifier[0]));
	}

	private FunctionExpression parseFunctionExpression() throws SyntaxError, CannotParse {
		require(TokenType.Function);
		final Token potentialName = accept(TokenType.Identifier);
		final Identifier name = potentialName == null ? null : new Identifier(potentialName.value);
		final List<Identifier> arguments = parseFunctionArguments();
		return new FunctionExpression(parseBlockStatement(), name, arguments.toArray(new Identifier[0]));
	}

	private List<Expression> parseExpressionList() throws SyntaxError, CannotParse {
		final List<Expression> result = new ArrayList<>();

		while (matchExpression()) {
			result.add(parseExpression(0, Left));
			if (accept(TokenType.Comma) == null) break;
		}

		return result;
	}

	private CallExpression parseCallExpression(Expression left) throws SyntaxError, CannotParse {
		final List<Expression> arguments = parseExpressionList();
		require(TokenType.RParen);
		return new CallExpression(left, arguments.toArray(new Expression[0]));
	}

	@SpecificationURL("https://tc39.es/ecma262/multipage#prod-UnaryExpression")
	private Expression parseUnaryPrefixedExpression() throws SyntaxError, CannotParse {
		final Token token = consume();
//		TODO: Remove when all implemented
		if (!associativity.containsKey(token.type))
			throw new NotImplemented("Associativity for token '" + token.type + "'");
		final Associativity assoc = associativity.get(token.type);
//		TODO: Remove when all implemented
		if (!precedence.containsKey(token.type)) throw new NotImplemented("Precedence for token '" + token.type + "'");
		final int minPrecedence = precedence.get(token.type);

		final UnaryExpression.UnaryOp op = switch (token.type) {
			case Minus -> UnaryExpression.UnaryOp.Negate;
			case Bang -> UnaryExpression.UnaryOp.LogicalNot;
			case Typeof -> UnaryExpression.UnaryOp.Typeof;
			case Increment -> UnaryExpression.UnaryOp.PreIncrement;
			case Decrement -> UnaryExpression.UnaryOp.PreDecrement;
			default -> throw new CannotParse(token, "Unary Operator");
		};

		return new UnaryExpression(parseExpression(minPrecedence, assoc), op);
	}

	private Expression parseExpression(int minPrecedence, Associativity assoc) throws SyntaxError, CannotParse {
		Expression latestExpr = parsePrimaryExpression();

		while (matchSecondaryExpression()) {
//			TODO: Remove when all implemented
			if (!precedence.containsKey(currentToken.type))
				throw new NotImplemented("Precedence for token '" + currentToken.type + "'");
			final int newPrecedence = precedence.get(currentToken.type);

			if (newPrecedence < minPrecedence || newPrecedence == minPrecedence && assoc == Left) {
				break;
			}

//			TODO: Remove when all implemented
			if (!associativity.containsKey(currentToken.type))
				throw new NotImplemented("Associativity for token '" + currentToken.type + "'");
			final Associativity newAssoc = associativity.get(currentToken.type);
			latestExpr = parseSecondaryExpression(latestExpr, newPrecedence, newAssoc);
		}

		return latestExpr;
	}

	private Expression parseSecondaryExpression(Expression left, int minPrecedence, Associativity assoc) throws SyntaxError, CannotParse {
		consumeAll(TokenType.Terminator);
		final Token token = consume();
		consumeAll(TokenType.Terminator);

		return switch (token.type) {
			case Plus -> new BinaryExpression(left, parseExpression(minPrecedence, assoc), BinaryExpression.BinaryOp.Add);
			case Minus -> new BinaryExpression(left, parseExpression(minPrecedence, assoc), BinaryExpression.BinaryOp.Subtract);
			case Star -> new BinaryExpression(left, parseExpression(minPrecedence, assoc), BinaryExpression.BinaryOp.Multiply);
			case Slash -> new BinaryExpression(left, parseExpression(minPrecedence, assoc), BinaryExpression.BinaryOp.Divide);
			case LParen -> parseCallExpression(left);
			case Equals -> new AssignmentExpression(left, parseExpression(minPrecedence, assoc), AssignmentExpression.AssignmentOp.Assign);
			case StrictEqual -> new EqualityExpression(left, parseExpression(minPrecedence, assoc), EqualityExpression.EqualityOp.StrictEquals);
			case StrictNotEqual -> new EqualityExpression(left, parseExpression(minPrecedence, assoc), EqualityExpression.EqualityOp.StrictNotEquals);
			case LogicalOr -> new LogicalExpression(left, parseExpression(minPrecedence, assoc), LogicalExpression.LogicOp.Or);
			case LogicalAnd -> new LogicalExpression(left, parseExpression(minPrecedence, assoc), LogicalExpression.LogicOp.And);
			case NullishCoalescing -> new LogicalExpression(left, parseExpression(minPrecedence, assoc), LogicalExpression.LogicOp.Coalesce);
			case PlusEquals -> new AssignmentExpression(left, parseExpression(minPrecedence, assoc), AssignmentExpression.AssignmentOp.PlusAssign);
			case MinusEquals -> new AssignmentExpression(left, parseExpression(minPrecedence, assoc), AssignmentExpression.AssignmentOp.MinusAssign);
			case Decrement -> new UnaryExpression(left, UnaryExpression.UnaryOp.PostDecrement);
			case Increment -> new UnaryExpression(left, UnaryExpression.UnaryOp.PostIncrement);
			case LessThan -> new RelationalExpression(left, parseExpression(minPrecedence, assoc), RelationalExpression.RelationalOp.LessThan);
			case GreaterThan -> new RelationalExpression(left, parseExpression(minPrecedence, assoc), RelationalExpression.RelationalOp.GreaterThan);

			case Period -> {
				final String prop = require(TokenType.Identifier).value;
				yield new MemberExpression(left, new StringLiteral(prop), false);
			}

			case LBracket -> {
				final Expression prop = parseExpression(0, Left);
				require(TokenType.RBracket);
				yield new MemberExpression(left, prop, true);
			}

			default -> throw new CannotParse(currentToken, "SecondaryExpression");
		};
	}

	private Expression parsePrimaryExpression() throws SyntaxError, CannotParse {
		if (matchUnaryPrefixedExpression()) {
			return parseUnaryPrefixedExpression();
		}

		return switch (currentToken.type) {
			case LParen -> {
				consume();
				final Expression expression = parseExpression(0, Left);
				require(TokenType.RParen);
				yield expression;
			}

			case StringLiteral -> new StringLiteral(consume().value);
			case NumericLiteral -> new NumericLiteral(Double.parseDouble(consume().value));
			case BooleanLiteral -> new BooleanLiteral(consume().value.equals("true"));
			case Identifier -> new Identifier(consume().value);
			case Function -> parseFunctionExpression();
			case LBracket -> parseArrayExpression();

			case This -> {
				consume();
				yield new ThisKeyword();
			}

			case Null -> {
				consume();
				yield new Null();
			}

			case Infinity -> {
				consume();
				yield new NumericLiteral(Double.POSITIVE_INFINITY);
			}

			case NaN -> {
				consume();
				yield new NumericLiteral(Double.NaN);
			}

			case Undefined -> {
				consume();
				yield new Undefined();
			}

			default -> throw new CannotParse(currentToken, "PrimaryExpression");
		};
	}

	private ArrayExpression parseArrayExpression() throws SyntaxError, CannotParse {
		require(TokenType.LBracket);
		final List<Expression> elements = parseExpressionList();
		require(TokenType.RBracket);
		return new ArrayExpression(elements);
	}

	private boolean matchDeclaration() {
		final TokenType t = currentToken.type;
		return t == TokenType.Function ||
			   t == TokenType.Let ||
			   t == TokenType.Var ||
			   t == TokenType.Const;
	}

	private boolean matchStatementOrExpression() {
		final TokenType t = currentToken.type;
		return matchExpression() ||
			   t == TokenType.Return ||
			   t == TokenType.Yield ||
			   t == TokenType.Do ||
			   t == TokenType.If ||
			   t == TokenType.Throw ||
			   t == TokenType.Try ||
			   t == TokenType.While ||
			   t == TokenType.With ||
			   t == TokenType.For ||
			   t == TokenType.LBrace ||
			   t == TokenType.Switch ||
			   t == TokenType.Break ||
			   t == TokenType.Continue ||
			   t == TokenType.Var ||
			   t == TokenType.Debugger ||
			   t == TokenType.Semicolon;

	}

	private boolean matchExpression() {
		final TokenType t = currentToken.type;
		return t == TokenType.StringLiteral ||
			   t == TokenType.NumericLiteral ||
			   t == TokenType.BooleanLiteral ||
			   t == TokenType.Null ||
			   t == TokenType.Function ||
			   t == TokenType.Infinity ||
			   t == TokenType.Undefined ||
			   t == TokenType.NaN ||
			   t == TokenType.This ||
			   t == TokenType.Identifier ||
			   t == TokenType.LParen ||
			   t == TokenType.LBracket ||
			   matchUnaryPrefixedExpression();
	}

	private boolean matchSecondaryExpression() {
		final TokenType t = currentToken.type;
		return t == TokenType.Plus ||
			   t == TokenType.Minus ||
			   t == TokenType.Star ||
			   t == TokenType.Slash ||
			   t == TokenType.StrictEqual ||
			   t == TokenType.StrictNotEqual ||
			   t == TokenType.LogicalOr ||
			   t == TokenType.LogicalAnd ||
			   t == TokenType.Period ||
			   t == TokenType.LessThan ||
			   t == TokenType.GreaterThan ||
			   t == TokenType.PlusEquals ||
			   t == TokenType.MinusEquals ||
			   t == TokenType.DivideEquals ||
			   t == TokenType.MultiplyEquals ||
			   t == TokenType.LeftShiftEquals ||
			   t == TokenType.RightShiftEquals ||
			   t == TokenType.NullishCoalescing ||
			   t == TokenType.LBracket ||
			   t == TokenType.LParen ||
			   t == TokenType.Equals ||
			   t == TokenType.Increment ||
			   t == TokenType.Decrement;
	}

	private boolean matchUnaryPrefixedExpression() {
		final TokenType t = currentToken.type;
		return t == TokenType.Increment ||
			   t == TokenType.Decrement ||
			   t == TokenType.Bang ||
			   t == TokenType.Tilde ||
			   t == TokenType.Plus ||
			   t == TokenType.Minus ||
			   t == TokenType.Typeof ||
			   t == TokenType.Void ||
			   t == TokenType.Delete;
	}
}