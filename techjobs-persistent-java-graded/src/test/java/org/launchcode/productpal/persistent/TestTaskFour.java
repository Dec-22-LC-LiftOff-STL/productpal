package org.launchcode.productpal.persistent;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;
import org.launchcode.productpal.persistent.models.Category;
import org.launchcode.productpal.persistent.models.Product;
import org.launchcode.productpal.persistent.models.Skill;
import org.launchcode.productpal.persistent.models.data.CategoryRepository;
import org.launchcode.productpal.persistent.models.data.ProductRepository;
import org.launchcode.productpal.persistent.models.data.SkillRepository;
import org.launchcode.productpal.persistent.controllers.HomeController;
import org.launchcode.productpal.persistent.controllers.ListController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.persistence.ManyToMany;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by LaunchCode
 */
public class TestTaskFour extends AbstractTest {

    /*
    * Verifies that Skill.jobs exists
    * */
    @Test
    public void testSkillClassHasJobsField () throws ClassNotFoundException {
        Class skillClass = getClassByName("models.Skill");
        Field jobsField = null;

        try {
            jobsField = skillClass.getDeclaredField("jobs");
        } catch (NoSuchFieldException e) {
            fail("Skill class does not have a jobs field");
        }
    }

    /*
    * Verifies that Skill.jobs is of type List (or a subclass of List)
    * */
    @Test
    public void testSkillJobsFieldHasCorrectType () throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class skillClass = getClassByName("models.Skill");
        Method getJobsMethod = skillClass.getMethod("getJobs");
        Skill skill = new Skill();
        Object jobsObj = getJobsMethod.invoke(skill);
        assertTrue(jobsObj instanceof List);
    }

    /*
    * Verifies that Skill.jobs has @ManyToMany with correct mappedBy value
    * */
    @Test
    public void testSkillJobsFieldHasCorrectAnnotation () throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class skillClass = getClassByName("models.Skill");
        Field jobsField = skillClass.getDeclaredField("jobs");
        Annotation annotation = jobsField.getDeclaredAnnotation(ManyToMany.class);
        assertNotNull(annotation);
        Method mappedByMethod = annotation.getClass().getMethod("mappedBy");
        assertEquals("skills", mappedByMethod.invoke(annotation));
    }

    /*
    * Verifies that Job.skills has been refactored to be of the correct type, with mapping annotation
    * */
    @Test
    public void testJobSkillsHasCorrectTypeAndAnnotation () throws ClassNotFoundException, NoSuchFieldException {
        Class jobClass = getClassByName("models.Job");
        Field skillsField = jobClass.getDeclaredField("skills");
        Type skillsFieldType = skillsField.getType();
        assertEquals(List.class, skillsFieldType, "Job.skills should be of type List<Skills>");
        assertNotNull(skillsField.getAnnotation(ManyToMany.class), "Job.skills is missing the correct mapping annotation");
    }

    /*
    * Verifies that after refactoring Job.skills, the non-default constructor
    * and accessors have been updated
    * */
    @Test
    public void testJobSkillsRefactoring () throws ClassNotFoundException, NoSuchMethodException {
        Class jobClass = getClassByName("models.Job");
        try {
            Constructor nonDefaultConstructor = jobClass.getConstructor(Category.class, List.class);
        } catch (NoSuchMethodException e) {
            fail("The non-default constructor has not been refactored to handle the new skills field type");
        }

        Method getSkillsMethod = jobClass.getMethod("getSkills");
        getSkillsMethod.getReturnType().isInstance(List.class);

        try {
            jobClass.getMethod("setSkills", List.class);
        } catch (NoSuchMethodException e) {
            fail("Job.setSkills has not been refactoring to handle the new skills field type");
        }
    }

    /*
    * Verifies that HomeController has an @Autowired skillRepository field
    * */
    @Test
    public void testHomeControllerHasSkillRepository () throws ClassNotFoundException {
        Class homeControllerClass = getClassByName("controllers.HomeController");
        Field skillRepositoryField = null;
        try {
            skillRepositoryField = homeControllerClass.getDeclaredField("skillRepository");
        } catch (NoSuchFieldException e) {
            fail("HomeController should have a skillRepository field");
        }

        assertEquals(SkillRepository.class, skillRepositoryField.getType(), "skillRepository is of incorrect type");
        assertNotNull(skillRepositoryField.getAnnotation(Autowired.class), "skillRepository must be @Autowired");
    }

    /*
    * Verifies that HomeController.processAddJobForm queries skillRepository and sets skills properly
    * */
    @Test
    public void testProcessAddJobFormHandlesSkillsProperly (
            @Mocked SkillRepository skillRepository,
            @Mocked CategoryRepository categoryRepository,
            @Mocked ProductRepository productRepository,
            @Mocked Product product,
            @Mocked Errors errors)
            throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class homeControllerClass = getClassByName("controllers.HomeController");
        Method processAddJobFormMethod = homeControllerClass.getMethod("processAddJobForm", Product.class, Errors.class, Model.class, int.class, List.class);

        new Expectations() {{
            skillRepository.findAllById((Iterable<Integer>) any);
            product.setSkills((List<Skill>) any);
        }};

        Model model = new ExtendedModelMap();
        HomeController homeController = new HomeController();

        Field skillRepositoryField = homeControllerClass.getDeclaredField("skillRepository");
        skillRepositoryField.setAccessible(true);
        skillRepositoryField.set(homeController, skillRepository);

        Field employerRepositoryField = homeControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(homeController, categoryRepository);

        Field jobRepositoryField = homeControllerClass.getDeclaredField("jobRepository");
            jobRepositoryField.setAccessible(true);
            jobRepositoryField.set(homeController, productRepository);

        processAddJobFormMethod.invoke(homeController, product, errors, model, 0, new ArrayList<Skill>());
    }

    /*
    * Verifies that skillRepository and employerRepository fields have been added to ListController
    * */
    @Test
    public void testListControllerHasAutowiredRepositories () throws ClassNotFoundException {
        Class listControllerClass = getClassByName("controllers.ListController");
        Field employerRepositoryField = null;
        Field skillRepositoryField = null;

        try {
            employerRepositoryField = listControllerClass.getDeclaredField("employerRepository");
        } catch (NoSuchFieldException e) {
            fail("ListController must have an employerRepository field");
        }

        assertEquals(CategoryRepository.class, employerRepositoryField.getType());
        assertNotNull(employerRepositoryField.getAnnotation(Autowired.class));

        try {
            skillRepositoryField = listControllerClass.getDeclaredField("skillRepository");
        } catch (NoSuchFieldException e) {
            fail("ListController must have a skillRepository field");
        }

        assertEquals(SkillRepository.class, skillRepositoryField.getType());
        assertNotNull(skillRepositoryField.getAnnotation(Autowired.class));
    }

    /*
    * Verifies that ListController.list sets the correct model attributes using skill/employerRepository objects
    * */
    @Test
    public void testListControllerListMethodSetsFormFieldData (@Mocked Model model, @Mocked SkillRepository skillRepository, @Mocked CategoryRepository categoryRepository) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class listControllerClass = getClassByName("controllers.ListController");
        ListController listController = new ListController();

        new Expectations() {{
            model.addAttribute("employers", any);
            model.addAttribute("skills", any);
            skillRepository.findAll();
            categoryRepository.findAll();
        }};

        Field skillRepositoryField = listControllerClass.getDeclaredField("skillRepository");
        skillRepositoryField.setAccessible(true);
        skillRepositoryField.set(listController, skillRepository);

        Field employerRepositoryField = listControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(listController, categoryRepository);

        listController.list(model);
    }

    @Test
    public void testSqlQuery () throws IOException {
        String queryFileContents = getFileContents("queries.sql");

        Pattern queryPattern = Pattern.compile("SELECT\\s+\\*\\s+FROM\\s+skill" +
                "\\s*(LEFT|INNER)?\\s+JOIN\\s+job_skills\\s+ON\\s+(skill.id\\s+=\\s+job_skills.skills_id|job_skills.skills_id\\s+=\\s+skill.id)" +
                "(\\s*WHERE\\s+job_skills.jobs_id\\s+IS\\s+NOT\\s+NULL)?" +
                "\\s*ORDER\\s+BY\\s+name\\s+ASC;", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher queryMatcher = queryPattern.matcher(queryFileContents);
        boolean queryFound = queryMatcher.find();
        assertTrue(queryFound, "Task 4 SQL query is incorrect. Test your query against your database to find the error.");
    }

}
