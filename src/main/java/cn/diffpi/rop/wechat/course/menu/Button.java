package cn.diffpi.rop.wechat.course.menu;

/**
 * 菜单的基类
 * 
 * @author luoxun
 */
public class Button {
	private String name;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
