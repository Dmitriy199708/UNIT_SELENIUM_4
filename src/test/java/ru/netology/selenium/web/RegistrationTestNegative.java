package ru.netology.selenium.web;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTestNegative {
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
    void registeringUserWithAnInvalidCity() {
        $x("//span[@data-test-id='city']//input").setValue("Крокус");
        String planningData = generateDate(3, "dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date']//input").setValue(planningData);
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий Лютиков");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='city'] //span[@class='input__sub']")
                .shouldBe(visible)
                .shouldBe(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void registeringUserWithAnInvalidCityLatinTerms() {
        $x("//span[@data-test-id='city']//input").setValue("Ggdqdc");
        String planningData = generateDate(3, "dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date']//input").setValue(planningData);
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий Лютиков");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='city'] //span[@class='input__sub']")
                .shouldBe(visible)
                .shouldBe(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void userRegistrationWithoutCity() {
        String planningData = generateDate(3, "dd.MM.yyyy");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date']//input").setValue(planningData);
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий Лютиков");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='city'] //span[@class='input__sub']")
                .shouldBe(visible)
                .shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void registeringUserWithAnInvalidDate() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2023");
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='date'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void userRegistrationWithDateInmmddyyFormat() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.24");
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='date'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void userRegistrationWithDateOfFebruary30() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("30.02.2024");
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='date'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void userRegistrationWithDateBlankDate() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='name']//input").setValue("Иванов-Иванович Иван");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='date'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void registeringUserWithAnInvalidName() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий1");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='name'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void registeringUserWitNameInLatin() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='name']//input").setValue("Gdergv");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='name'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void registeringUserWithAnEmptyName() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='name'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Поле обязательно для заполнения "));
    }

    @Test
    void registeringUserWithPhoneNumberStartingWith8() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='name']//input").setValue("Иванов-аглы Дмитрий");
        $x("//span[@data-test-id='phone']//input").setValue("879200077999");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='phone'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void userRegistrationForNonExistentNumber() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//td[@class='calendar__day' and text()='17'] ").click();
        String data = $x("//span[@data-test-id='date'] //input[@type='tel']").val();
        $x("//span[@data-test-id='name']//input").setValue("Иванов-аглы Дмитрий");
        $x("//span[@data-test-id='phone']//input").setValue("+70000000000");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + data));
    }

    @Test
    void registeringUserWithAnInvalidPhone() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий");
        $x("//span[@data-test-id='phone']//input").setValue("+792000779991");
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='phone'] //span[@class='input__sub']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }


    @Test
    void userRegistrationWithoutAgreeingToTheTermsAndConditions() {
        $x("//span[@data-test-id='city']//input").setValue("Москва");
        $x("//span[@data-test-id='date']//input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $x("//span[@data-test-id='date'] //input[@class='input__control']").sendKeys("20.01.2024");
        $x("//span[@data-test-id='name']//input").setValue("Дмитрий");
        $x("//span[@data-test-id='phone']//input").setValue("+79200077999");
        $x("//span[@class='button__text']").click();
        $x("//label[@data-test-id='agreement'] //span[@class='checkbox__text']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldCardDeliverySuccessCityList() {
        // Выбор даты на неделю вперёд, начиная от текущей даты, через инструмент календаря:
        LocalDate dateNow = LocalDate.now();
        LocalDate plusWeek = dateNow.plusWeeks(1);
        LocalDate availableDay = dateNow.plusDays(3);

        boolean isSameYear = dateNow.getYear() == plusWeek.getYear();
        boolean isMonthDiff = dateNow.getMonthValue() < plusWeek.getMonthValue();
        boolean availableStartSameMonth = availableDay.getMonthValue() == dateNow.getMonthValue();

        boolean needToChangeMonth = availableStartSameMonth && (!isSameYear || isMonthDiff);

        $("button[type='button']").click();

        if (needToChangeMonth) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }

        $$("td.calendar__day").find(text(String.valueOf(plusWeek.getDayOfMonth()))).click();
        // Остальные поля
        $("input[type='text']").setValue("Мо");
        $(withText("Москва")).click();
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $x("//label[@data-test-id='agreement'] //span[@class='checkbox__text']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}

