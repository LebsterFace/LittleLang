Test.expect("hello world", "   hello world  ".trim());
Test.expect("hello world", "hello world   ".trim());
Test.expect("hello world", "   hello world".trim());
Test.expect("hello world", "\thello world\t".trim());
Test.expect("hello world", "\thello world".trim());
Test.expect("hello world", "hello world\t".trim());
Test.expect("hello world", "\rhello world\r".trim());
Test.expect("hello world", "\rhello world".trim());
Test.expect("hello world", "hello world\r".trim());
Test.expect("hello world", "\rhello world\n".trim());
Test.expect("hello world", "\r\thello world".trim());
Test.expect("hello world", "hello world\r\n".trim());
Test.expect("hello world", "  \thello world\r\n".trim());
Test.expect("hello world", "\n\t\thello world\r\n".trim());
Test.expect("hello world", "\n\t\thello world\t\t".trim());
Test.expect("hello world", "   hello world".trimStart());
Test.expect("hello world   ", "hello world   ".trimStart());
Test.expect("hello world   ", "    hello world   ".trimStart());
Test.expect("hello world", "\thello world".trimStart());
Test.expect("hello world\t", "hello world\t".trimStart());
Test.expect("hello world\t", "\thello world\t".trimStart());
Test.expect("hello world", "\rhello world".trimStart());
Test.expect("hello world\r", "hello world\r".trimStart());
Test.expect("hello world\r", "\rhello world\r".trimStart());
Test.expect("hello world", "hello world   ".trimEnd());
Test.expect("   hello world", "   hello world".trimEnd());
Test.expect("   hello world", "   hello world   ".trimEnd());
Test.expect("hello world", "hello world\t".trimEnd());
Test.expect("\thello world", "\thello world".trimEnd());
Test.expect("\thello world", "\thello world\t".trimEnd());
Test.expect("hello world", "hello world\r".trimEnd());
Test.expect("\rhello world", "\rhello world".trimEnd());
Test.expect("\rhello world", "\rhello world\r".trimEnd());
Test.expect("hello world", "hello world\n".trimEnd());
Test.expect("\r\nhello world", "\r\nhello world".trimEnd());
Test.expect("\rhello world", "\rhello world\r\n".trimEnd());
Test.expect("_\u180E", "_\u180E".trim());
Test.expect("\u180E", "\u180E".trim());
Test.expect("\u180E_", "\u180E_".trim());
Test.expect("_😀", "_😀".trim());
Test.expect("😀", "😀".trim());
Test.expect("😀_", "😀_".trim());