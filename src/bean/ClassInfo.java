package bean;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/*
 * assn 402
 * yiqingw

-- 创建班级表 班级名称、年级、班主任名称、班级口号 、班级人数
CREATE TABLE class (
	id INT PRIMARY KEY AUTO_INCREMENT,
	cName VARCHAR(20),
	grade INT,
	teacher VARCHAR(20),
	slogan VARCHAR(100),
	size INT
);
 */
public class ClassInfo implements Serializable, HttpSessionActivationListener, HttpSessionBindingListener {
    private int id;
    private String cName;
    private int grade;
    private String teacher;
    private String slogan;
    private int size;

    public ClassInfo() {
    }

//    public ClassInfo(String cName, int grade, String teacher, String slogan) {
//        this.cName = cName;
//        this.grade = grade;
//        this.teacher = teacher;
//        this.slogan = slogan;
//    }

    public ClassInfo(int id, String cName, int grade, String teacher, String slogan, int size) {
        this.id = id;
        this.cName = cName;
        this.grade = grade;
        this.teacher = teacher;
        this.slogan = slogan;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "ClassInfo{" +
                "cName='" + cName + '\'' +
                ", grade=" + grade +
                ", teacher='" + teacher + '\'' +
                ", slogan='" + slogan + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("ClassInfo执行了钝化操作..." + httpSessionEvent.getSession());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("ClassInfo活化操作进行中...");
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("ClassInfo对象绑定到session中了" + httpSessionBindingEvent.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("ClassInfo解除绑定成功！");
    }
}
