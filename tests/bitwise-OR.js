expect(0, NaN | NaN);
expect(0, NaN | Infinity);
expect(0, NaN | (-Infinity));
expect(0, NaN | (-0));
expect(0, NaN | 0);
expect(3, NaN | 3);
expect(13, NaN | 13);
expect(0, NaN | 'str');
expect(-14, NaN | (-14));
expect(0, Infinity | NaN);
expect(0, Infinity | Infinity);
expect(0, Infinity | (-Infinity));
expect(0, Infinity | (-0));
expect(0, Infinity | 0);
expect(3, Infinity | 3);
expect(13, Infinity | 13);
expect(0, Infinity | 'str');
expect(-14, Infinity | (-14));
expect(0, (-Infinity) | NaN);
expect(0, (-Infinity) | Infinity);
expect(0, (-Infinity) | (-Infinity));
expect(0, (-Infinity) | (-0));
expect(0, (-Infinity) | 0);
expect(3, (-Infinity) | 3);
expect(13, (-Infinity) | 13);
expect(0, (-Infinity) | 'str');
expect(-14, (-Infinity) | (-14));
expect(0, (-0) | NaN);
expect(0, (-0) | Infinity);
expect(0, (-0) | (-Infinity));
expect(0, (-0) | (-0));
expect(0, (-0) | 0);
expect(3, (-0) | 3);
expect(13, (-0) | 13);
expect(0, (-0) | 'str');
expect(-14, (-0) | (-14));
expect(0, 0 | NaN);
expect(0, 0 | Infinity);
expect(0, 0 | (-Infinity));
expect(0, 0 | (-0));
expect(0, 0 | 0);
expect(3, 0 | 3);
expect(13, 0 | 13);
expect(0, 0 | 'str');
expect(-14, 0 | (-14));
expect(3, 3 | NaN);
expect(3, 3 | Infinity);
expect(3, 3 | (-Infinity));
expect(3, 3 | (-0));
expect(3, 3 | 0);
expect(3, 3 | 3);
expect(15, 3 | 13);
expect(3, 3 | 'str');
expect(-13, 3 | (-14));
expect(13, 13 | NaN);
expect(13, 13 | Infinity);
expect(13, 13 | (-Infinity));
expect(13, 13 | (-0));
expect(13, 13 | 0);
expect(15, 13 | 3);
expect(13, 13 | 13);
expect(13, 13 | 'str');
expect(-1, 13 | (-14));
expect(0, 'str' | NaN);
expect(0, 'str' | Infinity);
expect(0, 'str' | (-Infinity));
expect(0, 'str' | (-0));
expect(0, 'str' | 0);
expect(3, 'str' | 3);
expect(13, 'str' | 13);
expect(0, 'str' | 'str');
expect(-14, 'str' | (-14));
expect(-14, (-14) | NaN);
expect(-14, (-14) | Infinity);
expect(-14, (-14) | (-Infinity));
expect(-14, (-14) | (-0));
expect(-14, (-14) | 0);
expect(-13, (-14) | 3);
expect(-1, (-14) | 13);
expect(-14, (-14) | 'str');
expect(-14, (-14) | (-14));