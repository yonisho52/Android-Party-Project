package com.example.android_final_project.ObjectsClasses;

public class Survey {

    private String partyCode;
    private String question;
    private String ans1;
    private double ans1Rate;
    private int ans1NumOfRate;
    private String ans2;
    private double ans2Rate;
    private int ans2NumOfRate;
    private String ans3;
    private double ans3Rate;
    private int ans3NumOfRate;

    public Survey(String partyCode, String question, String ans1, String ans2, String ans3) {
        this.partyCode = partyCode;
        this.question = question;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans1Rate = 0;
        this.ans1NumOfRate = 0;
        this.ans2Rate = 0;
        this.ans2NumOfRate = 0;
        this.ans3Rate = 0;
        this.ans3NumOfRate = 0;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public double getAns1Rate() {
        return ans1Rate;
    }

    public int getAns1NumOfRate() {
        return ans1NumOfRate;
    }

    public double getAns2Rate() {
        return ans2Rate;
    }

    public int getAns2NumOfRate() {
        return ans2NumOfRate;
    }

    public double getAns3Rate() {
        return ans3Rate;
    }

    public int getAns3NumOfRate() {
        return ans3NumOfRate;
    }
}
