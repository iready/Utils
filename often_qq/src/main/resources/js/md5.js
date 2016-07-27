function md5(t) {
	return hex_md5(t)
}
function hex_md5(t) {
	return binl2hex(core_md5(str2binl(t), t.length * chrsz))
}
function str_md5(t) {
	return binl2str(core_md5(str2binl(t), t.length * chrsz))
}
function hex_hmac_md5(t, e) {
	return binl2hex(core_hmac_md5(t, e))
}
function b64_hmac_md5(t, e) {
	return binl2b64(core_hmac_md5(t, e))
}
function str_hmac_md5(t, e) {
	return binl2str(core_hmac_md5(t, e))
}
function core_md5(t, e) {
	t[e >> 5] |= 128 << e % 32, t[(e + 64 >>> 9 << 4) + 14] = e;
	for ( var n = 1732584193, i = -271733879, o = -1732584194, r = 271733878, a = 0; a < t.length; a += 16) {
		var s = n, p = i, c = o, u = r;
		n = md5_ff(n, i, o, r, t[a + 0], 7, -680876936), r = md5_ff(r, n, i, o,
				t[a + 1], 12, -389564586), o = md5_ff(o, r, n, i, t[a + 2], 17,
				606105819), i = md5_ff(i, o, r, n, t[a + 3], 22, -1044525330),
				n = md5_ff(n, i, o, r, t[a + 4], 7, -176418897), r = md5_ff(r,
						n, i, o, t[a + 5], 12, 1200080426), o = md5_ff(o, r, n,
						i, t[a + 6], 17, -1473231341), i = md5_ff(i, o, r, n,
						t[a + 7], 22, -45705983), n = md5_ff(n, i, o, r,
						t[a + 8], 7, 1770035416), r = md5_ff(r, n, i, o,
						t[a + 9], 12, -1958414417), o = md5_ff(o, r, n, i,
						t[a + 10], 17, -42063), i = md5_ff(i, o, r, n,
						t[a + 11], 22, -1990404162), n = md5_ff(n, i, o, r,
						t[a + 12], 7, 1804603682), r = md5_ff(r, n, i, o,
						t[a + 13], 12, -40341101), o = md5_ff(o, r, n, i,
						t[a + 14], 17, -1502002290), i = md5_ff(i, o, r, n,
						t[a + 15], 22, 1236535329), n = md5_gg(n, i, o, r,
						t[a + 1], 5, -165796510), r = md5_gg(r, n, i, o,
						t[a + 6], 9, -1069501632), o = md5_gg(o, r, n, i,
						t[a + 11], 14, 643717713), i = md5_gg(i, o, r, n,
						t[a + 0], 20, -373897302), n = md5_gg(n, i, o, r,
						t[a + 5], 5, -701558691), r = md5_gg(r, n, i, o,
						t[a + 10], 9, 38016083), o = md5_gg(o, r, n, i,
						t[a + 15], 14, -660478335), i = md5_gg(i, o, r, n,
						t[a + 4], 20, -405537848), n = md5_gg(n, i, o, r,
						t[a + 9], 5, 568446438), r = md5_gg(r, n, i, o,
						t[a + 14], 9, -1019803690), o = md5_gg(o, r, n, i,
						t[a + 3], 14, -187363961), i = md5_gg(i, o, r, n,
						t[a + 8], 20, 1163531501), n = md5_gg(n, i, o, r,
						t[a + 13], 5, -1444681467), r = md5_gg(r, n, i, o,
						t[a + 2], 9, -51403784), o = md5_gg(o, r, n, i,
						t[a + 7], 14, 1735328473), i = md5_gg(i, o, r, n,
						t[a + 12], 20, -1926607734), n = md5_hh(n, i, o, r,
						t[a + 5], 4, -378558), r = md5_hh(r, n, i, o, t[a + 8],
						11, -2022574463), o = md5_hh(o, r, n, i, t[a + 11], 16,
						1839030562), i = md5_hh(i, o, r, n, t[a + 14], 23,
						-35309556), n = md5_hh(n, i, o, r, t[a + 1], 4,
						-1530992060), r = md5_hh(r, n, i, o, t[a + 4], 11,
						1272893353), o = md5_hh(o, r, n, i, t[a + 7], 16,
						-155497632), i = md5_hh(i, o, r, n, t[a + 10], 23,
						-1094730640), n = md5_hh(n, i, o, r, t[a + 13], 4,
						681279174), r = md5_hh(r, n, i, o, t[a + 0], 11,
						-358537222), o = md5_hh(o, r, n, i, t[a + 3], 16,
						-722521979), i = md5_hh(i, o, r, n, t[a + 6], 23,
						76029189), n = md5_hh(n, i, o, r, t[a + 9], 4,
						-640364487), r = md5_hh(r, n, i, o, t[a + 12], 11,
						-421815835), o = md5_hh(o, r, n, i, t[a + 15], 16,
						530742520), i = md5_hh(i, o, r, n, t[a + 2], 23,
						-995338651), n = md5_ii(n, i, o, r, t[a + 0], 6,
						-198630844), r = md5_ii(r, n, i, o, t[a + 7], 10,
						1126891415), o = md5_ii(o, r, n, i, t[a + 14], 15,
						-1416354905), i = md5_ii(i, o, r, n, t[a + 5], 21,
						-57434055), n = md5_ii(n, i, o, r, t[a + 12], 6,
						1700485571), r = md5_ii(r, n, i, o, t[a + 3], 10,
						-1894986606), o = md5_ii(o, r, n, i, t[a + 10], 15,
						-1051523), i = md5_ii(i, o, r, n, t[a + 1], 21,
						-2054922799), n = md5_ii(n, i, o, r, t[a + 8], 6,
						1873313359), r = md5_ii(r, n, i, o, t[a + 15], 10,
						-30611744), o = md5_ii(o, r, n, i, t[a + 6], 15,
						-1560198380), i = md5_ii(i, o, r, n, t[a + 13], 21,
						1309151649), n = md5_ii(n, i, o, r, t[a + 4], 6,
						-145523070), r = md5_ii(r, n, i, o, t[a + 11], 10,
						-1120210379), o = md5_ii(o, r, n, i, t[a + 2], 15,
						718787259), i = md5_ii(i, o, r, n, t[a + 9], 21,
						-343485551), n = safe_add(n, s), i = safe_add(i, p),
				o = safe_add(o, c), r = safe_add(r, u)
	}
	return 16 == mode ? Array(i, o) : Array(n, i, o, r)
}
function md5_cmn(t, e, n, i, o, r) {
	return safe_add(bit_rol(safe_add(safe_add(e, t), safe_add(i, r)), o), n)
}
function md5_ff(t, e, n, i, o, r, a) {
	return md5_cmn(e & n | ~e & i, t, e, o, r, a)
}
function md5_gg(t, e, n, i, o, r, a) {
	return md5_cmn(e & i | n & ~i, t, e, o, r, a)
}
function md5_hh(t, e, n, i, o, r, a) {
	return md5_cmn(e ^ n ^ i, t, e, o, r, a)
}
function md5_ii(t, e, n, i, o, r, a) {
	return md5_cmn(n ^ (e | ~i), t, e, o, r, a)
}
function core_hmac_md5(t, e) {
	var n = str2binl(t);
	n.length > 16 && (n = core_md5(n, t.length * chrsz));
	for ( var i = Array(16), o = Array(16), r = 0; 16 > r; r++)
		i[r] = 909522486 ^ n[r], o[r] = 1549556828 ^ n[r];
	var a = core_md5(i.concat(str2binl(e)), 512 + e.length * chrsz);
	return core_md5(o.concat(a), 640)
}
function safe_add(t, e) {
	var n = (65535 & t) + (65535 & e), i = (t >> 16) + (e >> 16) + (n >> 16);
	return i << 16 | 65535 & n
}
function bit_rol(t, e) {
	return t << e | t >>> 32 - e
}
function str2binl(t) {
	for ( var e = Array(), n = (1 << chrsz) - 1, i = 0; i < t.length * chrsz; i += chrsz)
		e[i >> 5] |= (t.charCodeAt(i / chrsz) & n) << i % 32;
	return e
}
function binl2str(t) {
	for ( var e = "", n = (1 << chrsz) - 1, i = 0; i < 32 * t.length; i += chrsz)
		e += String.fromCharCode(t[i >> 5] >>> i % 32 & n);
	return e
}
function binl2hex(t) {
	for ( var e = hexcase ? "0123456789ABCDEF" : "0123456789abcdef", n = "", i = 0; i < 4 * t.length; i++)
		n += e.charAt(t[i >> 2] >> i % 4 * 8 + 4 & 15)
				+ e.charAt(t[i >> 2] >> i % 4 * 8 & 15);
	return n
}
function binl2b64(t) {
	for ( var e = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", n = "", i = 0; i < 4 * t.length; i += 3)
		for ( var o = (t[i >> 2] >> 8 * (i % 4) & 255) << 16
				| (t[i + 1 >> 2] >> 8 * ((i + 1) % 4) & 255) << 8
				| t[i + 2 >> 2] >> 8 * ((i + 2) % 4) & 255, r = 0; 4 > r; r++)
			n += 8 * i + 6 * r > 32 * t.length ? b64pad : e
					.charAt(o >> 6 * (3 - r) & 63);
	return n
}
function hexchar2bin(str) {
	for ( var arr = [], i = 0; i < str.length; i += 2)
		arr.push("\\x" + str.substr(i, 2));
	return arr = arr.join(""), eval("var temp = '" + arr + "'"), temp
}
function __monitor(t, e) {
	if (!(Math.random() > (e || 1)))
		try {
			var n = location.protocol
					+ "//ui.ptlogin2.qq.com/cgi-bin/report?id=" + t, i = document
					.createElement("img");
			i.src = n
		} catch (o) {
		}
}
function getEncryption(t, e, n, i) {
	n = n || "", t = t || "";
	for ( var o = i ? t : md5(t), r = hexchar2bin(o), a = md5(r + e), s = $pt.RSA
			.rsa_encrypt(r), p = (s.length / 2).toString(16), c = TEA
			.strToBytes(n.toUpperCase(), !0), u = Number(c.length / 2)
			.toString(16); u.length < 4;)
		u = "0" + u;
	for (; p.length < 4;)
		p = "0" + p;
	TEA.initkey(a);
	var l = TEA.enAsBase64(p + s + TEA.strToBytes(e) + u + c);
	return TEA.initkey(""), setTimeout(function() {
		__monitor(488358, 1)
	}, 0), l.replace(/[\/\+=]/g, function(t) {
		return {
			"/" : "-",
			"+" : "*",
			"=" : "_"
		}[t]
	})
}
function getRSAEncryption(t, e, n) {
	var i = n ? t : md5(t), o = i + e.toUpperCase(), r = $.RSA.rsa_encrypt(o);
	return r
}
var hexcase = 1, b64pad = "", chrsz = 8, mode = 32;