function xq_t1(n){
return 	TEA.strToBytes(n.toUpperCase(), !0);
}
function xq_a4(e){
	return 	TEA.strToBytes(e);
}
function xq_t2(n,p,s,e,u,c){
	TEA.initkey(n);
	return  TEA.enAsBase64(p + s + new String(e) + u + c);
}
function e() {
        return Math.round(4294967295 * Math.random())
    }
    function n(t, e, n) {
        (!n || n > 4) && (n = 4);
        for (var i = 0, o = e; e + n > o; o++)
            i <<= 8,
            i |= t[o];
        return (4294967295 & i) >>> 0
    }
    function i(t, e, n) {
        t[e + 3] = n >> 0 & 255,
        t[e + 2] = n >> 8 & 255,
        t[e + 1] = n >> 16 & 255,
        t[e + 0] = n >> 24 & 255
    }
    function o(t) {
        if (!t)
            return "";
        for (var e = "", n = 0; n < t.length; n++) {
            var i = Number(t[n]).toString(16);
            1 == i.length && (i = "0" + i),
            e += i
        }
        return e
    }
    function r(t) {
        for (var e = "", n = 0; n < t.length; n += 2)
            e += String.fromCharCode(parseInt(t.substr(n, 2), 16));
        return e
    }
    function a(t, e) {
        if (!t)
            return "";
        e && (t = s(t));
        for (var n = [], i = 0; i < t.length; i++)
            n[i] = t.charCodeAt(i);
        return o(n)
    }
    function s(t) {
        var e, n, i = [], o = t.length;
        for (e = 0; o > e; e++)
            n = t.charCodeAt(e),
            n > 0 && 127 >= n ? i.push(t.charAt(e)) : n >= 128 && 2047 >= n ? i.push(String.fromCharCode(192 | n >> 6 & 31), String.fromCharCode(128 | 63 & n)) : n >= 2048 && 65535 >= n && i.push(String.fromCharCode(224 | n >> 12 & 15), String.fromCharCode(128 | n >> 6 & 63), String.fromCharCode(128 | 63 & n));
        return i.join("")
    }
    function p(t) {
        _ = new Array(8),
        w = new Array(8),
        v = y = 0,
        k = !0,
        g = 0;
        var n = t.length
          , i = 0;
        g = (n + 10) % 8,
        0 != g && (g = 8 - g),
        b = new Array(n + g + 10),
        _[0] = 255 & (248 & e() | g);
        for (var o = 1; g >= o; o++)
            _[o] = 255 & e();
        g++;
        for (var o = 0; 8 > o; o++)
            w[o] = 0;
        for (i = 1; 2 >= i; )
            8 > g && (_[g++] = 255 & e(),
            i++),
            8 == g && u();
        for (var o = 0; n > 0; )
            8 > g && (_[g++] = t[o++],
            n--),
            8 == g && u();
        for (i = 1; 7 >= i; )
            8 > g && (_[g++] = 0,
            i++),
            8 == g && u();
        return b
    }
    function c(t) {
        var e = 0
          , n = new Array(8)
          , i = t.length;
        if ($ = t,
        i % 8 != 0 || 16 > i)
            return null ;
        if (w = d(t),
        g = 7 & w[0],
        e = i - g - 10,
        0 > e)
            return null ;
        for (var o = 0; o < n.length; o++)
            n[o] = 0;
        b = new Array(e),
        y = 0,
        v = 8,
        g++;
        for (var r = 1; 2 >= r; )
            if (8 > g && (g++,
            r++),
            8 == g && (n = t,
            !h()))
                return null ;
        for (var o = 0; 0 != e; )
            if (8 > g && (b[o] = 255 & (n[y + g] ^ w[g]),
            o++,
            e--,
            g++),
            8 == g && (n = t,
            y = v - 8,
            !h()))
                return null ;
        for (r = 1; 8 > r; r++) {
            if (8 > g) {
                if (0 != (n[y + g] ^ w[g]))
                    return null ;
                g++
            }
            if (8 == g && (n = t,
            y = v,
            !h()))
                return null 
        }
        return b
    }
    function u() {
        for (var t = 0; 8 > t; t++)
            _[t] ^= k ? w[t] : b[y + t];
        for (var e = l(_), t = 0; 8 > t; t++)
            b[v + t] = e[t] ^ w[t],
            w[t] = _[t];
        y = v,
        v += 8,
        g = 0,
        k = !1
    }
    function l(t) {
        for (var e = 16, o = n(t, 0, 4), r = n(t, 4, 4), a = n(m, 0, 4), s = n(m, 4, 4), p = n(m, 8, 4), c = n(m, 12, 4), u = 0, l = 2654435769; e-- > 0; )
            u += l,
            u = (4294967295 & u) >>> 0,
            o += (r << 4) + a ^ r + u ^ (r >>> 5) + s,
            o = (4294967295 & o) >>> 0,
            r += (o << 4) + p ^ o + u ^ (o >>> 5) + c,
            r = (4294967295 & r) >>> 0;
        var d = new Array(8);
        return i(d, 0, o),
        i(d, 4, r),
        d
    }
    function d(t) {
        for (var e = 16, o = n(t, 0, 4), r = n(t, 4, 4), a = n(m, 0, 4), s = n(m, 4, 4), p = n(m, 8, 4), c = n(m, 12, 4), u = 3816266640, l = 2654435769; e-- > 0; )
            r -= (o << 4) + p ^ o + u ^ (o >>> 5) + c,
            r = (4294967295 & r) >>> 0,
            o -= (r << 4) + a ^ r + u ^ (r >>> 5) + s,
            o = (4294967295 & o) >>> 0,
            u -= l,
            u = (4294967295 & u) >>> 0;
        var d = new Array(8);
        return i(d, 0, o),
        i(d, 4, r),
        d
    }
    function h() {
        for (var t = ($.length,
        0); 8 > t; t++)
            w[t] ^= $[v + t];
        return w = d(w),
        v += 8,
        g = 0,
        !0
    }
    function f(t, e) {
        var n = [];
        if (e)
            for (var i = 0; i < t.length; i++)
                n[i] = 255 & t.charCodeAt(i);
        else
            for (var o = 0, i = 0; i < t.length; i += 2)
                n[o++] = parseInt(t.substr(i, 2), 16);
        return n
    }
