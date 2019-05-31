package com.example.administrator.myapplication.JsonData;

import java.io.Serializable;
import java.util.List;

public class HelpJson implements Serializable {
    public int status;
    public List<HelpJson.DataBean> data;

    public static class DataBean {
        public String category_name;//标题
        public List<HelpList> help_list;//内容的子项

        public static class HelpList {
            public int help_id;//help的id
            public String title;//具体的子项
        }
    }


    public List<DataBean> getData() {
        return data;
    }
}
