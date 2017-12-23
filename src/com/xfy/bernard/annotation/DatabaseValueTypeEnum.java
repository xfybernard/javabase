package com.xfy.bernard.annotation;

public enum DatabaseValueTypeEnum {
	VARCHAR("字符型"), NUMBER("数字型"), DATE("日期型"), BOOL("布尔型");

	private String desc;

	private DatabaseValueTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
