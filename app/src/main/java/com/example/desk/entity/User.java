package com.example.desk.entity;

public class User {


    /**
     * data : {"userid":"2016021022","college":"外国语学院","classs":"英语135","password":"25f9e794323b453885f5181f1b624d0b","birthday":"1998-10-23","email":"13608428279@163.com","gender":"男","userlogo":null}
     * error_code : 0
     */

    private DataBean data;
    private int error_code;

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
         * userid : 2016021022
         * college : 外国语学院
         * classs : 英语135
         * password : 25f9e794323b453885f5181f1b624d0b
         * birthday : 1998-10-23
         * email : 13608428279@163.com
         * gender : 男
         * userlogo : null
         */

        private String userid;
        private String college;
        private String classs;
        private String password;
        private String birthday;
        private String email;
        private String gender;
        private String userlogo;

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

        public String getClasss() {
            return classs;
        }

        public void setClasss(String classs) {
            this.classs = classs;
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

        public String getUserlogo() {
            return userlogo;
        }

        public void setUserlogo(String userlogo) {
            this.userlogo = userlogo;
        }
    }
}
