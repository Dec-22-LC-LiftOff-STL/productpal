package org.launchcode.productpal.persistent;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;
import org.launchcode.productpal.persistent.controllers.SkillController;
import org.launchcode.productpal.persistent.models.Category;
import org.launchcode.productpal.persistent.models.Skill;
import org.launchcode.productpal.persistent.models.data.CategoryRepository;
import org.launchcode.productpal.persistent.models.data.SkillRepository;
import org.launchcode.productpal.persistent.controllers.CategoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by LaunchCode
 */
public class TestTaskTwo extends AbstractTest {

    // --- BEGIN AbstractEntity TESTS --- //

    /*
    * Verifies that AbstractEntity has @MappedSuperclass
    * */
    @Test
    public void testAbstractEntityHasCorrectAnnotation () throws ClassNotFoundException {
        Class abstractEntityClass = getClassByName("models.AbstractEntity");
        Annotation annotation = abstractEntityClass.getAnnotation(MappedSuperclass.class);
        assertNotNull(annotation, "AbstractEntity must have @MappedSuperclass annotation");
    }

    /*
    * Verifies that AbstractEntity.id has correct annotations
    * */
    @Test
    public void testIdFieldHasCorrectAnnotations () throws ClassNotFoundException {
        Class abstractEntityClass = getClassByName("models.AbstractEntity");
        Field idField = null;
        try {
            idField = abstractEntityClass.getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            fail("AbstractEntity does not have an id field");
        }

        Annotation idAnnotation = idField.getAnnotation(Id.class);
        assertNotNull(idAnnotation, "id field must have @Id annotation");

        Annotation generatedValueAnnotation = idField.getAnnotation(GeneratedValue.class);
        assertNotNull(generatedValueAnnotation, "id field must have @GeneratedValue annotation");
    }

    /*
    * Verifies that AbstractEntity.name has correct annotations
    * */
    @Test
    public void testNameFieldHasCorrectAnnotations () throws ClassNotFoundException {
        Class abstractEntityClass = getClassByName("models.AbstractEntity");
        Field nameField = null;
        try {
            nameField = abstractEntityClass.getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            fail("AbstractEntity does not have a name field");
        }

        Annotation sizeAnnotation = nameField.getAnnotation(Size.class);
        assertNotNull(sizeAnnotation, "name field must use @Size to validate input");

        // we allow for either @NotBlank or @NotNull to ensure the field is not empty
        Annotation notEmptyAnnotation = nameField.getAnnotation(NotNull.class);
        if (notEmptyAnnotation == null) {
            notEmptyAnnotation = nameField.getAnnotation(NotBlank.class);
        }

        assertNotNull(notEmptyAnnotation, "name must have an annotation to ensure the field is not empty");
    }

    // --- END AbstractEntity TESTS --- //

    // --- BEGIN Employer TESTS --- //

    /*
    * Verifies that Employer has a location field
    * */
    @Test
    public void testEmployerHasLocationField () throws ClassNotFoundException {
        Class employerClass = getClassByName("models.Employer");
        Field locationField = null;
        try {
            locationField = employerClass.getDeclaredField("location");
        } catch (NoSuchFieldException e) {
            fail("Employer class has no location field");
        }

        Class locationClass = locationField.getType();
        assertEquals(String.class, locationClass);
    }

    /*
    * Verifies that Employer.location has public accessors
    * */
    @Test
    public void testLocationFieldHasPublicAccessors () throws ClassNotFoundException, NoSuchFieldException {
        Class employerClass = getClassByName("models.Employer");
        Field locationField = employerClass.getDeclaredField("location");

        Method getLocationMethod = null;
        try {
            getLocationMethod = employerClass.getDeclaredMethod("getLocation");
        } catch (NoSuchMethodException e) {
            fail("Employer class has no getLocation method");
        }
        int getLocationModifier = getLocationMethod.getModifiers();
        assertEquals(Modifier.PUBLIC, getLocationModifier, "getLocation must be public");

        Method setLocationMethod = null;
        try {
            setLocationMethod = employerClass.getDeclaredMethod("setLocation", String.class);
        } catch (NoSuchMethodException e) {
            fail("Employer class has no setLocation method");
        }

        int setLocationModifier = setLocationMethod.getModifiers();
        assertEquals(Modifier.PUBLIC, setLocationModifier, "setLocation must be public");
    }

