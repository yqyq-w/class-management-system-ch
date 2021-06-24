package bean;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;
import java.util.Objects;

/*
其中学生信息有:
学号、姓名、性别、出生日期、邮箱、备注、

新增字段：所属班级
 */
public class Student implements Serializable, HttpSessionActivationListener, HttpSessionBindingListener {
    private int id;
    private String stuId;
    private String stuName;
    private String sex;
    private String bDay;
    private String email;
    private String note;

    private int cid;
    private ClassInfo classInfo;

    public Student(){};

    public Student(String stuId, String stuName, String sex, String bDay, String email, String note) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.sex = sex;
        this.bDay = bDay;
        this.email = email;
        this.note = note;
    }

    public Student(int id, String stuId, String stuName, String sex, String bDay, String email, String note, int cid) {
        this.id = id;
        this.stuId = stuId;
        this.stuName = stuName;
        this.sex = sex;
        this.bDay = bDay;
        this.email = email;
        this.note = note;
        this.cid = cid;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getbDay() {
        return bDay;
    }

    public void setbDay(String bDay) {
        this.bDay = bDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    // toString
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", sex='" + sex + '\'' +
                ", bDay='" + bDay + '\'' +
                ", email='" + email + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("Student执行了钝化操作..." + httpSessionEvent.getSession());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("Student活化操作进行中...");
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Student对象绑定到session中了" + httpSessionBindingEvent.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        System.out.println("Student解除绑定成功！");
    }
}
