package com.example.administrator.myapplication.JsonData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fm on 2019/5/23.
 */

public class ChildJson implements Serializable {

    /**
     * status : 200
     * data : [{"role":2,"allowShow":true,"endtime":0,"enddate":"","payService":{"studentId":"223189","school_id":61,"school_type":"exam","endTime":0,"endDate":"","dayNum":0,"subscribe_id":0,"knowStatus":0,"countdown":"","report_free":false},"showShop":1,"school_type":"exam","roleLabel":"爸爸","student_id":"223189","class_name":"高一1班","school_name":"青岛19中","real_name":"王少川","cardId":"","status":1,"primary_contact":"268219","username":"2932295943","card_id":"370202200302104918","bind_verify":0,"temp_password":"","subscribe_status":false,"is_create":true}]
     */

    public int status;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * role : 2
         * allowShow : true
         * endtime : 0
         * enddate :
         * payService : {"studentId":"223189","school_id":61,"school_type":"exam","endTime":0,"endDate":"","dayNum":0,"subscribe_id":0,"knowStatus":0,"countdown":"","report_free":false}
         * showShop : 1
         * school_type : exam
         * roleLabel : 爸爸
         * student_id : 223189
         * class_name : 高一1班
         * school_name : 青岛19中
         * real_name : 王少川
         * cardId :
         * status : 1
         * primary_contact : 268219
         * username : 2932295943
         * card_id : 370202200302104918
         * bind_verify : 0
         * temp_password :
         * subscribe_status : false
         * is_create : true
         */

        public int role;
        public boolean allowShow;
        public int endtime;
        public String enddate;
        public PayServiceBean payService;
        public int showShop;
        public String school_type;
        public String roleLabel;
        public String student_id;
        public String class_name;
        public String school_name;
        public String real_name;
        public String cardId;
        public int status;
        public String primary_contact;
        public String username;
        public String card_id;
        public int bind_verify;
        public String temp_password;
        public boolean subscribe_status;
        public boolean is_create;

        public static class PayServiceBean {
            /**
             * studentId : 223189
             * school_id : 61
             * school_type : exam
             * endTime : 0
             * endDate :
             * dayNum : 0
             * subscribe_id : 0
             * knowStatus : 0
             * countdown :
             * report_free : false
             */

            public String studentId;
            public int school_id;
            public String school_type;
            public int endTime;
            public String endDate;
            public int dayNum;
            public int subscribe_id;
            public int knowStatus;
            public String countdown;
            public boolean report_free;
        }


    }

    public List<DataBean> getData() {
        return data;
    }

}
