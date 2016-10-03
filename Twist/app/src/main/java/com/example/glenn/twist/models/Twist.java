package com.example.glenn.twist.models;

/**
 * Created by Glenn on 24/08/2016.
 */
public class Twist {

    private String title;
    private String twistee;
    private String myAnswer;
    private String twisteeAnswer;
    private String reward;
    private Boolean won;

    public Twist(String title, String twistee, String myAnswer, String twisteeAnswer, String reward, Boolean won) {
        setTitle(title);
        setTwistee(twistee);
        setMyAnswer(myAnswer);
        setTwisteeAnswer(twisteeAnswer);
        setReward(reward);
        setWon(won);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTwistee() {
        return twistee;
    }

    public void setTwistee(String twistee) {
        this.twistee = twistee;
    }

    public String getTwisteeAnswer() {
        return twisteeAnswer;
    }

    public void setTwisteeAnswer(String twisteeAnswer) {
        this.twisteeAnswer = twisteeAnswer;
    }

    public String getMyAnswer() {
        return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
        this.myAnswer = myAnswer;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }
}
