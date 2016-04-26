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

scenario "user can't create an inproceedings reference without giving all the mandatory values", {

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

scenario "user can create a new inproceedings reference with all of the optional values", {

    given 'the user is at the inproceedings reference creation page', {
        createDriver();
        getToReferenceCreationPage();
    }

    when 'valid values are given', {
        createInproceedingsReferenceWithOptionalValues(3);
    }

    then "user will be redirected to the newly created reference's page", {
        driver.getPageSource().contains("author3").shouldBe true
        driver.getPageSource().contains("title3").shouldBe true
        driver.getPageSource().contains("bookTitle3").shouldBe true
        driver.getPageSource().contains("1903").shouldBe true

        driver.getPageSource().contains("editor3").shouldBe true
        driver.getPageSource().contains("volume3").shouldBe true
        driver.getPageSource().contains("series3").shouldBe true
        driver.getPageSource().contains("303").shouldBe true
        driver.getPageSource().contains("series3").shouldBe true
        driver.getPageSource().contains("address3").shouldBe true
        driver.getPageSource().contains("13").shouldBe true
        driver.getPageSource().contains("organization3").shouldBe true
        driver.getPageSource().contains("publisher3").shouldBe true
        driver.getPageSource().contains("note3").shouldBe true
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
    giveMandatoryValues(i)  
    element.submit()
}

void createInproceedingsReferenceWithOptionalValues(int i) {
    giveMandatoryValues(i)
    giveOptionalValues(i)

    element.submit()
}

void editInproceedingsReference(i) {
    createInproceedingsReference(i)
}

void giveMandatoryValues(int i) {
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
}

void giveOptionalValues(int i) {
    element = driver.findElement(By.name("editor")); 
    element.sendKeys("editor" + i);
    element = driver.findElement(By.name("volume")); 
    element.sendKeys("volume" + i);
    element = driver.findElement(By.name("series")); 
    element.sendKeys("series" + i);
    element = driver.findElement(By.name("pages")); 
    element.sendKeys("30" + i);
    element = driver.findElement(By.name("address")); 
    element.sendKeys("address" + i);
    element = driver.findElement(By.name("month")); 
    element.sendKeys("1" + i);
    element = driver.findElement(By.name("organization")); 
    element.sendKeys("organization" + i);
    element = driver.findElement(By.name("publisher")); 
    element.sendKeys("publisher" + i);
    element = driver.findElement(By.name("note")); 
    element.sendKeys("note" + i);
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