    /*
    * Verifies that Employer.location has correct validation annotations
    * */
    @Test
    public void testLocationHasCorrectValidationAnnotations () throws ClassNotFoundException, NoSuchFieldException {
        Class employerClass = getClassByName("models.Employer");
        Field locationField = employerClass.getDeclaredField("location");

        Annotation sizeAnnotation = locationField.getAnnotation(Size.class);
        assertNotNull(sizeAnnotation, "location field must use @Size to validate input");

        // we allow for either @NotBlank or @NotNull to ensure the field is not empty
        Annotation notEmptyAnnotation = locationField.getAnnotation(NotNull.class);
        if (notEmptyAnnotation == null) {
            notEmptyAnnotation = locationField.getAnnotation(NotBlank.class);
        }

        assertNotNull(notEmptyAnnotation, "location must have an annotation to ensure the field is not empty");
    }

    /*
    * Verifies that Employer has the persistence annotation
    * */
    @Test
    public void testEmployerHasPersistenceAnnotation () throws ClassNotFoundException {
        Class employerClass = getClassByName("models.Employer");
        Annotation entityAnnotation = employerClass.getAnnotation(Entity.class);
        assertNotNull(entityAnnotation, "Employer must have the @Entity persistence annotation");
    }

    /*
    * Verifies that Employer has a no-arg/default constructor
    * */
    @Test
    public void testEmployerHasDefaultConstructor () throws ClassNotFoundException {
        Class employerClass = getClassByName("models.Employer");
        try {
            Constructor defaultConstructor = employerClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fail("Employer has no no-arg/default constructor");
        }
    }

    // --- END Employer TESTS --- //

    // --- START Skill TESTS --- //

    /*
     * Verifies that Skill has a description field
     * */
    @Test
    public void testSkillHasDescriptionField () throws ClassNotFoundException {
        Class skillClass = getClassByName("models.Skill");
        Field descriptionField = null;
        try {
            descriptionField = skillClass.getDeclaredField("description");
        } catch (NoSuchFieldException e) {
            fail("Skill class has no description field");
        }

        Class descriptionClass = descriptionField.getType();
        assertEquals(String.class, descriptionClass);
    }

    /*
     * Verifies that Skill.description has public accessors
     * */
    @Test
    public void testDescriptionFieldHasPublicAccessors () throws ClassNotFoundException, NoSuchFieldException {
        Class skillClass = getClassByName("models.Skill");
        Field descriptionField = skillClass.getDeclaredField("description");

        Method getDescriptionMethod = null;
        try {
            getDescriptionMethod = skillClass.getDeclaredMethod("getDescription");
        } catch (NoSuchMethodException e) {
            fail("Skill class has no getDescription method");
        }
        int getDescriptionModifier = getDescriptionMethod.getModifiers();
        assertEquals(Modifier.PUBLIC, getDescriptionModifier, "getDescription must be public");

        Method setDescriptionMethod = null;
        try {
            setDescriptionMethod = skillClass.getDeclaredMethod("setDescription", String.class);
        } catch (NoSuchMethodException e) {
            fail("Skill class has no setDescription method");
        }

        int setDescriptionModifier = setDescriptionMethod.getModifiers();
        assertEquals(Modifier.PUBLIC, setDescriptionModifier, "setDescription must be public");
    }

