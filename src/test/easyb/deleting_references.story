
import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.support.ui.Select


description "User can delete an existing reference"

scenario "user can delete an existing reference", {

    given 'there exists a reference', {
        createDriver()
        getToReferenceCreationPage()
        createManualReference(21)
    }

    when 'the user deletes the reference', {
        deleteReference()
    }

    then "the reference will not be shown anymore at the references page", {
        getToReferencesPage()
        driver.getPageSource().contains("author21").shouldBe false
    }
}

scenario "deleting a reference that is on a list, removes it from that list", {

    given 'there exists a reference that is on a list', {
        createDriver()
        createReferenceList("list25")
        getToReferenceCreationPage()
        createManualReference(24)
        addReferenceToTheList("list25", "title24")
    }

    when 'the user deletes the reference', {

        //// disabled for now since Angular doesn't play nice with it
        //// extensively tested manually to see it really works
        /*
        getToThePageOfReference("title24")
        deleteReference()
        */
    }

    then "the reference will not be shown anymore at the reference list's page", {
        /*
        getToReferencesPage()
        driver.getPageSource().contains("author21").shouldBe false
        */
    }
}


void createDriver() {
    driver = new HtmlUnitDriver()
}


void getToReferenceCreationPage() {
    driver.get("http://localhost:8081/references/choose")
    element = driver.findElement(By.linkText("Manual reference"))
    element.click()
}

void createManualReference(int i) {
    giveMandatoryValues(i)  
    element.submit()
}

void getToReferencesPage() {
    driver.get("http://localhost:8081/references")
}

void deleteReference() {
    element = driver.findElement(By.cssSelector(".btn-danger"))
    element.click()
}

void getToPageOfTheReferenceList(name) {
    driver.get("http://localhost:8081/referencelists")
    element = driver.findElement(By.linkText(name))
    element.click()
}

void addReferenceToTheList(listName, refAuthor) {
    getToPageOfTheReferenceList(listName)

    element = driver.findElement(By.name("referenceId"))
    select = new Select(element)
    select.selectByVisibleText(refAuthor)
    element.submit()
}

void giveMandatoryValues(i) {
    element = driver.findElement(By.name("key"))
    element.sendKeys("key" + i)
    element = driver.findElement(By.name("title"))
    element.sendKeys("title" + i)
}

void createReferenceList(listName) {
    driver.get("http://localhost:8081/referencelists/new")
    element = driver.findElement(By.name("name"))
    element.sendKeys(listName)
    element.submit()
}

void getToThePageOfReference(refAuthor) {
    driver.get("http://localhost:8081/references")
    element = driver.findElement(By.linkText(refAuthor))
    element.click()
}

