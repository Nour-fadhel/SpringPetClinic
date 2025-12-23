package com.nour.petclinic.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest {

    private WebDriver driver;

  @BeforeClass
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");

    driver = new ChromeDriver(options);
    driver.manage().window().setSize(new Dimension(1920, 1080));
    driver.get("http://192.168.49.2:30080/");
}


    @Test
    public void homePageTitleShouldContainPetClinic() {
        driver.get("http://192.168.49.2:30080/");
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("petclinic"));
    }
    @Test
    public void ownersPageShouldContainTable() {
        driver.get("http://192.168.49.2:30080/owners");
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("owners"), "Owners page should contain 'owners' text");
    }
    @Test
    public void vetsPageShouldContainVetsText() {
        driver.get("http://192.168.49.2:30080/vets");
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("veterinarians")
                || pageSource.toLowerCase().contains("vets"),
                "Vets page should mention veterinarians");
    }
    @Test
    public void homeLinkShouldReturnToHomePage() {
        driver.get("http://192.168.49.2:30080/owners");
        driver.findElement(org.openqa.selenium.By.linkText("HOME")).click();
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("petclinic"), "Home link should go back to PetClinic home");
    }
    @Test
    public void oupsPageShouldBeReachable() {
        driver.get("http://192.168.49.2:30080/oups");
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.toLowerCase().contains("something happened")
                || pageSource.toLowerCase().contains("oups"),
                "Oups page should display an error message");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
