package com.longke.shot.entity;

import java.util.List;

/**
 * 作者：$ longke on 2018/3/28 08:52
 * 邮箱：373497847@qq.com
 */

public class GetStudentRankingDetail {

    /**
     * FormateStr : yyyy-MM-dd
     * ResultCode : 200
     * Success : true
     * Message :
     * Data : {"ClassName":"一班","GroupIndex":2,"CurrStudentCode":"test022","RankingList":[{"RankingIndex":1,"StudentName":"测试22","StudentCode":"test022","TotalBulletCount":4,"Score":21,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":2,"StudentName":"测试20","StudentCode":"test020","TotalBulletCount":1,"Score":10,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":3,"StudentName":"测试18","StudentCode":"test018","TotalBulletCount":1,"Score":7,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":4,"StudentName":"测试21","StudentCode":"test021","TotalBulletCount":1,"Score":7,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":5,"StudentName":"测试12","StudentCode":"test012","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":6,"StudentName":"测试13","StudentCode":"test013","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":7,"StudentName":"测试14","StudentCode":"test014","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":8,"StudentName":"测试15","StudentCode":"test015","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":9,"StudentName":"测试16","StudentCode":"test016","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":10,"StudentName":"测试17","StudentCode":"test017","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":11,"StudentName":"测试19","StudentCode":"test019","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"}]}
     * TotalCount : 0
     * SessionId : qrix5gdetitzya2qyapj50ne
     * ContentEncoding : null
     * ContentType : null
     * JsonRequestBehavior : 0
     * MaxJsonLength : null
     * RecursionLimit : null
     */

    private String FormateStr;
    private int ResultCode;
    private boolean Success;
    private String Message;
    private DataEntity Data;
    private int TotalCount;
    private String SessionId;
    private Object ContentEncoding;
    private Object ContentType;
    private int JsonRequestBehavior;
    private Object MaxJsonLength;
    private Object RecursionLimit;

    public String getFormateStr() {
        return FormateStr;
    }

    public void setFormateStr(String FormateStr) {
        this.FormateStr = FormateStr;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataEntity getData() {
        return Data;
    }

    public void setData(DataEntity Data) {
        this.Data = Data;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String SessionId) {
        this.SessionId = SessionId;
    }

    public Object getContentEncoding() {
        return ContentEncoding;
    }

    public void setContentEncoding(Object ContentEncoding) {
        this.ContentEncoding = ContentEncoding;
    }

    public Object getContentType() {
        return ContentType;
    }

    public void setContentType(Object ContentType) {
        this.ContentType = ContentType;
    }

    public int getJsonRequestBehavior() {
        return JsonRequestBehavior;
    }

    public void setJsonRequestBehavior(int JsonRequestBehavior) {
        this.JsonRequestBehavior = JsonRequestBehavior;
    }

    public Object getMaxJsonLength() {
        return MaxJsonLength;
    }

    public void setMaxJsonLength(Object MaxJsonLength) {
        this.MaxJsonLength = MaxJsonLength;
    }

    public Object getRecursionLimit() {
        return RecursionLimit;
    }

    public void setRecursionLimit(Object RecursionLimit) {
        this.RecursionLimit = RecursionLimit;
    }

    public static class DataEntity {
        /**
         * ClassName : 一班
         * GroupIndex : 2
         * CurrStudentCode : test022
         * RankingList : [{"RankingIndex":1,"StudentName":"测试22","StudentCode":"test022","TotalBulletCount":4,"Score":21,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":2,"StudentName":"测试20","StudentCode":"test020","TotalBulletCount":1,"Score":10,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":3,"StudentName":"测试18","StudentCode":"test018","TotalBulletCount":1,"Score":7,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":4,"StudentName":"测试21","StudentCode":"test021","TotalBulletCount":1,"Score":7,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":5,"StudentName":"测试12","StudentCode":"test012","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":6,"StudentName":"测试13","StudentCode":"test013","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":7,"StudentName":"测试14","StudentCode":"test014","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":8,"StudentName":"测试15","StudentCode":"test015","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":9,"StudentName":"测试16","StudentCode":"test016","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":10,"StudentName":"测试17","StudentCode":"test017","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"},{"RankingIndex":11,"StudentName":"测试19","StudentCode":"test019","TotalBulletCount":0,"Score":0,"IsFinished":true,"UseTime":"112秒"}]
         */

        private String ClassName;
        private int GroupIndex;
        private String CurrStudentCode;
        private List<RankingListEntity> RankingList;

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
        }

        public int getGroupIndex() {
            return GroupIndex;
        }

        public void setGroupIndex(int GroupIndex) {
            this.GroupIndex = GroupIndex;
        }

        public String getCurrStudentCode() {
            return CurrStudentCode;
        }

        public void setCurrStudentCode(String CurrStudentCode) {
            this.CurrStudentCode = CurrStudentCode;
        }

        public List<RankingListEntity> getRankingList() {
            return RankingList;
        }

        public void setRankingList(List<RankingListEntity> RankingList) {
            this.RankingList = RankingList;
        }

        public static class RankingListEntity {
            /**
             * RankingIndex : 1
             * StudentName : 测试22
             * StudentCode : test022
             * TotalBulletCount : 4
             * Score : 21
             * IsFinished : true
             * UseTime : 112秒
             */

            private int RankingIndex;
            private String StudentName;
            private String StudentCode;
            private int TotalBulletCount;
            private int Score;
            private boolean IsFinished;
            private String UseTime;

            public int getRankingIndex() {
                return RankingIndex;
            }

            public void setRankingIndex(int RankingIndex) {
                this.RankingIndex = RankingIndex;
            }

            public String getStudentName() {
                return StudentName;
            }

            public void setStudentName(String StudentName) {
                this.StudentName = StudentName;
            }

            public String getStudentCode() {
                return StudentCode;
            }

            public void setStudentCode(String StudentCode) {
                this.StudentCode = StudentCode;
            }

            public int getTotalBulletCount() {
                return TotalBulletCount;
            }

            public void setTotalBulletCount(int TotalBulletCount) {
                this.TotalBulletCount = TotalBulletCount;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }

            public boolean isIsFinished() {
                return IsFinished;
            }

            public void setIsFinished(boolean IsFinished) {
                this.IsFinished = IsFinished;
            }

            public String getUseTime() {
                return UseTime;
            }

            public void setUseTime(String UseTime) {
                this.UseTime = UseTime;
            }
        }
    }
}
