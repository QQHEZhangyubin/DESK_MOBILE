package com.example.desk.entity;

public class User {
    //双重检查，单例模式
    private static volatile  User user;
    /**
     * data : {"userid":"2016021053","college":"计算机与控制工程学院","password":"50324f72b7d6ea0214a5fde172ee454d","birthday":"1998/10/23","email":"13608428279@163.com","gender":"男","classss":"软件161"}
     * error_code : 0
     */

    private DataBean data;
    private int error_code;
    public static void  ClearUser(){
        //清空user对象
        user = null;
    }
    private User(){}
    public static User getInstance(){
        if (user == null){
            synchronized (User.class){
                if (user == null){
                    user = new User();
                }
            }
        }
        return user;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class DataBean {
        /**
         * userid : 2016021053
         * college : 计算机与控制工程学院
         * password : 50324f72b7d6ea0214a5fde172ee454d
         * birthday : 1998/10/23
         * email : 13608428279@163.com
         * gender : 男
         * classss : 软件161
         */

        private String userid;
        private String college;
        private String password;
        private String birthday;
        private String email;
        private String gender;
        private String classss;
        private String userlogo;

        public String getUserlogo() {
            return userlogo;
        }

        public void setUserlogo(String userlogo) {
            this.userlogo = userlogo;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getCollege() {
            return college;
        }

        public void setCollege(String college) {
            this.college = college;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getClassss() {
            return classss;
        }

        public void setClassss(String classss) {
            this.classss = classss;
        }
    }
}
