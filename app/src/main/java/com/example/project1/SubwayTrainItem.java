package com.example.project1;

public class SubwayTrainItem {

    String subwayName;
    int trainSituation;
    int trainNum;

    public SubwayTrainItem(String subwayName, int trainSituation, int trainNum) {
        int k=0;
        switch(trainSituation){
            case 0:
                k=R.drawable.subwayline60;
                break;
            case 1:
                k=R.drawable.subwaylinewithcar66;
                break;
            case 2:
                k=R.drawable.subwaylinewithoutcar66;
                break;
            case 3:
                k=R.drawable.subwaylinewithcar66;
                break;
        }

        this.subwayName = subwayName;
        this.trainSituation = k;
        this.trainNum = trainNum;


    }

    public String getSubwayName() {
        return subwayName;
    }

    public void setSubwayName(String subwayName) {
        this.subwayName = subwayName;
    }

    public int getTrainSituation() {
        return trainSituation;
    }

    public void setTrainSituation(int trainSituation) {
        this.trainSituation = trainSituation;
    }

    public int getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(int trainNum) {
        this.trainNum = trainNum;
    }
}
