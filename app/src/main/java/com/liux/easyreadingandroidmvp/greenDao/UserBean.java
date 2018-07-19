package com.liux.easyreadingandroidmvp.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by
 * 项目名称：com.liux.easyreading.greenDao
 * 项目日期：2018/3/29
 * 作者：liux
 * 功能：
 * @Entity  　表明这个实体类会在数据库中生成一个与之相对应的表。
 * @Id 　对应数据表中的 Id 字段，有了解数据库的话，是一条数据的唯一标识。对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
 * @Property(nameInDb = "STUDENTNUM") 　表名这个属性对应数据表中的 STUDENTNUM 字段。
 * @Property 　可以自定义字段名，注意外键不能使用该属性
 * @NotNull 　该属性值不能为空
 * @Transient 　该属性不会被存入数据库中
 * @Unique 　表名该属性在数据库中只能有唯一值
 *
 * @author 750954283(qq)
 */
@Entity
public class UserBean {

    @Id
    private Long id;
    @Property(nameInDb = "USERNAME")
    private String userName;
    @Property(nameInDb = "PASSWORD")
    private String passWord;
    private int age;
    private String sex;

    @Generated(hash = 131503740)
    public UserBean(Long id, String userName, String passWord, int age, String sex) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.age = age;
        this.sex = sex;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return this.passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
