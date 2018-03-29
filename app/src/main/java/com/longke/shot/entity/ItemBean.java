package com.longke.shot.entity;

import java.util.List;

/**
 * 作者：$ longke on 2018/3/27 14:35
 * 邮箱：373497847@qq.com
 */

public class ItemBean {


    /**
     * FormateStr : yyyy-MM-dd
     * ResultCode : 200
     * Success : true
     * Message :
     * Data : {"StudentData":{"StudentName":"测试67","ClassName":"一班","GroupIndex":7,"TotalBulletCount":15,"TotalScore":70,"UseTime":"16秒"},"ShootDetailList":[{"BulletHoleIndex":1,"CurrShootNum":5,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":2,"CurrShootNum":5,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":3,"CurrShootNum":8,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":4,"CurrShootNum":6,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":5,"CurrShootNum":0,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":6,"CurrShootNum":8,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":7,"CurrShootNum":7,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":8,"CurrShootNum":3,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":9,"CurrShootNum":1,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":10,"CurrShootNum":4,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":11,"CurrShootNum":0,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":12,"CurrShootNum":5,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":13,"CurrShootNum":9,"ShootTime":"2018-03-29 23:55:01"},{"BulletHoleIndex":14,"CurrShootNum":3,"ShootTime":"2018-03-29 23:55:01"},{"BulletHoleIndex":15,"CurrShootNum":6,"ShootTime":"2018-03-29 23:55:01"}]}
     * TotalCount : 0
     * SessionId : rudy2p1xaydxicc2o0mamrwr
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
    private DataBean Data;
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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
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

    public static class DataBean {
        /**
         * StudentData : {"StudentName":"测试67","ClassName":"一班","GroupIndex":7,"TotalBulletCount":15,"TotalScore":70,"UseTime":"16秒"}
         * ShootDetailList : [{"BulletHoleIndex":1,"CurrShootNum":5,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":2,"CurrShootNum":5,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":3,"CurrShootNum":8,"ShootTime":"2018-03-29 23:54:58"},{"BulletHoleIndex":4,"CurrShootNum":6,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":5,"CurrShootNum":0,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":6,"CurrShootNum":8,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":7,"CurrShootNum":7,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":8,"CurrShootNum":3,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":9,"CurrShootNum":1,"ShootTime":"2018-03-29 23:54:59"},{"BulletHoleIndex":10,"CurrShootNum":4,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":11,"CurrShootNum":0,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":12,"CurrShootNum":5,"ShootTime":"2018-03-29 23:55:00"},{"BulletHoleIndex":13,"CurrShootNum":9,"ShootTime":"2018-03-29 23:55:01"},{"BulletHoleIndex":14,"CurrShootNum":3,"ShootTime":"2018-03-29 23:55:01"},{"BulletHoleIndex":15,"CurrShootNum":6,"ShootTime":"2018-03-29 23:55:01"}]
         */

        private StudentDataBean StudentData;
        private List<ShootDetailListBean> ShootDetailList;

        public StudentDataBean getStudentData() {
            return StudentData;
        }

        public void setStudentData(StudentDataBean StudentData) {
            this.StudentData = StudentData;
        }

        public List<ShootDetailListBean> getShootDetailList() {
            return ShootDetailList;
        }

        public void setShootDetailList(List<ShootDetailListBean> ShootDetailList) {
            this.ShootDetailList = ShootDetailList;
        }

        public static class StudentDataBean {
            /**
             * StudentName : 测试67
             * ClassName : 一班
             * GroupIndex : 7
             * TotalBulletCount : 15
             * TotalScore : 70
             * UseTime : 16秒
             */

            private String StudentName;
            private String ClassName;
            private int GroupIndex;
            private int TotalBulletCount;
            private int TotalScore;
            private String UseTime;

            public String getStudentName() {
                return StudentName;
            }

            public void setStudentName(String StudentName) {
                this.StudentName = StudentName;
            }

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

            public int getTotalBulletCount() {
                return TotalBulletCount;
            }

            public void setTotalBulletCount(int TotalBulletCount) {
                this.TotalBulletCount = TotalBulletCount;
            }

            public int getTotalScore() {
                return TotalScore;
            }

            public void setTotalScore(int TotalScore) {
                this.TotalScore = TotalScore;
            }

            public String getUseTime() {
                return UseTime;
            }

            public void setUseTime(String UseTime) {
                this.UseTime = UseTime;
            }
        }

        public static class ShootDetailListBean {
            /**
             * BulletHoleIndex : 1
             * CurrShootNum : 5
             * ShootTime : 2018-03-29 23:54:58
             */

            private int BulletHoleIndex;
            private int CurrShootNum;
            private String ShootTime;

            public int getBulletHoleIndex() {
                return BulletHoleIndex;
            }

            public void setBulletHoleIndex(int BulletHoleIndex) {
                this.BulletHoleIndex = BulletHoleIndex;
            }

            public int getCurrShootNum() {
                return CurrShootNum;
            }

            public void setCurrShootNum(int CurrShootNum) {
                this.CurrShootNum = CurrShootNum;
            }

            public String getShootTime() {
                return ShootTime;
            }

            public void setShootTime(String ShootTime) {
                this.ShootTime = ShootTime;
            }
        }
    }
}
