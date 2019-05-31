package org.tang.wechat.api.aes;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class AesException extends Exception {
	private static Logger logger = LoggerFactory.getLogger(AesException.class);

	public final static int OK = 0;
	public final static int VALIDATE_SIGNATUR_EERROR = -40001;
	public final static String VALIDATE_SIGNATURE_MESSAGE = "签名验证错误";
	public final static int PARSEXMLERROR = -40002;
	public final static String PARSE_XML_REEOR_MESSAGE = "xml解析失败";
	public final static int COMPUTE_SIGNATURE_ERROR = -40003;
	public final static String COMPUTE_SIGNATURE_MESSAGE = "sha加密生成签名失败";
	public final static int ILLEGALAESKEY = -40004;
	public final static String ILLEGAL_AESKEY_MESSAGE = "SymmetricKey非法";
	public final static int ValidateCorpidError = -40005;
	public final static String VALIDATE_CORPID_ERROR_MESSAGE = "appid校验失败";
	public final static int ENCRYPTAESERROR = -40006;
	public final static String ENCRYPT_AES_ERROR_MESSAGE = "aes加密失败";
	public final static int DECRYPTAESERROR = -40007;
	public final static String DECRYPT_AES_ERROR_MESSAGE = "aes解密失败";
	public final static int ILLEGALBUFFER = -40008;
	public final static String ILLEGAL_BUFFER_MESSAGE = "解密后得到的buffer非法";
	public final static int ILLEGALBUFFER_AESKEY_ERROR = -40009;
	public final static String ILLEGALBUFFER_AESKEY_ERROR_MESSAGE = "解密后得到的buffer非法,AESKEY没有对应起来";
	//public final static int EncodeBase64Error = -40009;
	//public final static int DecodeBase64Error = -40010;
	//public final static int GenReturnXmlError = -40011;

	private int code;

	private static String getMessage(int code) {
		logger.debug("code " + code);
		switch (code) {
		case VALIDATE_SIGNATUR_EERROR:
			logger.debug("FAIL MESSAGE" + VALIDATE_SIGNATURE_MESSAGE);
			return VALIDATE_SIGNATURE_MESSAGE;
		case PARSEXMLERROR:
			logger.debug("FAIL MESSAGE" + PARSE_XML_REEOR_MESSAGE);
			return PARSE_XML_REEOR_MESSAGE;
		case COMPUTE_SIGNATURE_ERROR:
			logger.debug("FAIL MESSAGE" + COMPUTE_SIGNATURE_MESSAGE);
			return COMPUTE_SIGNATURE_MESSAGE;
		case ILLEGALAESKEY:
			logger.debug("FAIL MESSAGE" + ILLEGAL_AESKEY_MESSAGE);
			return ILLEGAL_AESKEY_MESSAGE;
		case ValidateCorpidError:
			logger.debug("FAIL MESSAGE" + VALIDATE_CORPID_ERROR_MESSAGE);
			return VALIDATE_CORPID_ERROR_MESSAGE;
		case ENCRYPTAESERROR:
			logger.debug("FAIL MESSAGE" + ENCRYPT_AES_ERROR_MESSAGE);
			return ENCRYPT_AES_ERROR_MESSAGE;
		case DECRYPTAESERROR:
			logger.debug("FAIL MESSAGE" + DECRYPT_AES_ERROR_MESSAGE);
			return DECRYPT_AES_ERROR_MESSAGE;
		case ILLEGALBUFFER:
			logger.debug("FAIL MESSAGE" + ILLEGAL_BUFFER_MESSAGE);
			return ILLEGAL_BUFFER_MESSAGE;
		case ILLEGALBUFFER_AESKEY_ERROR:
			logger.debug("FAIL MESSAGE" + ILLEGALBUFFER_AESKEY_ERROR_MESSAGE);
			return ILLEGALBUFFER_AESKEY_ERROR_MESSAGE;
//		case EncodeBase64Error:
//			return "base64加密错误";
//		case DecodeBase64Error:
//			return "base64解密错误";
//		case GenReturnXmlError:
//			return "xml生成失败";
		default:
			return null; // cannot be
		}
	}

	public int getCode() {
		return code;
	}

	AesException(int code) {
		super(getMessage(code));
		this.code = code;
	}

}