package domain1.tema7tehnologiijava.services.objects;

public class CreateSubmission {
    public Integer activity;
    public String comment;
    public Integer grade;
    public CreateSubmission() {}
    public String getComment() {
        return comment;
    }
    public Integer getActivity() {
        return activity;
    }
    public Integer getGrade() {
        return grade;
    }
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    public void setActivity(Integer activity) {
        this.activity = activity;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}