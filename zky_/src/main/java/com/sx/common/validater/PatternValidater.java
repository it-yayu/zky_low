package com.sx.common.validater;

public class PatternValidater {
	//严格非法字符控制
	public  static final String full = "^[^\\[\\]:?\"{}`=^&!*|;$%@\'<>+\\r\\n,\\\\../ ]*$";
	//标准非法字符控制
	public  static final String normal = "^[^|;$%'<>`+\\r\\n,\\\\ *]*$";
	//身份证号码
	public  static final String sfzhm = "^([1-9]\\d{5}(19|20)\\d{2}[01]\\d[0-3]\\d\\d{3}[0-9xX])?$";
	//手机号
	public  static final String sjh = "^(1\\d{10})?$";
	//邮编
	public  static final String yb = "^([0-9]{6})?$";
	//邮箱
	public  static final String email = "^([-\\w]?\\w+([-+.]\\w+)*[-\\w]?@[-\\w]?\\w+([-.]\\w+)*[-\\w]?\\.[-\\w]?\\w+([-.]\\w+)*[-\\w]?)?$";
	//联系电话
	public  static final String lxdh = "^((1\\d{10})|((\\d{3,4}-)?\\d{6,8}(-\\d{1,4})?))(、((1\\d{10})|((\\d{3,4}-)?\\d{6,8}(-\\d{1,4})?)))*$";
	//网址
	public  static final String url = "^http:\\/\\/[A-Za-z0-9-]+\\.[A-Za-z0-9-]+[\\/=\\?%\\-&_~`@[\\-]\\':+!-]*([^<>\\-])*$";
	
	//英文
	public  static final String english = "^[A-Za-z]*$";
	//中文
	public  static final String chinese = "^[Α-￥]*$";
	//QQ号码[10位以内]
	public  static final String qq = "^([1-9]\\d{4,9})?$";
	//正整数(包含0)
	public  static final String number = "^(\\d*)?$";
	//正整数(不包含0)
	public  static final String num = "^([1-9]\\d*)?$";
	//整数(正和负)
	public  static final String integer = "^[-\\+]?\\d*$";
	//浮点数(正和负)
	public  static final String Double = "^[-\\+]?\\d+(\\.\\d+)?$";
	//正浮点数(包含0)
	public  static final String posdouble = "^(\\d+(\\.\\d+)?)?$";
	//组织机构代码[含-]
	public  static final String zzjgdm = "^([a-zA-Z0-9]{8}-[a-zA-Z0-9])?$";
	//组织机构代码[不含-]
	public  static final String zzjgm = "^([a-zA-Z0-9]{9})*$";
	//QQ号码[5位以上]
	public  static final String longqq = "^([1-9]\\d{4,})?$";
	public  static final String dwqq ="^([1-9]\\d{4,})?$";
	//发文字号
	public  static final String fzwh = "^[\\u4e00-\\u9fa5\\d]*[\\【\\（\\(\\〔\\[]\\d{4}[\\]\\】\\）\\〕\\)\\]][\\u4e00-\\u9fa5\\d]*$";
	//IP地址
	public  static final String ip = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	//双字节字符(包括汉字)
	public  static final String szjzf = "[^\\x00-\\xff]";
	//日期
	public  static final String rq = "^((\\d{4})(0\\d{1}|1[0-2]))?$";
	//日期
	public  static final String _rq = "^((\\d{4})(0\\d{1}|1[0-2])(0\\d{1}|[12]\\d{1}|3[01]))?$";
	//用户名
	public  static final String yhm = "^[a-zA-Z0-9]*$";
	//个人企业用户
	public  static final String yhzh = "^[a-zA-Z0-9^_\\@\\.x22]{8,48}$";
	//密码
	public  static final String mm = "^[a-zA-Z0-9]*$";
	//6位日期
	public  static final String nyrq = "^(\\d{4}-\\d{1,2})?$";
	//多文本
	public  static final String less = "^[^|$'<>`()\\\\*]*$";
	public  static final String year = "^(\\d{4})?$";
	//导入日期
	public static final String drrq = "^[12]\\d{3}-(0[1-9]|1[0-2])-([0-2]\\d|3[01])$";
	//字母数字下划线
	public static final String zmszxhx = "^[a-zA-Z0-9_]*$";
	//数字
	public static final String sz = "^[0-9]*$";
	// 是1否0
	public static final String s1f0 = "^[01]?$";
	//时间：14位：20171210242060
	public static final String sj14 = "^((\\d{4})(0\\d{1}|1[0-2])(0\\d{1}|[12]\\d{1}|3[01])(0\\d{1}|1\\d{1}|2[0-3])[0-5]\\d{1}([0-5]\\d{1}))?$";
	//8位日期校验 2018-06-01
	public static final String rq_8="^((\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]))?$";
	
	public static void main(String[] args) {
		//严格非法字符控制
		String full = "^[^\\[\\]:?\"{}`=^&!*|;$%@\'<>+\\r\\n,\\\\../ ]*$";
		//标准非法字符控制
		String normal = "^[^|;$%'<>`+\\r\\n,\\\\ *]*$";
		//身份证号码
		String sfzhm = "^([1-9]\\d{5}(19|20)\\d{2}[01]\\d[0-3]\\d\\d{3}[0-9xX])?$";
		//手机号 11位数字
		String sjh = "^(1\\d{10})?$";
		//邮编   6位数字
		String yb = "^([0-9]{6})?$";
		//邮箱   大小写字母 或 数字  或下划线开头 13526259628@163.com
		String email = "^([-\\w]?\\w+([-+.]\\w+)*[-\\w]?@[-\\w]?\\w+([-.]\\w+)*[-\\w]?\\.[-\\w]?\\w+([-.]\\w+)*[-\\w]?)?$";
		//联系电话  5-32位数字
		String lxdh = "^([0-9]{5,32})?";
		//8位日期校验 2018-06-01
		String rq_8="^((\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]))?$";
		// 是1否0
		String s1f0 = "^[01]?$";
		//多文本
		String less = "^[^|$'<>`()\\\\*]*$";
		String rq_6 = "^(\\d{4}-\\d{1,2})?$";
		
		String a="2018-06";
		System.out.println(a.matches(rq_6));
	}
}