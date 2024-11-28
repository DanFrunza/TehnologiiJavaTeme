package domain1.tema7tehnologiijava.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import domain1.tema7tehnologiijava.models.Submission;

@ApplicationScoped
public class SubmissionListener {
    public void onAnySubmissionEvent(@Observes Submission submission) {
        System.out.println("Observed creating a submission " + submission.toString());
    }
}
