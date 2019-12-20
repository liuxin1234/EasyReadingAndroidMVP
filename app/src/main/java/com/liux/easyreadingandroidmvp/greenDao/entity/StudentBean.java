package com.liux.easyreadingandroidmvp.greenDao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by
 * 项目名称：com.liux.easyreadingandroidmvp.greenDao.entity
 * 项目日期：2019/6/27
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */
@Entity
public class StudentBean {
    @Id
    private Long id;
    private String name;
    private int age;
    private String sex;
    @Generated(hash = 574961751)
    public StudentBean(Long id, String name, int age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    @Generated(hash = 2097171990)
    public StudentBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }


}
