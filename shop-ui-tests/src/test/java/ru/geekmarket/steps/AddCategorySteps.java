package ru.geekmarket.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.geekmarket.DriverInitializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddCategorySteps {

    private WebDriver webDriver = null;

    @Given("^I open browser$")
    public void iOpenBrowser() throws Throwable {
        webDriver = DriverInitializer.getDriver();
    }

    @When("^I navigate to login\\.html page$")
    public void iNavigateToLoginHtmlPage() throws Throwable {
        Thread.sleep(3000);
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^I click on login button$")
    public void iClickOnLoginButton() throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.className("btn-primary"));
        webElement.click();
    }

    @When("^I provide username as \"([^\"]*)\" and password as \"([^\"]*)\"$")
    public void iProvideUsernameAsAndPasswordAs(String username, String password) throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("username"));
        webElement.sendKeys(username);
        webElement = webDriver.findElement(By.id("password"));
        webElement.sendKeys(password);
    }

    @When("^I open category page$")
    public void iOpenCategoryPage() throws Throwable {
        Thread.sleep(3000);
        webDriver.get(DriverInitializer.getProperty("login.url"));
    }

    @When("^I click on add category button$")
    public void iClickAddCategoryButton() throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("add_cat_btn"));
        webElement.click();
    }

    @When("^I provide category name as \"([^\"]*)\"$")
    public void IProvideCategoryName(String name) throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(name);
    }

    @When("^I click on submit button$")
    public void iClickSubmitButton() throws Throwable {
        Thread.sleep(3000);
        WebElement webElement = webDriver.findElement(By.className("btn-primary"));
        webElement.click();
    }

    @Then("^I see new category \"([^\"]*)\"$")
    public void iSeeNewCategory(String category) throws Throwable {
        Thread.sleep(3000);
        List<WebElement> webElement = webDriver.findElements(By.className("btn-primary"));
        boolean success = false;
        for (WebElement el: webElement) {
            if (el.getText().equals(category)) {
                success = true;
                break;
            }
        }
        assertThat(success);
    }


    @After
    public void quitBrowser() {
        webDriver.quit();
    }
}
