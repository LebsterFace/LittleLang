// length
Test.expect(0, Map.prototype.entries.length);

// basic functionality
const original = [
	["a", 0],
	["b", 1],
	["c", 2],
];
const a = new Map(original);
const it = a.entries();
Test.expectEqual({ value: ["a", 0], done: false }, it.next());
Test.expectEqual({ value: ["b", 1], done: false }, it.next());
Test.expectEqual({ value: ["c", 2], done: false }, it.next());
Test.expectEqual({ value: undefined, done: true }, it.next());
Test.expectEqual({ value: undefined, done: true }, it.next());
Test.expectEqual({ value: undefined, done: true }, it.next());