    /*
     * Verifies that Skill has the persistence annotation
     * */
    @Test
    public void testSkillHasPersistenceAnnotation () throws ClassNotFoundException {
        Class skillClass = getClassByName("models.Skill");
        Annotation entityAnnotation = skillClass.getAnnotation(Entity.class);
        assertNotNull(entityAnnotation, "Skill must have the @Entity persistence annotation");
    }

    /*
     * Verifies that Skill has a no-arg/default constructor
     * */
    @Test
    public void testSkillHasDefaultConstructor () throws ClassNotFoundException {
        Class skillClass = getClassByName("models.Skill");
        try {
            Constructor defaultConstructor = skillClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fail("Skill has no no-arg/default constructor");
        }
    }

    // --- END Skill TESTS --- //

    // --- BEGIN DATA LAYER TESTS --- //

    /*
    * Verifies that EmployerRepository exists
    * */
    @Test
    public void testEmployerRepositoryExists () {
        try {
            Class employerRepositoryClass = getClassByName("models.data.EmployerRepository");
        } catch (ClassNotFoundException e) {
            fail("EmployerRepository does not exist");
        }
    }

    /*
     * Verifies that EmployerRepository implements CrudRepository
     * */
    @Test
    public void testEmployerRepositoryImplementsJpaInterface () throws ClassNotFoundException {
        Class employerRepositoryClass = getClassByName("models.data.EmployerRepository");
        Class[] interfaces = employerRepositoryClass.getInterfaces();
        assertTrue(Arrays.asList(interfaces).contains(CrudRepository.class), "EmployerRepository must implement CrudRepository");
    }

    /*
     * Verifies that EmployerRepository has @Repository
     * */
    @Test
    public void testEmployerRepositoryHasRepositoryAnnotation () throws ClassNotFoundException {
        Class employerRepositoryClass = getClassByName("models.data.EmployerRepository");
        Annotation annotation = employerRepositoryClass.getAnnotation(Repository.class);
    }

    /*
     * Verifies that SkillRepository exists
     * */
    @Test
    public void testSkillRepositoryExists () {
        try {
            Class skillRepositoryClass = getClassByName("models.data.SkillRepository");
        } catch (ClassNotFoundException e) {
            fail("SkillRepository does not exist");
        }
    }

    /*
     * Verifies that SkillRepository implements CrudRepository
     * */
    @Test
    public void testSkillRepositoryImplementsJpaInterface () throws ClassNotFoundException {
        Class skillRepositoryClass = getClassByName("models.data.SkillRepository");
        Class[] interfaces = skillRepositoryClass.getInterfaces();
        assertTrue(Arrays.asList(interfaces).contains(CrudRepository.class), "SkillRepository must implement CrudRepository");
    }

    /*
     * Verifies that SkillRepository has @Repository
     * */
    @Test
    public void testSkillRepositoryHasRepositoryAnnotation () throws ClassNotFoundException {
        Class skillRepositoryClass = getClassByName("models.data.SkillRepository");
        Annotation annotation = skillRepositoryClass.getAnnotation(Repository.class);
    }

    // --- END DATA LAYER TESTS --- //

    // --- BEGIN CONTROLLER TESTS --- //

    /*
    * Verifies that the employerRepository field is correctly defined
    * */
    @Test
    public void testEmployerRepositoryDefinition () throws ClassNotFoundException {
        Class employerController = getClassByName("controllers.EmployerController");
        Field employerRepositoryField = null;

        try {
            employerRepositoryField = employerController.getDeclaredField("employerRepository");
        } catch (NoSuchFieldException e) {
            fail("EmployerController does not have an employerRepository field");
        }

        assertEquals(CategoryRepository.class, employerRepositoryField.getType(), "employerRepository must be of type EmployerRepository");
        assertNotNull(employerRepositoryField.getAnnotation(Autowired.class), "employerRepository must have the @Autowired annotation");
    }

    /*
    * Verifies that EmployerController.index is properly defined
    * */
    @Test
    public void testEmployerControllerIndexMethodDefinition (@Mocked CategoryRepository categoryRepository) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class employerControllerClass = getClassByName("controllers.EmployerController");
        Method indexMethod = null;

