function xq_a2(o) {
	return (o.length / 2).toString(16);
}
function xq_a3(c) {
	return Number(c.length / 2).toString(16);
}
function xq_a4(l, t) {
	return l.replace(/[\/\+=]/g, function(t) {
		return {
			"/" : "-",
			"+" : "*",
			"=" : "_"
		}[t]
	});
}
function getGTK(str) {
	var hash = 5381;
	for ( var i = 0, len = str.length; i < len; ++i) {
		hash += (hash << 5) + str.charAt(i).charCodeAt();
	}
	return  new String(hash & 0x7fffffff);
}
