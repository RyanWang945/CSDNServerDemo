package com.ryan.encrypt.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvN5ehwl3QsS4aie6YVuX" +
			"CeZCoPQ4c/tLVryTvnIGD14MySjPWBqPT9NPNrPaWxPcqxyZyDSrIsugLOayIhtG" +
			"ziBqCT5x/NnVWDDYi6zuET6PJ65toyKikHfr6Zshb79Q0IaCzGv73f4+vkWB19zh" +
			"5C0c/jH5PpRSFYyIiudqLdRtdbQB5Y1k8fv4eQOfIb6uF1cBjzO2iVKzJkuYZ17J" +
			"YEZ2/8bpcMsgUhKwZ8gxjoC+Zjx7jD3168cCLSy+7IQ+PL+QfBJVoHmpAM1Lz9TI" +
			"2mKwW+hTSMkjsEiyLFt81vCGP9+CDGoTgL8qkv7aezuhq0oJU8LoOSVRBOqs6APO" +
			"CQIDAQAB";

	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC83l6HCXdCxLhq"+
			"J7phW5cJ5kKg9Dhz+0tWvJO+cgYPXgzJKM9YGo9P0082s9pbE9yrHJnINKsiy6As" +
			"5rIiG0bOIGoJPnH82dVYMNiLrO4RPo8nrm2jIqKQd+vpmyFvv1DQhoLMa/vd/j6+" +
			"RYHX3OHkLRz+Mfk+lFIVjIiK52ot1G11tAHljWTx+/h5A58hvq4XVwGPM7aJUrMm" +
			"S5hnXslgRnb/xulwyyBSErBnyDGOgL5mPHuMPfXrxwItLL7shD48v5B8ElWgeakA" +
			"zUvP1MjaYrBb6FNIySOwSLIsW3zW8IY/34IMahOAvyqS/tp7O6GrSglTwug5JVEE" +
			"6qzoA84JAgMBAAECggEASIxKVyilrPoPtIcqKreZ3u2Z7mWhQbvnhpvNTLxT45dG" +
			"5gZNDxS0s/8BUIvpdk681uWXlLXWfvkkv9ql1kAQBKGi2YYpFu097DBnHdABapJT" +
			"PvTbnGxzcXb/Ia67O0eL7W2d1JQuHWnKBCCC7b4k7xb9Kg9cJFOo7CodUO3vdUaX" +
			"3NhB4nsgHFH8Fzn/VcfZV/yZ/ucJE2hdgFM6+fBm3V+6Ct6oaen1BJaKw9fNyboW" +
			"9JSDEujWRNmc3BC22/yxJkxp0HXplmzh3rwxnA+7QZtThOLHft0mwIwEDkqy6uNg" +
			"KJxjXOGv3nUNTkLtaTe7JDkiXSkFYfIdlblI6ifx4QKBgQD3ryoFn/pJVWSQMmjo" +
			"YjO7ax1u7w8QZ7XruGllrVigDI1l24stJ/dDRzA94CTC3QstI8SwaJ5q7mFvpRY5" +
			"iolvS7l90xuOVk4gXMBoDKkvfFXgXzO8t+2Q/rwJ8dgK0Rzs6MZup94SWcZ3UVrn" +
			"6LoBG8cykrArGA1DQ54aIFvUVQKBgQDDNa/4YS8ZA5WX0JRXm3NIa9qxr/nwPXYg" +
			"cY6yEvhswFL+hppwRh5qKlpEimtC6MfzltZUgsmm4IRy+DfYqVoVGVv7OKM1Er42" +
			"LB0oLaYVilIntGYzLO2oZdJUnTVQKdfMY83NrhR8+KGSnsSNq/qThixFwihKwaFi" +
			"OOaRzzFm5QKBgCc3xBaZ6x57d+3hHX1WN0zAvCtZjPATC9okHAQue270Ldtn9NSU" +
			"lHFBr2DMweVc7K0cEcPA/Px/fEwaPrejuPk3a0qCB8X04m/7X7fZ8VwEevCJAn8q" +
			"PGKOAyYr9gJYdBULEoho6h/LuUIhTpyT3Afa5yKZ2W0pZMg2x6zpXMD1AoGAOYm4" +
			"jXhvvmq9DBtxrgTmzQDlXBxnijVDfTqbecd3q+5gSB96o/829cn4A13dUcJ1NUpP" +
			"cGeAcoyY3Z5mL5lzMObhosHF8V3N7P/BtFcILi8wGf3lZc4nWsxwW3pe/Xrn3ZnJ" +
			"ToeyCEfsr9/WRX/cguE4aXNaFvbNz7q/01iD0jUCgYEAg/HUnQtIaaoej3IeyVkG" +
			"FBhq8rBUl1aye+BZJsE0jFZhovudpNA0KTc3t6Te0UuxB0hcq6hyivYrBWJZFWNU" +
			"uI11LXI8YH35Lex9TRqvN1cBqLOOXlCktEw917FLjih8P93br/V55skuEGqz27Tc" +
			"VvwUG+bh1Nb3umy+Y3Qbwt0=";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
