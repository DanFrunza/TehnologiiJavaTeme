package domain1.tema7tehnologiijava.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import domain1.tema7tehnologiijava.beans.AuthBean;
import domain1.tema7tehnologiijava.beans.SubmissionBean;
import domain1.tema7tehnologiijava.models.Activity;
import domain1.tema7tehnologiijava.models.Submission;
import domain1.tema7tehnologiijava.services.objects.Authentification;
import domain1.tema7tehnologiijava.services.objects.CreateSubmission;
import java.util.List;


@Path("/submissions")
@ApplicationScoped
public class SubmissionService {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private SubmissionBean submissionBean;
    @Inject
    private AuthBean authBean;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Authentification authentication) {
        authBean.setUsername(authentication.getUser());
        authBean.setPassword(authentication.getPassword());
        authBean.login();
        if (authBean.getCurrentMyUser() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(authBean.getCurrentMyUser()).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = Submission.class)
    public Response getAllSubmissions() {
        if (authBean.getCurrentMyUser() == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        List<Submission> submissions = submissionBean.getEvaluations();
        return Response.ok(submissions).type(MediaType.APPLICATION_JSON).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubmissionById(@PathParam("id") Long id) {
        Submission submission = entityManager.find(Submission.class, id);
        if (submission == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(submission).build();
    }
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = Submission.class)
    @Transactional
    public Response updateSubmission(@PathParam("id") Long id, CreateSubmission subm) {
        if (authBean.getCurrentMyUser() == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        Submission submission = entityManager.find(Submission.class, id);
        if (submission == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(subm.getGrade() != null)
            submission.setGrade(subm.getGrade());
        if (subm.getComment() != null)
            submission.setComment(subm.getComment());
        entityManager.persist(submission);
        return Response.ok(submission).build();
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSubmission(@PathParam("id") Long id) {
        if (authBean.getCurrentMyUser() == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        Submission submission = entityManager.find(Submission.class, id);
        entityManager.remove(submission);
        return Response.ok().type(MediaType.APPLICATION_JSON).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponseSchema(value = Submission.class)
    @Transactional
    public Response createSubmission(CreateSubmission s) {
        System.out.print(s);
        if (authBean.getCurrentMyUser() == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();
        System.out.print(s);
        Submission submission = new Submission();
        submission.setUser(authBean.getCurrentMyUser());
        submission.setActivity(
                entityManager.createQuery(
                        "select a from Activity a WHERE a.id = :ID", Activity.class
                ).setParameter("ID", s.activity).getSingleResult()
        );
        submission.setComment(s.comment);
        submission.setGrade(s.grade);
        entityManager.persist(submission);
        return Response.ok(submission).type(MediaType.APPLICATION_JSON).build();
    }
}
