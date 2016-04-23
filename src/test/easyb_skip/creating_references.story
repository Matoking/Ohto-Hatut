import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver


description 'User can create a new reference list with a valid name'

scenario "user can create an inproceedings reference with mandatory values", {

    given 'the user is at the inproceedings reference creation page', {
        createDriver();
        getToReferenceCreationPage();
    }

    when 'valid values are given', {
        createInproceedingsReference(1);
    }

    then "user will be redirected to the newly created reference's page", {
        driver.getPageSource().contains("author1").shouldBe true
        driver.getPageSource().contains("title1").shouldBe true
        driver.getPageSource().contains("bookTitle1").shouldBe true
        driver.getPageSource().contains("1901").shouldBe true

    }
}

scenario "user can create an inproceedings reference without giving all the mandatory values", {

    given 'the user is at the inproceedings reference creation page', {
        createDriver();
        getToReferenceCreationPage();
    }

    when 'not all mandatory values are given', {
        createInproceedingsReferenceWithoutAllMandatoryValues(2);
    }

    then "user will be shown the appropriate error message", {
        driver.getPageSource().contains("year is empty!").shouldBe true

    }
}

void createDriver() {
    driver = new HtmlUnitDriver()
}


void getToReferenceCreationPage() {
    driver.get("http://localhost:8081/references/choose")
    element = driver.findElement(By.linkText("Inproceedings reference"))
    element.click();
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

void createInproceedingsReferenceWithoutAllMandatoryValues(int i) {
    element = driver.findElement(By.name("key")); 
    element.sendKeys("key" + i);
    element = driver.findElement(By.name("author")); 
    element.sendKeys("author" + i);
    element = driver.findElement(By.name("title")); 
    element.sendKeys("title" + i);    
    element = driver.findElement(By.name("booktitle")); 
    element.sendKeys("bookTitle" + i);      
    element.submit()
}
