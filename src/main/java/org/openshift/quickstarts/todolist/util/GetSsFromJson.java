package org.openshift.quickstarts.todolist.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.openshift.quickstarts.todolist.vo.ConfVO;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GetSsFromJson {
	
	public static List<ConfVO> getConfList() {
		List<ConfVO> list = new ArrayList<ConfVO>();
		try {
//			String jsonStr = HttpUtil.get("https://free-ss.site/ss.json?_="+System.currentTimeMillis());
//			String jsonStr = "{\"data\": [[\"10/10/10/10\", \"60.149.166.211\", \"13437\", \"chacha20\", \"29816846\", \"15:15:02\", \"JP\"], [\"10/10/10/10\", \"191.41.17.236\", \"19843\", \"rc4-md5\", \"86231655\", \"15:15:02\", \"SG\"], [\"10/10/10/10\", \"124.4.66.156\", \"16202\", \"rc4-md5\", \"11317521\", \"15:15:02\", \"JP\"], [\"10/10/10/10\", \"133.24.127.161\", \"15653\", \"chacha20\", \"12767086\", \"15:15:02\", \"SG\"], [\"10/10/10/10\", \"156.230.218.209\", \"15378\", \"rc4-md5\", \"52566129\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"56.34.162.167\", \"18308\", \"chacha20\", \"29407582\", \"15:15:02\", \"JP\"], [\"10/10/10/10\", \"124.114.243.188\", \"16718\", \"aes-256-cfb\", \"11809647\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"50.137.54.203\", \"16926\", \"chacha20\", \"92659555\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"30.250.11.62\", \"19311\", \"rc4-md5\", \"25067501\", \"15:15:02\", \"SG\"], [\"10/10/10/10\", \"77.227.61.49\", \"18031\", \"aes-256-cfb\", \"33102413\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"78.142.156.86\", \"16918\", \"aes-256-cfb\", \"80099411\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"33.176.48.87\", \"18858\", \"aes-256-cfb\", \"89601381\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"223.150.207.200\", \"13960\", \"aes-256-cfb\", \"92012823\", \"15:15:02\", \"SG\"], [\"10/10/10/10\", \"203.215.81.244\", \"13440\", \"aes-256-cfb\", \"20747923\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"35.85.99.139\", \"19325\", \"aes-256-cfb\", \"79074733\", \"15:15:02\", \"JP\"], [\"10/10/10/10\", \"114.140.150.55\", \"15614\", \"aes-256-cfb\", \"92718859\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"170.186.58.111\", \"11166\", \"aes-256-cfb\", \"59327307\", \"15:15:02\", \"SG\"], [\"10/10/10/10\", \"29.112.235.179\", \"18643\", \"aes-256-cfb\", \"97055003\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"156.160.69.212\", \"19247\", \"aes-256-cfb\", \"50197131\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"62.94.119.214\", \"19516\", \"aes-256-cfb\", \"74712547\", \"15:15:02\", \"US\"], [\"10/10/10/10\", \"19.239.152.167\", \"17973\", \"chacha20\", \"99773821\", \"15:15:02\", \"US\"]]}";
			String jsonStr = getSsJson();
			JSONObject jo = JSONObject.parseObject(jsonStr);
			JSONArray data = jo.getJSONArray("data");
			for(int i=0;i<data.size();i++){
				JSONArray ja = data.getJSONArray(i);
				//["10/10/10/10", "60.149.166.211", "13437", "chacha20", "29816846", "15:15:02", "JP"]
				if(ja.size()!=7){
					continue;
				}
				ConfVO vo = new ConfVO();
				vo.setServer(ja.getString(1));
				vo.setServerPort(ja.getIntValue(2));
				vo.setMethod(ja.getString(3));
				vo.setPassword(ja.getString(4));
				vo.setRemarks(ja.getString(6));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(ExceptionUtil.getExceptionMessage(e));
		}

		return list;
	}
	
	public static String getSsJson() throws Exception{
//		String str = "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";		
		String[] strs = getABC();		
		String a = strs[0];
		String b = strs[1];
		String c = strs[2];
		String setCookie = strs[3];
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		String yi = HttpUtil.get("https://free-ss.site/ss.json?_="+currentTimeMillis);
		
		String url = "https://free-ss.site/data.php";
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String> formParams = new HashMap<String, String>();		
		formParams.put("a", a);
		formParams.put("b", b);		
		formParams.put("c", c);
		Map<String, String> headers = new HashMap<String, String>();
		
//		:authority: free-ss.site
//		:method: POST
//		:path: /data.php
//		:scheme: https
//		accept: */*
//		accept-encoding: gzip, deflate, br
//		accept-language: zh-CN,zh;q=0.9,zh-TW;q=0.8
//		cache-control: no-cache
//		content-length: 56
//		content-type: application/x-www-form-urlencoded; charset=UTF-8
//		cookie: __cfduid=db74c08218e8bbba70ce90c54439f77ef1538468968
//		origin: https://free-ss.site
//		pragma: no-cache
//		referer: https://free-ss.site/
//		user-agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36
//		x-requested-with: XMLHttpRequest
		
//		headers.put(":authority", "free-ss.site");
//		headers.put(":method", "POST");
//		headers.put(":path", "/data.php");
//		headers.put(":scheme", "https");
		headers.put("origin", "https://free-ss.site");
		headers.put("referer", "https://free-ss.site/");
		headers.put("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		headers.put("content-length", "56");
		headers.put("accept-encoding", "gzip, deflate, br");
		headers.put("accept", "*/*");
		headers.put("accept-language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");
		headers.put("cache-control", "no-cache");
		headers.put("pragma", "no-cache");

		headers.put("x-requested-with", "XMLHttpRequest");
		headers.put("Cookie", setCookie);
		String str = HttpUtil.post(url, params, formParams, headers);
		System.out.println("最终返回："+str);
//		String a = "bfd153389ce78a61";
//		String b = "c17b952ea46fd803";
//		String c = "10b5b4075947ef97";
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKeySpec skeySpec = new SecretKeySpec(a.getBytes("ASCII"), "AES");
//		IvParameterSpec iv = new IvParameterSpec(b.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted1 = Base64.getDecoder().decode(str);// 先用base64解密
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		System.out.println(originalString);
		return originalString;
	}
	
	public static String[] getABC() throws Exception{
		String[] result = new String[4];
		String url = "https://free-ss.site";
//		Map<String, String> params = new HashMap<>();
//		Map<String, String> formParams = new HashMap<>();
//		Map<String, String> headers = new HashMap<>();
//		headers.put(":authority", "free-ss.site");
//		headers.put(":method", "POST");
//		headers.put(":path", "/data.php");
//		headers.put(":scheme", "https");
//		headers.put("origin", "https://free-ss.site");
//		headers.put("referer", "https://free-ss.site/");
//		headers.put("c", "10b5b4075947ef97");
		HttpResult hr = HttpUtil.getForSetCookies(url);
		String setCookie = hr.getSetCookie();
		String str = hr.getContent();
		str = str.replaceAll("\\n( )*//.*\\n", "");
//		System.out.println(str);
		str = str.replaceAll("/\\*([^\\*]*)\\*/", "");
		System.out.println("精简后html：");
		System.out.println(str);
		int begin = str.indexOf("else{var");
		int end = str.indexOf("}", begin);
		String useStr = str.substring(begin, end);
		int aBegin = useStr.indexOf("a='")+3;
		int aEnd = useStr.indexOf("'",aBegin);
		int bBegin = useStr.indexOf("b='")+3;
		int bEnd = useStr.indexOf("'",bBegin);
		int cBegin = useStr.indexOf("c='")+3;
		int cEnd = useStr.indexOf("'",cBegin);
		
		String a = useStr.substring(aBegin, aEnd);
		String b = useStr.substring(bBegin, bEnd);
		String c = useStr.substring(cBegin, cEnd);
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(setCookie);
		
		setCookie = setCookie.substring(0, setCookie.indexOf(";"));
		System.out.println(setCookie);
		result[0] = a;
		result[1] = b;
		result[2] = c;
		result[3] = setCookie;
		return result;
	}
	
	public static void test(){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
//            engine.eval("function add(a,b){return a1(a,b);}function a1(a,b){return a+1;}");
//            engine.eval("function encc(r) {	var n = str2bin(r),		t = al(n, r.length),		o = new Array(8);	o[0] = 1937774191, o[1] = 1226093241, o[2] = 388252375, o[3] = 3666478592, o[4] = 2842636476, o[5] = 372324522, o[6] = 3817729613, o[7] = 2969243214;	for (var a = 0; a < t; a++) o = zip(o, n, a);	return word2str(o, \"\").substr(0, 16)}function word2str(r, n) {	for (var t = Array(8).join(\"0\"), o = 0; o < r.length; o++) r[o] = (t + (r[o] >>> 0).toString(16)).slice(-8);	return r.join(n)}function str2bin(r) {	for (var n = new Array(r.length >> 2), t = 0; t < 8 * r.length; t += 8) n[t >> 5] |= (255 & r.charCodeAt(t / 8)) << 24 - t % 32;	return n}function al(r, n) {	r[n >> 2] |= 128 << 24 - n % 4 * 8;	for (var t = 1 + (n + 8 >> 6), o = 16 * t, a = 1 + (n >> 2); a < o; a++) r[a] = 0;	return r[o - 1] = 8 * n, t}function p0(r) {	return r ^ br(r, 9) ^ br(r, 17)}function p1(r) {	return r ^ br(r, 15) ^ br(r, 23)}function br(r, n) {	return r << n | r >>> 32 - n}function zip(r, n, o) {	for (var a = new Array(68), u = new Array(64), f = 0; f < 68; f++) a[f] = f < 16 ? n[16 * o + f] : p1(a[f - 16] ^ a[f - 9] ^ br(a[f - 3], 15)) ^ br(a[f - 13], 7) ^ a[f - 6];	for (f = 0; f < 64; f++) u[f] = a[f] ^ a[f + 4];	var e, i, c, b, v = r[0],		g = r[1],		l = r[2],		d = r[3],		s = r[4],		h = r[5],		p = r[6],		w = r[7];	for (f = 0; f < 64; f++) i = (e = br(aa(br(v, 12), s, br(t(f), f)), 7)) ^ br(v, 12), c = aa(ff(v, g, l, f), d, i, u[f]), b = aa(gg(s, h, p, f), w, e, a[f]), d = l, l = br(g, 9), g = v, v = c, w = p, p = br(h, 19), h = s, s = p0(b);	return r[0] ^= v, r[1] ^= g, r[2] ^= l, r[3] ^= d, r[4] ^= s, r[5] ^= h, r[6] ^= p, r[7] ^= w, r}function t(r) {	return 0 <= r && r < 16 ? 2043430169 : r < 64 ? 2055708042 : void 0}function ff(r, n, t, o) {	return 0 <= o && o < 16 ? r ^ n ^ t : o < 64 ? r & n | r & t | n & t : void 0}function gg(r, n, t, o) {	return 0 <= o && o < 16 ? r ^ n ^ t : o < 64 ? r & n | ~r & t : void 0}function sa(r, n) {	var t = (65535 & r) + (65535 & n);	return (r >> 16) + (n >> 16) + (t >> 16) << 16 | 65535 & t}function aa() {	for (var r = 0, n = 0; n < arguments.length; n++) r = sa(r, arguments[n]);	return r}");
            engine.eval("function encc(r){var n=str2bin(r),t=al(n,r.length),o=new Array(8);o[0]=1937774191,o[1]=1226093241,o[2]=388252375,o[3]=3666478592,o[4]=2842636476,o[5]=372324522,o[6]=3817729613,o[7]=2969243214;for(var a=0;a<t;a++)o=zip(o,n,a);return word2str(o,\"\").substr(0,16)}function word2str(r,n){for(var t=Array(8).join(\"0\"),o=0;o<r.length;o++)r[o]=(t+(r[o]>>>0).toString(16)).slice(-8);return r.join(n)}function str2bin(r){for(var n=new Array(r.length>>2),t=0;t<8*r.length;t+=8)n[t>>5]|=(255&r.charCodeAt(t/8))<<24-t%32;return n}function al(r,n){r[n>>2]|=128<<24-n%4*8;for(var t=1+(n+8>>6),o=16*t,a=1+(n>>2);a<o;a++)r[a]=0;return r[o-1]=8*n,t}function p0(r){return r^br(r,9)^br(r,17)}function p1(r){return r^br(r,15)^br(r,23)}function br(r,n){return r<<n|r>>>32-n}function zip(r,n,o){for(var a=new Array(68),u=new Array(64),f=0;f<68;f++)a[f]=f<16?n[16*o+f]:p1(a[f-16]^a[f-9]^br(a[f-3],15))^br(a[f-13],7)^a[f-6];for(f=0;f<64;f++)u[f]=a[f]^a[f+4];var e,i,c,b,v=r[0],g=r[1],l=r[2],d=r[3],s=r[4],h=r[5],p=r[6],w=r[7];for(f=0;f<64;f++)i=(e=br(aa(br(v,12),s,br(t(f),f)),7))^br(v,12),c=aa(ff(v,g,l,f),d,i,u[f]),b=aa(gg(s,h,p,f),w,e,a[f]),d=l,l=br(g,9),g=v,v=c,w=p,p=br(h,19),h=s,s=p0(b);return r[0]^=v,r[1]^=g,r[2]^=l,r[3]^=d,r[4]^=s,r[5]^=h,r[6]^=p,r[7]^=w,r}function t(r){return 0<=r&&r<16?2043430169:r<64?2055708042:void 0}function ff(r,n,t,o){return 0<=o&&o<16?r^n^t:o<64?r&n|r&t|n&t:void 0}function gg(r,n,t,o){return 0<=o&&o<16?r^n^t:o<64?r&n|~r&t:void 0}function sa(r,n){var t=(65535&r)+(65535&n);return(r>>16)+(n>>16)+(t>>16)<<16|65535&t}function aa(){for(var r=0,n=0;n<arguments.length;n++)r=sa(r,arguments[n]);return r}");
        	if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("encc","ac840f362eb1957d"));
            }
            }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws Exception {
//		String ssJson = getSsJson();
//		System.out.println("结果：");
//		System.out.println(ssJson);
		test();
		
	}

}
