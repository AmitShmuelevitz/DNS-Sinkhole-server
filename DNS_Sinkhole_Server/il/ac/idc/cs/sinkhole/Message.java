package il.ac.idc.cs.sinkhole;

import java.util.Arrays;

public class Message {
    /**
     * a parser for a full DNS query. this object gets the content of the DNS query as byte array and delegate all
     * necessary functionality
     */
    byte [] content;

    Question[] questions;
    GeneralRecord[] answers;
    AuthRecord[] authoritie;
    GeneralRecord[] additional;

    Headers headers;

    public AuthRecord getAuthorities(int index) {
        if (index < this.authoritie.length){
            return this.authoritie[index];
        }
        else{
            return null;
        }

    }

    public byte[] getContent() {
        return Arrays.copyOf(content, this.getEnd());
    }

    public Message(byte [] content){

        this.content = content;

        this.headers = new Headers(content);

        this.questions = new Question[this.headers.getNumQuestions()];
        this.answers = new GeneralRecord[this.headers.getNumAnswers()];
        this.authoritie = new AuthRecord[this.headers.getNumAuthorities()];
        this.additional = new GeneralRecord[this.headers.getNumAdditional()];

        // note the order of calls
        this.parseQuestions();
        this.parseAnswers();
        this.parseAuth();
        this.parseAdditionals();

    }

    private void parseQuestions(){
        int offset = this.headers.end();
        for (int i = 0; i < this.questions.length; i++){
            this.questions[i] = new Question(offset, content);
            offset = this.questions[i].getEnd();
        }
    }

    public int getQuestionsEnd(){
        int offset;
        if (this.questions.length > 0){
            offset = this.questions[this.questions.length - 1].getEnd();
        }
        else {
            offset = this.headers.end();
        }
        return offset;
    }

    private void parseAnswers(){
        int offset = this.getQuestionsEnd();
        for (int i = 0; i < this.answers.length; i++){
            this.answers[i] = new GeneralRecord(offset, content);
            offset = this.answers[i].getEnd();
        }

    }

    private int getAnswersEnd(){
        int offset;
        if (this.answers.length > 0){
            offset = this.answers[this.answers.length - 1].getEnd();
        }
        else {
            offset = this.getQuestionsEnd();
        }
        return offset;
    }

    private void parseAuth(){
        int offset = this.getAnswersEnd();
        for (int i = 0; i < this.authoritie.length; i++){
            this.authoritie[i] = new AuthRecord(offset, content);
            offset = this.authoritie[i].getEnd();
        }

    }

    private int getAuthEnd(){
        int offset;
        if (this.authoritie.length > 0){
            offset = this.authoritie[this.authoritie.length - 1].getEnd();
        }
        else {
            offset = this.getAnswersEnd();
        }
        return offset;
    }

    private void parseAdditionals(){
        int offset = this.getAuthEnd();
        for (int i = 0; i < this.additional.length; i++){
            this.additional[i] = new GeneralRecord(offset, content);
            offset = this.additional[i].getEnd();
        }

    }

    private int getAddtionalsEnd(){
        int offset;
        if (this.additional.length > 0){
            offset = this.additional[this.additional.length - 1].getEnd();
        }
        else {
            offset = this.getAuthEnd();
        }
        return offset;
    }

    public int getEnd(){
        return this.getAddtionalsEnd();
    }


}