        // Verify that the index method exists
        try {
            indexMethod = employerControllerClass.getMethod("index", Model.class);
        } catch (NoSuchMethodException e) {
            fail("EmployerController must have an index method that takes a parameter of type Model");
        }

        Annotation annotation = indexMethod.getDeclaredAnnotation(RequestMapping.class);

        // Verify that index has a routing annotation. We need to accommodate
        // both @RequestMapping and @GetMapping.
        if (annotation == null) {
            annotation = indexMethod.getDeclaredAnnotation(GetMapping.class);
        }

        assertNotNull(annotation, "index method must have a routing annotation");

        Method annotationValueMethod = annotation.getClass().getMethod("value");
        String[] values = ((String[]) annotationValueMethod.invoke(annotation));
        assertEquals(1, values.length, "The routing annotation for index must have a value");
        assertEquals("", values[0], "The value parameter for the routing annotation must be the empty string");

//        // Verify that index calls employerRepository.findAll()
        new Expectations() {{
            categoryRepository.findAll();
        }};

        Model model = new ExtendedModelMap();
        CategoryController categoryController = new CategoryController();
        Field employerRepositoryField = employerControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(categoryController, categoryRepository);
        indexMethod.invoke(categoryController, model);
    }

    /*
    * Verify that processAddEmployerForm saves a new employer to the database
    * */
    @Test
    public void testNewEmployerIsSaved (@Mocked CategoryRepository categoryRepository, @Mocked Errors errors) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class employerControllerClass = getClassByName("controllers.EmployerController");
        Method processAddEmployerFormMethod = employerControllerClass.getMethod("processAddEmployerForm", Category.class, Errors.class, Model.class);
        Method saveMethod = CategoryRepository.class.getMethod("save", Object.class);

        Category category = new Category();
        category.setDescription("St.Louis City");
        category.setName("LaunchCode");

        new Expectations() {{
            saveMethod.invoke(categoryRepository, category);
        }};

        Model model = new ExtendedModelMap();
        CategoryController categoryController = new CategoryController();
        Field employerRepositoryField = employerControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(categoryController, categoryRepository);
        processAddEmployerFormMethod.invoke(categoryController, category, errors, model);
    }

    /*
    * Verifies that displayViewEmployer calls findById to retrieve an employer object
    * */
    @Test
    public void testDisplayViewEmployerCallsFindById (@Mocked CategoryRepository categoryRepository) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class employerControllerClass = getClassByName("controllers.EmployerController");
        Method displayViewEmployerMethod = employerControllerClass.getMethod("displayViewEmployer", Model.class, int.class);

        new Expectations() {{
           categoryRepository.findById(1);
        }};

        Model model = new ExtendedModelMap();
        CategoryController categoryController = new CategoryController();
        Field employerRepositoryField = employerControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(categoryController, categoryRepository);
        displayViewEmployerMethod.invoke(categoryController, model, 1);
    }

    /*
     * Verifies that the skillRepository field is correctly defined
     * */
    @Test
    public void testSkillRepositoryDefinition () throws ClassNotFoundException {
        Class skillController = getClassByName("controllers.SkillController");
        Field skillRepositoryField = null;

        try {
            skillRepositoryField = skillController.getDeclaredField("skillRepository");
        } catch (NoSuchFieldException e) {
            fail("SkillController does not have an skillRepository field");
        }

        assertEquals(SkillRepository.class, skillRepositoryField.getType(), "skillRepository must be of type SkillRepository");
        assertNotNull(skillRepositoryField.getAnnotation(Autowired.class), "skillRepository must have the @Autowired annotation");
    }

    /*
     * Verifies that SkillController.index is properly defined
     * */
    @Test
    public void testSkillControllerIndexMethodDefinition (@Mocked SkillRepository skillRepository) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class skillControllerClass = getClassByName("controllers.SkillController");
        Method indexMethod = null;

        // Verify that the index method exists
        try {
            indexMethod = skillControllerClass.getMethod("index", Model.class);
        } catch (NoSuchMethodException e) {
            fail("SkillController must have an index method that takes a parameter of type Model");
        }

        Annotation annotation = indexMethod.getDeclaredAnnotation(RequestMapping.class);

