package org.example.transportation.controller;
import org.example.transportation.service.GoodService;
import org.example.transportation.entity.Good;

//Основная цель следования принципам MVC — отделить реализацию бизнес-логики приложения (модели) от ее визуализации (вида).
// Такое разделение повышает возможность повторного использования кода.
//AppController — это контроллер в архитектуре MVC (Model-View-Controller), реализованный с использованием Spring MVC.
// Контроллер отвечает за обработку HTTP-запросов, взаимодействие с бизнес-логикой и возврат соответствующих представлений
// (например, HTML-страниц).



import java.util.List;  // Импортируем коллекцию (класс списков)
// Аннотация @Autowired используется в Spring Framework для
// переопределения и автоматического внедрения зависимостей (dependency injection)
//связываем все зависимости
import org.springframework.beans.factory.annotation.Autowired;
//// Аннотация @Param используется в Spring Data для параметризации
//методов репозитория. Она указывает, что параметр метода является параметром запроса, который будет
// использован в SQL запросе или другом запросе к базе данных.
//привязываем наши параметры передачи sql запроса
import org.springframework.data.repository.query.Param;
//// Используется в Spring Framework для маркировки классов как контроллеров.
//Контроллеры в Spring MVC отвечают за обработку HTTP-запросов и возвращение ответов в виде представлений, JSON, XML и т.д
//позволяет распозновать наш класс как управляющий
//тут будут адреса наших страниц, из какой таблицы и т.д.
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
// Класс (Модель), который используется в Spring MVC для передачи данных из контроллера
//в представление
//интерфейс Model необохим для взаимодействия контроллера и конфигуратора
//и добавляния всех элементов в интерфейс
import org.springframework.ui.Model;
// Аннотация @ModelAttribute используется в Spring MVC для связывания параметров запроса с атрибутами модели.
// Это позволяет легко передавать данные из формы в контроллер и обратно
//аннотация для связывания параметра и метода/переменной, которая будет выводиться в веб интерфейсе
import org.springframework.web.bind.annotation.*;
// Аннотация @PathVariable используется в Spring MVC для параметризации методов контроллера
// с использованием частей пути запроса. Это позволяет легко маршрутизировать запросы на основе определенных частей URL.
//отвечает за получение параметра из адресной строки браузера
// Задаем наш URl. Аннотация @RequestMapping используется в
// Spring MVC для указания, какие методы контроллера должны обрабатывать определенные HTTP-методы и пути
//для создания адреса(url) в браузерной строке
//адреса контроллеров в целом и методов в частности
//метод, указывающий с помощью какого http запроса будем передавать данные(POST,GET...)
// Класс ModelAndView представляет собой абстрактный класс, который
// используется в Spring MVC для возвращения модели и представления из контроллера. Он объединяет модель, которая содержит
// данные для представления, и имя представления, которое будет отображено.
//метод, позволяющий указывать название html страницы, которую мы подвязываем к контроллеру
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;


@Controller // Весь наш класс будет контроллером
public class AppController { // Создаем класс с модификатором доступа Public, так как контроллер должен быть открытым полностью
    // из-за аннотации @Controller
    @Autowired
    private GoodService service;
    // Автоматически создаем и внедряем класс GoodService и переименовываем в его service. Вот так и выглядит
    // внедрение зависимостей (dependency injection)
    //запросы, начинающиеся с /
    @RequestMapping("/index")
    public String viewAdminHomePage(Model model, @Param("keyword") String keyword) {
        List<Good> listGoods = service.listAll(keyword);
        model.addAttribute("listGoods", listGoods);
        model.addAttribute("keyword", keyword);
        model.addAttribute("good", new Good());
        return "index"; // Возвращаем шаблон index для администратора
    }

    // Обработчик для страницы index_user
    @RequestMapping("/index_user")
    public String viewUserHomePage(Model model, @Param("keyword") String keyword) {
        List<Good> listGoods = service.listAll(keyword);
        model.addAttribute("listGoods", listGoods);
        model.addAttribute("keyword", keyword);
        model.addAttribute("good", new Good());
        return "index_user"; // Возвращаем шаблон index_user для обычного пользователя
    }

    @RequestMapping("/") // слеш => Наша главная страница
    //1. Вызывается метод service.listAll(keyword), который, вероятно, возвращает список книг, соответствующих заданному
    // поисковому запросу.
    //2. Полученный список добавляется в модель под именем listGoods.
//3. Значение keyword также добавляется в модель.
//4. Возвращается имя представления index, которое будет отображаться в ответ на запрос.
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
//Реализация поиска на главной странице по критериям
        List<Good> listGoods = service.listAll(keyword); // Наш список книг. Элементы в список передаются из класса GoodService
        model.addAttribute("listGoods", listGoods); // Создаем модель и добавляем в нее атрибут. На главной странице будет
        // выводиться список книг
        model.addAttribute("keyword", keyword); // Создаем модель и добавляем в нее атрибут. На главной странице будет выводиться
        // поиск книг
        //index.html-туда будут возвращаться данные
        model.addAttribute("good", new Good());
        return "index"; // Выводится все то, что отображено в шаблоне index.html, модели будут туда также добавляться
    }

    //контроллер по добавлению книги
    @RequestMapping("/new")
    public String showNewGoodForm(Model model) {
        Good good = new Good();
        model.addAttribute("good", good);
        return "new_good";
    }
    //метод сохранения книги
    // Передаем данные из модели (получили в методе showNewGoodForm) в базу данных
    @PostMapping("/save")
    public String saveGood(@ModelAttribute("good") Good good) {
        service.save(good);
        return "redirect:/index"; // Перенаправление на главную страницу после сохранения
    }


    //контроллер по редактированию книг по ключу id
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Good getGoodById(@PathVariable(name = "id") Long id) {
        return service.get(id); // Возвращаем объект для редактирования
    }

    // Сохранение изменений (AJAX)
    @PostMapping("/edit")
    public String saveEditedGood(@ModelAttribute("good") Good good) {
        service.save(good);
        return "redirect:/goods"; // Перенаправление на список
    }

    @RequestMapping("/delete/{id}")
    public String deleteGood(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/index";
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // Имя файла шаблона (например, about.html в папке templates)
    }
    @GetMapping("/services")
    public String servicesPage() {
        return "services";
    }
    @GetMapping("/faq")
    public String faqPage() {
        return "faq";
    }
    @GetMapping("/terms")
    public String termsPage() {
        return "terms"; // Имя файла шаблона (например, about.html в папке templates)
    }
    @GetMapping("/privacy")
    public String privacyPage() {
        return "privacy"; // Имя файла шаблона (например, about.html в папке templates)
    }
    @GetMapping("/return-policy")
    public String policyPage() {
        return "return-policy"; // Имя файла шаблона (например, about.html в папке templates)
    }
}
