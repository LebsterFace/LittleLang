Test.expect(128104, "👨‍👩‍👦".codePointAt(0));
Test.expect(56424, "👨‍👩‍👦".codePointAt(1));
Test.expect(8205, "👨‍👩‍👦".codePointAt(2));
Test.expect(128105, "👨‍👩‍👦".codePointAt(3));
Test.expect(56425, "👨‍👩‍👦".codePointAt(4));
Test.expect(8205, "👨‍👩‍👦".codePointAt(5));
Test.expect(128102, "👨‍👩‍👦".codePointAt(6));
Test.expect(56422, "👨‍👩‍👦".codePointAt(7));
Test.expect(undefined, "ABC".codePointAt(-1));
Test.expect(65, "ABC".codePointAt(0));
Test.expect(66, "ABC".codePointAt(1));
Test.expect(67, "ABC".codePointAt(2));
Test.expect(undefined, "ABC".codePointAt(3));
Test.expect(67197, "𐙽".codePointAt(0));
Test.expect(56957, "𐙽".codePointAt(1));
Test.expect(undefined, "𐙽".codePointAt(2));
Test.expect(70, "Foobar".codePointAt(0));
Test.expect(111, "Foobar".codePointAt(1));
Test.expect(111, "Foobar".codePointAt(2));
Test.expect(98, "Foobar".codePointAt(3));
Test.expect(97, "Foobar".codePointAt(4));
Test.expect(114, "Foobar".codePointAt(5));
Test.expect(undefined, "Foobar".codePointAt(6));
Test.expect(undefined, "Foobar".codePointAt(-1));
Test.expect(70, "Foobar".codePointAt());
Test.expect(70, "Foobar".codePointAt(NaN));
Test.expect(70, "Foobar".codePointAt("foo"));
Test.expect(70, "Foobar".codePointAt(undefined));
Test.expect(128512, "😀".codePointAt(0));
Test.expect(56832, "😀".codePointAt(1));
Test.expect(undefined, "😀".codePointAt(2));