package xyz.lebster.core.parser;

import xyz.lebster.core.exception.SyntaxError;

public final class ParserState {
	public final Token[] tokens;
	public Token currentToken;
	public int index = -1;
	public boolean inBreakContext = false;
	public boolean inContinueContext = false;

	public ParserState(Token[] tokens) {
		this.tokens = tokens;
	}

	private ParserState(Token[] tokens, int index, Token currentToken) {
		this.tokens = tokens;
		this.currentToken = currentToken;
		this.index = index;
	}

	public void expected(TokenType type) throws SyntaxError {
		throw new SyntaxError("Unexpected token %s. Expected %s".formatted(currentToken, type), currentToken.position);
	}

	public void expected(String value) throws SyntaxError {
		throw new SyntaxError("Unexpected token %s. Expected '%s'".formatted(currentToken, value), currentToken.position);
	}

	public void unexpected() throws SyntaxError {
		throw new SyntaxError("Unexpected token " + currentToken, currentToken.position);
	}

	Token consume() {
		final Token oldToken = currentToken;
		if (index + 1 != tokens.length) index++;
		currentToken = tokens[index];
		return oldToken;
	}

	String require(TokenType type) throws SyntaxError {
		if (currentToken.type != type) expected(type);
		return consume().value;
	}

	void require(TokenType type, String value) throws SyntaxError {
		if (currentToken.type != type) expected(type);
		if (!currentToken.value.equals(value)) expected(value);
		consume();
	}

	Token accept(TokenType type) {
		return currentToken.type == type ? consume() : null;
	}

	boolean match(TokenType type, String value) {
		return currentToken.type == type && currentToken.value.equals(value);
	}

	boolean optional(TokenType type) {
		if (currentToken.type == type) {
			consume();
			return true;
		}

		return false;
	}

	boolean is(TokenType... types) {
		for (final TokenType type : types) {
			if (currentToken.type == type)
				return true;
		}

		return false;
	}

	void consumeAll(TokenType t) {
		while (currentToken.type == t) consume();
	}

	boolean isFinished() {
		return index >= tokens.length;
	}

	public ParserState copy() {
		final ParserState cloned = new ParserState(tokens, index, currentToken);
		cloned.inContinueContext = this.inContinueContext;
		cloned.inBreakContext = this.inBreakContext;
		return cloned;
	}
}