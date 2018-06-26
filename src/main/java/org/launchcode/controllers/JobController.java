package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam("id") int id, Model model) {

        // TODO #1 (FINISHED) - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute(job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors, String name, int employerId, int locationId, int coreCompetencyId, int positionTypeId) {

        // TODO #6 (FINISHED) - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            return "new-job";
        }

        String aName = name;
        Employer anEmployer = jobData.getEmployers().findById(employerId);
        Location aLocation = jobData.getLocations().findById(locationId);
        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(coreCompetencyId);
        PositionType aPositionType = jobData.getPositionTypes().findById(positionTypeId);

        Job newJob = new Job();
        newJob.setName(aName);
        newJob.setEmployer(anEmployer);
        newJob.setLocation(aLocation);
        newJob.setCoreCompetency(aSkill);
        newJob.setPositionType(aPositionType);

        jobData.add(newJob);

        int id = newJob.getId();

        return "redirect:/job?id=" + id;

    }
}