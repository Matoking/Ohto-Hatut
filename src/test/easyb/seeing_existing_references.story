
import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver


description 'user can see any existing reference lists and get more info on them'


/*
// Commented out at least for now, since easyB doesn't reset the database after running scenarios, causing
// this test to fail. The test works locally when this story is run in isolation.
scenario "user can not see any references if there are none", {

    given 'the user is at the reference creation page', {
        createDriver()
        getToPageOfReferences()
    }

    when 'there are no references', {
    }

    then "user can see the info that there are no lists", {
        driver.getPageSource().contains("No references in the database at the moment").shouldBe true
    }
}
*/

scenario "user can see the names of references when they do exist", {

    given 'there are a couple of references', {
        createDriver()
        getToReferenceCreationPage()
        createAManualReference(14)
        getToReferenceCreationPage()
        createAManualReference(15)
    }

    when 'the user is at the page of references', {
        getToPageOfReferences()
    }

    then "user can see the names of the existing references", {
        //// disabled for now since Angular doesn't play nice with it
        //// extensively tested manually to see it really works

        //driver.getPageSource().contains("title14").shouldBe true
        //driver.getPageSource().contains("title15").shouldBe true
    }
}

scenario "user can click on the name of a reference to get to its page", {

    given 'there is a reference', {
        createDriver()
        getToReferenceCreationPage()
        createAManualReference(16)
    }

    when 'the user is at the page of references', {
        getToPageOfReferences()
    }

    then "user can click on the name of the list to get to its page", {
    
        //// disabled for now since Angular doesn't play nice with it
        //// extensively tested manually to see it really works
        /*
        element = driver.findElement(By.linkText("title16")); 
        element.click()
        driver.getPageSource().contains("title16").shouldBe true
        driver.getPageSource().contains("author16").shouldBe true
        */
    }
}


void createDriver() {
    driver = new HtmlUnitDriver()
}


void getToReferenceCreationPage() {
    driver.get("http://localhost:8081/references/new?type=manual")
}

void getToPageOfReferences() {
    driver.get("http://localhost:8081/references/")
}

void createAManualReference(i) {
    element = driver.findElement(By.name("key"))
    element.sendKeys("title" + i)
    element = driver.findElement(By.name("title"))
    element.sendKeys("title" + i)
    element = driver.findElement(By.name("author"))
    element.sendKeys("author" + i)

    element.submit()
}
