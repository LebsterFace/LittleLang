package xyz.lebster.interpreter;

import xyz.lebster.node.value.Value;

public record ExecutionContext(Value<?> executedCallee, Value<?> thisValue) {
}