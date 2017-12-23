package com.xfy.bernard.annotation;

public enum DatabaseValueTypeEnum {
	VARCHAR("�ַ���"), NUMBER("������"), DATE("������"), BOOL("������");

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
