import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver


description 'User can create a new reference list with a valid name'

scenario "user can create a new reference list with a valid name", {

    given 'the user is at the reference list creation page', {
        getToReferenceListCreationPage();
    }

    when 'a valid name is given to the list', {
        submitName("list1")
    }

    then "user will be redirected to the newly created list's page", {
        driver.getPageSource().contains("list1").shouldBe true
    }
}

scenario "user cannot create a list without giving name", {
    given "the user is at the reference creation page", {
        getToReferenceListCreationPage()
    }

    when "no name is given to the list", {
        submitName("")
    }

    then "error message will be shown", {
        driver.getPageSource().contains("may not be empty").shouldBe true
    }
}

void getToReferenceListCreationPage() {
    driver = new HtmlUnitDriver()
    driver.get("http://localhost:8081/referencelists/new")
}

void submitName(String name) {
    element = driver.findElement(By.name("name"))
    element.sendKeys(name)
    element.submit()
}