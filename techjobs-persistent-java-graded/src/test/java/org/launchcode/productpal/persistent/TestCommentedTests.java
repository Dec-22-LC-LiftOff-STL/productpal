package org.launchcode.productpal.persistent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by LaunchCode
 */
public class TestCommentedTests extends AbstractTest {

    @Test
    public void testTaskTwoTestNumber () throws ClassNotFoundException {
        Class testTaskTwoClass = getClassByName("TestTaskTwo");
        int numTests = testTaskTwoClass.getMethods().length;
        assertEquals(36, numTests);
    }

    @Test
    public void testTaskThreeTestNumber () throws ClassNotFoundException {
        Class testTaskThreeClass = getClassByName("TestTaskThree");
        int numTests = testTaskThreeClass.getMethods().length;
        assertEquals(16, numTests);
    }

    @Test
    public void testTaskFourTestNumber () throws ClassNotFoundException {
        Class testTaskTwoClass = getClassByName("TestTaskFour");
        int numTests = testTaskTwoClass.getMethods().length;
        assertEquals(19, numTests);
    }
}
