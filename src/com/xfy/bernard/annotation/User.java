package com.xfy.bernard.annotation;

import java.lang.reflect.Field;

@TableAnnotation("t_user")
public class User {
	@ColumnAnnotation(column = "id", sort = ColumnSortEnum.DESC, type = DatabaseValueTypeEnum.NUMBER)
	private Integer id;
	@ColumnAnnotation(column = "user_name")
	private String userName;
	@ColumnAnnotation(column = "user_pwd")
	private String userPwd;

	public User(Integer id, String userName, String userPwd) {
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public static void main(String[] args) {
		User u = new User(1, "zs", "123456");
		if (u.getClass().isAnnotationPresent(TableAnnotation.class)) {
			TableAnnotation tableAtn = u.getClass().getAnnotation(TableAnnotation.class);
			System.out.println("u ӳ��ı���Ϊ:" + tableAtn.value());
			Field[] fields = u.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(ColumnAnnotation.class)) {
					ColumnAnnotation can = field.getAnnotation(ColumnAnnotation.class);
					System.out.println(String.format("����:[%s],��Ӧ�����ݿ��ֶ���:[%s],����:[%s],����:[%s],���ݿ���Ϣ:[%s]",
							field.getName(), can.column(), can.type(), can.sort(),
							can.databaseInfo().name() + can.databaseInfo().version()));
				}
			}
		}
	}
}
