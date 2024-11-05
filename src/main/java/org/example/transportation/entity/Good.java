package org.example.transportation.entity;


//Good — это сущность (entity), представляющая модель данных книги в приложении.
// она сопоставляется с таблицей в базе данных с помощью JPA (Java Persistence API).
//Java Persistence API (JPA) — спецификация API Jakarta EE,
// которая предоставляет возможность сохранять в удобном виде Java-объекты в базе данных.



//джакарта - это jpa. будет реализовываться наш класс
//gradle-библиотеки. если не работает джкарата, то прописать в gradle:
//implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

//entity-класс будет храниться в бд и создана таблица

//private-только в рамках класса
//@setter=> все перменные как setter
import jakarta.persistence.Entity; // Интерфейс для определения класса сущностей, который будет храниться в БД
import jakarta.persistence.GeneratedValue; // Интерфейс для автоматической генерации значений ID
import jakarta.persistence.GenerationType; // Метод для представления стратегии автоматической генерации значений ID
import jakarta.persistence.Id; // Интерфейс для аннотации @ID, что позволяет обозначить в базе данных поле ID в качестве первичного ключа
import lombok.Getter; //Интерфейс, позволяющий указывать на то, переменные можно обозначить геттерами
import lombok.Setter; //Интерфейс, позволяющий указывать на то, переменные можно обозначить сеттерами
import java.time.LocalDate;//тип данных, который содержит только день, месяц и год

@Setter // Указываем, что все наши переменные являются сеттерами
@Entity // Это означает, что класс Student представляет сущность в базе данных, и Hibernate будет использовать этот класс для управления данными в
// базе данных. Hibernate - реализация JPA(спецификация, которая позволяет сохранять java-объекты в БД)
public class Good {
    private Long id; // ID
    @Getter // Указываем, что геттер
    private String good_name; // Имя
    @Getter
    private String content; // содержание груза
    @Getter
    private String departure_city; // город отправки
    @Getter
    private LocalDate departure_date; //дата отправки
    @Getter
    private String arrival_city; // город прибытия
    @Getter
    private LocalDate arrival_date; // дата прибытия

    public Good() { // Создаем метод
    }
    //Аннотация @Id указывает, что свойство id является первичным ключом (primary key) в базе
    // данных.
    // Это означает, что каждый объект класса будет иметь уникальный идентификатор, который будет
    // использоваться для связи с другими объектами и для поиска объектов в базе данных.
    //для авто инкремента id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    //переопределяем метод в базовом классе object
    @Override
    public String toString() {
        return "good [id=" + id + ", good_name=" + good_name + ", publisher=" + content + ", departure_city=" + departure_city + ", departure_date=" + departure_date + ", arrival_city=" + arrival_city + ", arrival_date=" + arrival_date + "]";
    }
}
