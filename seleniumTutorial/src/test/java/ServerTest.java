import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerTest {
    private Server server;
    private WebDriver driver;
    private WebElement todoInput;

    private final List<String> todoTestList = Arrays.asList(
            "Learn Selenium",
            "Understand and modify code base",
            "Static Analysis with IntelliJ",
            "Code Profiling with YourKit",
            "Dependency Management",
            "Unit testing with Junit");

    @BeforeEach
    void setup() throws IOException {
        server = new Server();
        server.start();

        driver = new ChromeDriver();
        driver.get("http://localhost:" + server.getPort());

        todoInput = driver.findElement(By.tagName("input"));

        for(String todo: todoTestList) {
            submitTodo(todo);
        }

    }

    @Test
    void testAddTodos(){
        List<WebElement> foundTodoElements = driver.findElements(By.className("todo-text"));
        Assertions.assertEquals(todoTestList.size(), foundTodoElements.size());

        List<String> foundTodoStrings = getTextForElements(foundTodoElements);

        for(String expectedTodo : todoTestList){
            Assertions.assertTrue(foundTodoStrings.contains(expectedTodo), "Missing todo: " + expectedTodo);
        }
    }
    @Test
    void testMarkAndShowCompleted(){
        List<String> expectedCompletedItems = Arrays.asList("Learn Selenium", "Code Profiling with YourKit");

        for (String completedItem : expectedCompletedItems) {
            WebElement completedItemCheckbox = findCheckboxForTodoItemByText(completedItem);
            Assertions.assertNotNull(completedItemCheckbox);
            completedItemCheckbox.click();
        }

        WebElement showCompletedButton = driver.findElement(By.className("todo-text"));
        Assertions.assertNotNull(showCompletedButton);
        showCompletedButton.click();

        List<WebElement> displayedTodoElements = driver.findElements(By.className("todo-text"));
        Assertions.assertEquals(expectedCompletedItems.size(), displayedTodoElements.size() - 4);

        List<String> displayedElementText = getTextForElements(displayedTodoElements);
        for (String expectedCompletedItem : expectedCompletedItems) {
            Assertions.assertTrue(displayedElementText.contains(expectedCompletedItem), "Expected item "+
                    expectedCompletedItem + " not displayed");
        }
    }

    @Test
    void testDeleteCompleted(){

        for (String completedItem : todoTestList) {
            WebElement completedItemCheckbox = findCheckboxForTodoItemByText(completedItem);
            Assertions.assertNotNull(completedItemCheckbox);
            completedItemCheckbox.click();
        }

        WebElement showCompletedButton = driver.findElement(By.id("show-completed"));
        Assertions.assertNotNull(showCompletedButton);
        showCompletedButton.click();

        List<WebElement> displayedTodoElements = driver.findElements(By.className("todo-text"));
        Assertions.assertEquals(todoTestList.size(), displayedTodoElements.size());


        WebElement deleteCompletedButton = driver.findElement(By.id("delete-completed"));
        Assertions.assertNotNull(deleteCompletedButton);
        deleteCompletedButton.click();

        List<WebElement> foundTodoElements = driver.findElements(By.className("todo-text"));
        Assertions.assertEquals(0, foundTodoElements.size());


    }



    private WebElement findCheckboxForTodoItemByText(String todoItemText){
        String xpathExpression = String.format("//*[contains(text(), '%s')]/parent::li", todoItemText);
        WebElement liElement = driver.findElement(By.xpath(xpathExpression));
        return liElement.findElement(By.className("completed-checkbox"));
    }
    private List<String> getTextForElements(List<WebElement> webElements){
        List<String> strings = new ArrayList<>(webElements.size());
        for (WebElement webElement : webElements) {
            strings.add(webElement.getText());
        }
        return strings;
    }
    private void submitTodo(String todoText){
        todoInput.sendKeys(todoText);
        todoInput.submit();

        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        todoInput.clear();


    }
    @AfterEach
    void tearDown(){

        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        driver.quit();
        server.stop();
    }



}
