package jp.co.amway.aurora.test.bean;

public class TestActionInfo {

	private String by;
	private String element;
	private String action;
	private String value;
	private boolean screenShot;
	private String comment;
	private boolean status;
	private String errMsg;

	public TestActionInfo() {
		by = "";
		status = false;
		errMsg = "";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isScreenShot() {
		return screenShot;
	}

	public void setScreenShot(boolean screenShot) {
		this.screenShot = screenShot;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
