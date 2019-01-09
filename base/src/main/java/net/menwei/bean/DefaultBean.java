package net.menwei.bean;

public class DefaultBean {
	private String str;
	private String methodName;
	private long timestamp;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "DefaultBean [str=" + str + ", methodName=" + methodName + ", timestamp=" + timestamp + "]";
	}
	

}
