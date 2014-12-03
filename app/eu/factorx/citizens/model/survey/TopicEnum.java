package eu.factorx.citizens.model.survey;

import java.util.Arrays;

/**
 * Created by florian on 3/12/14.
 */
public enum TopicEnum {


    HOUSEHOLD_ACTION_PRESENCE("action", "household", "Q3210", "Q3211"),
    HOUSEHOLD_ACTION_DINNER("action", "dinner", "Q3710", "Q3711", "Q3720", "Q3730", "Q3750", "Q3760", "Q3740", "Q3741"),
    HOUSEHOLD_ACTION_HEATING("action", "heating", "Q3310", "Q3320", "Q3330"),
    HOUSEHOLD_ACTION_PROGRAMS("action", "programs", "Q3110", "Q3120", "Q3130"),
    HOUSEHOLD_ACTION_LIGHTING("action", "lighting", "Q3410", "Q3420", "Q3510", "Q3530", "Q3610", "Q3620", "Q3630", "Q3631", "Q3640", "Q3810");


    private String questionType;
    private String topicName;
    private String[] questionCodeList;

    private TopicEnum(String questionType, String topicName, String... questionCodeList) {
        this.questionType = questionType;
        this.topicName = topicName;
        this.questionCodeList = questionCodeList;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getTopicName() {
        return topicName;
    }

    public String[] getQuestionCodeList() {
        return questionCodeList;
    }

    @Override
    public String toString() {
        return "TopicEnum{" +
                "questionType='" + questionType + '\'' +
                ", topicName='" + topicName + '\'' +
                ", questionCodeList=" + Arrays.toString(questionCodeList) +
                '}';
    }
}