var m = "", g = 0, _ = [], w = [], v = 0, y = 0, b = [], $ = [], k = !0;
TEA = {
        encrypt: function(t, e) {
            var n = f(t, e)
              , i = p(n);
            return o(i)
        },
        enAsBase64: function(t, e) {
            for (var n = f(t, e), i = p(n), o = "", r = 0; r < i.length; r++)
                o += String.fromCharCode(i[r]);
            return S.encode(o)
        },
        decrypt: function(t) {
            var e = f(t, !1)
              , n = c(e);
            return o(n)
        },
        initkey: function(t, e) {
            m = f(t, e)
        },
        bytesToStr: r,
        strToBytes: a,
        bytesInStr: o,
        dataFromStr: f
    }
var S = {};
S.PADCHAR = "=",
S.ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
S.getbyte = function(t, e) {
    var n = t.charCodeAt(e);
    if (n > 255)
        throw "INVALID_CHARACTER_ERR: DOM Exception 5";
    return n
}
,
S.encode = function(t) {
    if (1 != arguments.length)
        throw "SyntaxError: Not enough arguments";
    var e, n, i = S.PADCHAR, o = S.ALPHA, r = S.getbyte, a = [];
    t = "" + t;
    var s = t.length - t.length % 3;
    if (0 == t.length)
        return t;
    for (e = 0; s > e; e += 3)
        n = r(t, e) << 16 | r(t, e + 1) << 8 | r(t, e + 2),
        a.push(o.charAt(n >> 18)),
        a.push(o.charAt(n >> 12 & 63)),
        a.push(o.charAt(n >> 6 & 63)),
        a.push(o.charAt(63 & n));
    switch (t.length - s) {
    case 1:
        n = r(t, e) << 16,
        a.push(o.charAt(n >> 18) + o.charAt(n >> 12 & 63) + i + i);
        break;
    case 2:
        n = r(t, e) << 16 | r(t, e + 1) << 8,
        a.push(o.charAt(n >> 18) + o.charAt(n >> 12 & 63) + o.charAt(n >> 6 & 63) + i)
    }
    return a.join("")
}