Test.expect(-1, ~0);
Test.expect(-2, ~1);
Test.expect(-3, ~2);
Test.expect(-4, ~3);
Test.expect(-5, ~4);
Test.expect(-6, ~5);
Test.expect(0, ~-1);
Test.expect(-43, ~42);
Test.expect(-10000, ~9999);
Test.expect(-43, ~"42");
Test.expect(-1, ~"foo");
Test.expect(-1, ~[]);
Test.expect(-1, ~{});
Test.expect(-1, ~undefined);
Test.expect(-1, ~null);
Test.expect(-1, ~NaN);
Test.expect(-1, ~Infinity);
Test.expect(-1, ~-Infinity);