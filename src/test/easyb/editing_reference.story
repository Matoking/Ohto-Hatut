import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver


description "User can edit an existing reference's info"

scenario "user can edit an existing reference and save it with valid mandatory values", {

    given 'there exists a reference', {
        createDriver()
        getToReferenceCreationPage()
        createInproceedingsReference(17)
    }

    when 'info is changed with valid values', {
        getToReferenceEditingPage()
        editInproceedingsReference(18)
    }

    then "user will be redirected to the just edited reference's page", {
        driver.getPageSource().contains("author18").shouldBe true
        driver.getPageSource().contains("title18").shouldBe true
        driver.getPageSource().contains("bookTitle18").shouldBe true
        driver.getPageSource().contains("19018").shouldBe true
    }
}

scenario "user can not save an edited reference if mandatory values were not given", {

    given 'there exists a reference', {
        createDriver()
        getToReferenceCreationPage()
        createInproceedingsReference(17)
    }

    when 'info is changed with some mandatory values missing', {
        getToReferenceEditingPage()
        
        editInproceedingsReferenceLeavingOutSomeValues(18)
    }

    then "user will be shown the appropriate error message", {
        driver.getPageSource().contains("title and booktitle are empty!").shouldBe true
    }
}

void createDriver() {
    driver = new HtmlUnitDriver()
}


void getToReferenceCreationPage() {
    driver.get("http://localhost:8081/references/choose")
    element = driver.findElement(By.linkText("Inproceedings reference"))
    element.click()
}

void createInproceedingsReference(int i) {
    giveMandatoryValues(i)  
    element.submit()
}

void getToReferenceEditingPage() {
    element = driver.findElement(By.linkText("Edit"))
    element.click();
}

void editInproceedingsReference(i) {
    createInproceedingsReference(i)
}

void editInproceedingsReferenceLeavingOutSomeValues(i) {
    element = driver.findElement(By.name("key"))
    element.sendKeys("key" + i)
    element = driver.findElement(By.name("author"))
    element.sendKeys("author" + i)
    element = driver.findElement(By.name("title"))
    element.clear()
    element = driver.findElement(By.name("booktitle"))
    element.clear()
    element = driver.findElement(By.name("year")); 
    element.sendKeys("190" + i);

    element.submit()
}

void giveMandatoryValues(i) {
    element = driver.findElement(By.name("key"))
    element.sendKeys("key" + i)
    element = driver.findElement(By.name("author"))
    element.sendKeys("author" + i)
    element = driver.findElement(By.name("title"))
    element.sendKeys("title" + i)
    element = driver.findElement(By.name("booktitle"))
    element.sendKeys("bookTitle" + i)
    element = driver.findElement(By.name("year")); 
    element.sendKeys("190" + i);
}
