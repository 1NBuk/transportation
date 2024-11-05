package org.example.transportation.repository;

import org.example.transportation.entity.Good;

//GoodRepository — это репозиторий данных, который предоставляет методы для взаимодействия с базой данных,
// такие как создание, чтение, обновление и удаление (CRUD) записей.


//интерфейс используется для объявления поведения, которое должны реализовать классы
//в интерфейсе аннотация с sql запросом

//Интерфейс GoodRepository является репозиторием в Spring Data JPA. Репозитории в Spring Data JPA предоставляют
//абстракции над базовыми CRUD-операциями (CREATE – создание, READ – чтение, UPDATE – обновление, DELETE –
//удаление) для работы с данными в базе данных.
//Интерфейс GoodRepository расширяет (наследует) интерфейс JpaRepository, который предоставляет базовые операции
//CRUD для сущностей, идентифицируемых по Long. JpaRepository автоматически генерируется Spring Data JPA на основе
//информации, полученной из аннотаций @Entity и @Id в классе Student.
//Интерфейс GoodRepository также содержит метод search, который использует аннотацию @Query для определения SQL запроса,
// который будет выполняться для поиска студентов по заданному поисковому запросу. Запрос использует функцию
//CONCAT для объединения различных полей студента в одно строковое значение, которое затем сравнивается с шаблоном,
//включающим %?1%, где ?1 представляет собой параметр запроса, который будет заполнен значением keyword.

import java.util.List;
//jpa дает сохранять Java-объекты в базе
import org.springframework.data.jpa.repository.JpaRepository;
//аантоция отвечает за sql запросы
import org.springframework.data.jpa.repository.Query;
//p-параметр,нам вавжно взаимодействовать с классом Студент
//1-наполнитель значения. слово, которое введем в переменной будем отображаться вместо него
public interface GoodRepository extends JpaRepository<Good, Long> {
    @Query("SELECT p FROM Good p WHERE CONCAT(p.good_name, ' ', p.content, ' ', p.departure_city, ' ', p.departure_date, ' ', p.arrival_city, ' ', p.arrival_date) LIKE %?1%")
    List<Good> search(String keyword);
} // сформированный список, используемый во всем исходном коде. Все переменные в запрос берутся класса Good

