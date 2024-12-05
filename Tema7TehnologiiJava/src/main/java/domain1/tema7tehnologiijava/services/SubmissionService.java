package domain1.tema7tehnologiijava.services;

import domain1.tema7tehnologiijava.models.Submission;
import domain1.tema7tehnologiijava.models.Activity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/submissions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubmissionService {

    @PersistenceContext
    private EntityManager em;

    @GET
    public List<Submission> getAllSubmissions() {
        return em.createQuery("SELECT s FROM Submission s", Submission.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Submission getSubmission(@PathParam("id") Long id) {
        return em.find(Submission.class, id);
    }

    @POST
    @Transactional
    public Response addSubmission(Submission submission) {
        //Activity activity = activityRepository.findById(submissionDto.getActivity().getId());
        // Set the existing activity to the submission
        //submission.setActivity(activity);
        em.persist(submission);

        return Response.status(Response.Status.CREATED).entity(submission).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateSubmission(@PathParam("id") Long id, Submission updatedSubmission) {
        Submission existingSubmission = em.find(Submission.class, id);
        if (existingSubmission == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingSubmission.setGrade(updatedSubmission.getGrade());
        existingSubmission.setComment(updatedSubmission.getComment());
        em.merge(existingSubmission);
        return Response.ok(existingSubmission).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSubmission(@PathParam("id") Long id) {
        Submission submission = em.find(Submission.class, id);
        if (submission == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(submission);
        return Response.noContent().build();
    }
}