//        // Verify that index has a routing annotation. We need to accommodate
//        // both @RequestMapping and @GetMapping.
        if (annotation == null) {
            annotation = indexMethod.getDeclaredAnnotation(GetMapping.class);
        }

        assertNotNull(annotation, "index method must have a routing annotation");

        Method annotationValueMethod = annotation.getClass().getMethod("value");
        String[] values = ((String[]) annotationValueMethod.invoke(annotation));
        assertEquals(1, values.length, "The routing annotation for index must have a value");
        assertEquals("", values[0], "The value parameter for the routing annotation must be the empty string");

//        // Verify that index calls skillRepository.findAll()
        new Expectations() {{
            skillRepository.findAll();
        }};

        Model model = new ExtendedModelMap();
        SkillController skillController = new SkillController();
        Field skillRepositoryField = skillControllerClass.getDeclaredField("skillRepository");
        skillRepositoryField.setAccessible(true);
        skillRepositoryField.set(skillController, skillRepository);
        indexMethod.invoke(skillController, model);
    }

    /*
     * Verify that processAddSkillForm saves a new skill to the database
     * */
    @Test
    public void testNewSkillIsSaved (@Mocked SkillRepository skillRepository, @Mocked Errors errors) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class skillControllerClass = getClassByName("controllers.SkillController");
        Method processAddSkillFormMethod = skillControllerClass.getMethod("processAddSkillForm", Skill.class, Errors.class, Model.class);
        Method saveMethod = SkillRepository.class.getMethod("save", Object.class);

        Skill skill = new Skill();
        skill.setName("Java");

        new Expectations() {{
            saveMethod.invoke(skillRepository, skill);
        }};

        Model model = new ExtendedModelMap();
        SkillController skillController = new SkillController();
        Field skillRepositoryField = skillControllerClass.getDeclaredField("skillRepository");
        skillRepositoryField.setAccessible(true);
        skillRepositoryField.set(skillController, skillRepository);
        processAddSkillFormMethod.invoke(skillController, skill, errors, model);
    }

    /*
     * Verifies that displayViewSkill calls findById to retrieve an skill object
     * */
//    @Test
    public void testDisplayViewSkillCallsFindById (@Mocked SkillRepository skillRepository) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        Class skillControllerClass = getClassByName("controllers.SkillController");
        Method displayViewSkillMethod = skillControllerClass.getMethod("displayViewSkill", Model.class, int.class);

        new Expectations() {{
            skillRepository.findById(1);
        }};

        Model model = new ExtendedModelMap();
        SkillController skillController = new SkillController();
        Field skillRepositoryField = skillControllerClass.getDeclaredField("skillRepository");
        skillRepositoryField.setAccessible(true);
        skillRepositoryField.set(skillController, skillRepository);
        displayViewSkillMethod.invoke(skillController, model, 1);
    }

    // --- END CONTROLLER TESTS --- //

    /*
    * Tests SQL query for task 2
    * */
    @Test
    public void testSqlQuery() throws IOException {
        String queryFileContents = getFileContents("queries.sql");

        Pattern queryPattern = Pattern.compile("SELECT\\s+name\\s+FROM\\s+employer\\s+WHERE\\s+location\\s+=\\s+\"St.\\s+Louis\\s+City\";", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher queryMatcher = queryPattern.matcher(queryFileContents);
        boolean queryFound = queryMatcher.find();
        assertTrue(queryFound, "Task 2 SQL query is incorrect. Test your query against your database to find the error.");
    }
}
