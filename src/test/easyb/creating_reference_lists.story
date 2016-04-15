
import ohtuhatut.*

import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver


description 'User can create a new reference list with a valid name'

scenario "user can create a new reference list with a valid name", {

    given 'the user is at the reference creation page', {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8080/referencelists/new")
    }

    when 'a valid name is given to the list', {
        element = driver.findElement(By.name("name"))
        element.sendKeys("list1")
        element.submit()
    }

    then "user will be redirected to the newly created list's page", {
        driver.getPageSource().contains("list1").shouldBe true
    }
}

scenario "user cannot create a list without giving name", {
    given "the user is at the reference creation page", {
        driver = new HtmlUnitDriver()
        driver.get("http://localhost:8080/referencelists/new")
    }

    when "no name is given to the list", {
        element = driver.findElement(By.name("name"))
        element.submit()
    }

    then "error message will be shown", {
        driver.getPageSource().contains("may not be empty").shouldBe true
    }
}