
import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver

description 'user can see any existing reference lists and get more info on them'

scenario "user can not see any lists if there are none", {

    given 'the user is at the reference creation page', {
        createDriver()
        getToPageOfReferenceLists();
    }

    when 'there are no lists', {
    }

    then "user can see the info that there are no lists", {
        driver.getPageSource().contains("No reference lists in the database at the moment").shouldBe true
    }
}

scenario "user can see the names of reference lists when they do exist", {

    given 'there are a couple of reference lists', {
        createDriver()
        getToReferenceListCreationPage()
        createReferenceList(5)
        getToReferenceListCreationPage()
        createReferenceList(6)
    }

    when 'the user is at the page of reference lists', {
        getToPageOfReferenceLists()
    }

    then "user can see the names of the existing lists", {
        driver.getPageSource().contains("list5").shouldBe true
        //driver.getPageSource().contains("list6").shouldBe true
    }
}

scenario "user can click on the name of a reference list to get to its page", {

    given 'there is a reference list', {
        createDriver()
        getToReferenceListCreationPage()
        createReferenceList(7)
    }

    when 'the user is at the page of reference lists', {
        getToPageOfReferenceLists()
    }

    then "user can click on the name of the list to get to its page", {
        element = driver.findElement(By.linkText("list7")); 
        element.click()
        driver.getPageSource().contains("list7").shouldBe true
        driver.getPageSource().contains("No references in the database at the moment for you to add").shouldBe true
    }
}


void createDriver() {
    driver = new HtmlUnitDriver()
}


void getToReferenceListCreationPage() {
    driver.get("http://localhost:8081/referencelists/new")
}

void createReferenceList(int i) {
    element = driver.findElement(By.name("name")); 
    element.sendKeys("list" + i);      
    element.submit()
}

void getToPageOfReferenceLists() {
    driver.get("http://localhost:8081/referencelists/")
}

