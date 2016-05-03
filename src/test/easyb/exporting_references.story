import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.htmlunit.HtmlUnitDriver

description 'User can export a reference list with a name of their choosing'

scenario "user can choose a non-empty reference list to be exported into a Bibtex file", {

    given 'there exists a non-empty reference list', {
        createDriver()
        getToReferenceCreationPage()

        createInproceedingsReference(101)
        
        getToReferenceListCreationPage()
        createReferenceList(101)
        addReferenceToList()
        
    }

    when 'the user gives a name to the export and presses the export button', {
        giveExportName();
    }

    then "user will get the reference list parsed in the Bibtex format", {
        driver.getPageSource().contains("@inproceedings{key101,").shouldBe true
        driver.getPageSource().contains('title = "title101",').shouldBe true
        driver.getPageSource().contains('booktitle = "bookTitle101"').shouldBe true
    }
}

scenario "user can not choose an empty reference list to be exported into a Bibtex file", {

    given 'there exists a non-empty reference list', {
        createDriver()        
        getToReferenceListCreationPage()
        createReferenceList(102)        
    }

    then "user has the option to export the reference list", {
        driver.getPageSource().contains("referenceListToBeExported102").shouldBe true
        driver.getCurrentUrl().contains("referencelists").shouldBe true

        driver.getPageSource().contains("Export references").shouldBe false
    }
}

void createDriver() {
    driver = new HtmlUnitDriver()
}

void getToReferenceCreationPage() {
    driver.get("http://localhost:8081/references/new?type=inproceedings")
}

void getToReferenceListCreationPage() {
    driver.get("http://localhost:8081/referencelists/new")
}

void giveExportName() {
    element = driver.findElement(By.name("name"))
    element.sendKeys("exportName")
    element.submit()
}

void createReferenceList(int i) {
    element = driver.findElement(By.name("name"))
    element.sendKeys("referenceListToBeExported" + i)     
    element.submit()
}

void addReferenceToList() {
    element = driver.findElement(By.name("referenceId"))
    select = new Select(element)
    select.selectByVisibleText("title101")
    element.submit()
}

void createInproceedingsReference(int i) {
    element = driver.findElement(By.name("key")); 
    element.sendKeys("key" + i);
    element = driver.findElement(By.name("author")); 
    element.sendKeys("author" + i);
    element = driver.findElement(By.name("title")); 
    element.sendKeys("title" + i);    
    element = driver.findElement(By.name("booktitle")); 
    element.sendKeys("bookTitle" + i);    
    element = driver.findElement(By.name("year")); 
    element.sendKeys("190" + i);    
    element.submit()
}
