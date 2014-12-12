package eu.factorx.citizens.model.survey;

import eu.factorx.citizens.model.type.AccountType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 3/12/14.
 */
public enum TopicEnum {

    // the order is important for the email generation
    // for the topics and for the linked question code
    // please keep it save !
    HOUSEHOLD_ACTION_PRESENCE("action", AccountType.HOUSEHOLD, "presence","Q3210", "Q3211"),
    HOUSEHOLD_ACTION_PROGRAMS("action", AccountType.HOUSEHOLD, "programs", "Q3110", "Q3120", "Q3130"),
    HOUSEHOLD_ACTION_HEATING("action", AccountType.HOUSEHOLD, "heating", "Q3310", "Q3320", "Q3330"),
    HOUSEHOLD_ACTION_LIGHTING("action", AccountType.HOUSEHOLD, "lighting", "Q3410", "Q3420", "Q3510", "Q3530", "Q3610", "Q3620", "Q3630", "Q3631", "Q3640", "Q3810"),
    HOUSEHOLD_ACTION_DINNER("action", AccountType.HOUSEHOLD, "dinner", "Q3710", "Q3711", "Q3720", "Q3730", "Q3750", "Q3760", "Q3740", "Q3741");



    private String questionType;
    private String topicName;
    private AccountType accountType;
    private List<String> questionCodeList;

    private TopicEnum(String questionType, AccountType accountType, String topicName,String... questionCodeList) {
        this.questionType = questionType;
        this.accountType = accountType;
        this.topicName = topicName;

        this.questionCodeList = new ArrayList<>();

        for (String s : questionCodeList) {
            this.questionCodeList.add(s);
        }

    }

    public String getQuestionType() {
        return questionType;
    }

    public String getTopicName() {
        return topicName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public List<String> getQuestionCodeList() {
        return questionCodeList;
    }

    @Override
    public String toString() {
        return "TopicEnum{" +
                "questionType='" + questionType + '\'' +
                ", topicName='" + topicName + '\'' +
                ", questionCodeList=" + questionCodeList +
                '}';
    }
}
