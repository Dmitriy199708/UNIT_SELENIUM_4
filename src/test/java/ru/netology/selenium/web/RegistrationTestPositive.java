package ru.netology.selenium.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationTestPositive {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("window-size=1800x900");
        driver = new ChromeDriver(options);
        openSite();
    }

    void openSite() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterByAccountNumberDOMModification() {
        $x("//span[@data-test-id='city']//input").setValue("Нижний Новгород");
        String planningData = generateDate(3, "dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date']//input").setValue(planningData);
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий Лютиков");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + planningData));
    }

    @Test
    void successfulUserRegistrationOnTheSelectedDate() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        String date = ("22.01.2024");
        $x("//span[@data-test-id='date']//input").setValue(date);
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void successfulUserRegistrationOnTheSelectedDateFromTheCalendar() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //span[@class='icon-button__content']").click();
        $x("//td[@class='calendar__day' and @data-day='1705438800000'] ").click();
        String data = $x("//span[@data-test-id='date'] //input[@type='tel']").val();
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + data));
    }

    @Test
    void successfulUserRegistrationForTheSelectedCityFromTheDropDownList() {
        $x("//span[@data-test-id='city']//input").setValue("Ке");
        $x("//span[@class='menu-item__control' and text()='Кемерово']").click();
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        String date = ("22.01.2024");
        $x("//span[@data-test-id='date']//input").setValue(date);
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + date));
    }
}

