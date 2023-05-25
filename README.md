# Проект по автоматизации тестирования для M3o
<a target="_blank" href="https://m3o.com//">Cайт M3o</a>


## :pushpin: <a id="list"></a> Содержание:

* <a href="#tools">Технологии и инструменты</a>

* <a href="#cases">Реализованные проверки</a>

* <a href="#console">Запуск из терминала</a>

* <a href="#jenkins">Сборка в Jenkins</a>

* <a href="#allure">Allure отчеты</a>

* <a href="#telegram">Отчёт в Telegram</a>

* <a href="#testops">Интеграция с Allure TestOps</a>

* <a href="#jira">Интеграция с Jira</a>
* 
## :hammer_and_wrench: <a id="tools"></a> Технологии и инструменты
<p align="center">
<a href="https://www.jetbrains.com/idea/"><img src="images/Idea.svg" width="50" height="50"  alt="IDEA"/></a>
<a href="https://www.java.com/"><img src="images/Java.svg" width="50" height="50"  alt="Java"/></a>
<a href="https://github.com/"><img src="images/GitHub.svg" width="50" height="50"  alt="Github"/></a>
<a href="https://junit.org/junit5/"><img src="images/Junit5.svg" width="50" height="50"  alt="JUnit 5"/></a>
<a href="https://gradle.org/"><img src="images/Gradle.svg" width="50" height="50"  alt="Gradle"/></a>
<a href="https://selenide.org/"><img src="images/Selenide.svg" width="50" height="50"  alt="Selenide"/></a>
<a href="https://github.com/allure-framework/allure2"><img src="images/Allure.svg" width="50" height="50"  alt="Allure"/></a>
<a href="https://www.jenkins.io/"><img src="images/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a>
</p>


## :scroll: Реализованные проверки

## :computer: Запуск из терминала
```bash
 gradle clean test
```

## <a href="https://www.jenkins.io/"><img src="images/Jenkins.svg" width="50" height="50"  alt="Jenkins"/></a> Сборка в [Jenkins](https://jenkins.autotests.cloud/job/018_klyul_Diplom_API/)

Для запуска сборки необходимо нажать кнопку <code><strong>*Собрать*</strong></code>.
<a href="https://https://jenkins.autotests.cloud/job/018_klyul_Diplom_API/#"><img src="images/jenkins.png" alt="jenkins"/></a>
<p align="center">
</p>

## <a href="https://github.com/allure-framework/allure2"><img src="images/Allure.svg" width="50" height="50"  alt="Allure"/></a> Allure отчеты
:information_source: По итогам сборки в Jenkins собирается отчет в Allure Report
> Для просмотра необходимо залогиниться в Jenkins
* Подключено добавление в отчет:
    - исходного кода страницы
    - логов браузера
<p align="center">
<img src="images/allurereport1.png">
<img src="images/allurereport2.png">
</p>

## <img src="images/Telegram.svg" width="50" height="50"> Отчёт в Telegram 

После завершения сборки сообщение с основными результатами прогона тестов отправляется через созданный бот в выбранный телеграм-канал
<p align="center">
<img src="images/tgbot.png">

