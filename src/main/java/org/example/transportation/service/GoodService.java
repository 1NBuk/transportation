package org.example.transportation.service;
import org.example.transportation.entity.Good;
import org.example.transportation.repository.GoodRepository;

//GoodService — это сервисный класс, который содержит бизнес-логику приложения. Он взаимодействует с
// GoodRepository для выполнения операций с данными и предоставляет эти операции контроллеру.


import java.util.List;
//Autowired-аннотация,которая нужная для связи всмех зависимостей из всех классов
//то есть собираем проект вместе
import org.springframework.beans.factory.annotation.Autowired;
//Service-анноация для обнаружения всех зависимостей
//и класс service принадлежит springboot
//в этом классе вызов всех методов(bean компонентов)
import org.springframework.stereotype.Service;
//Указывает, что класс является сервисным компонентом в контексте Spring.
@Service
public class GoodService {
    //Автоматически внедряет зависимость GoodRepository.
    @Autowired
    private GoodRepository repo; // Автоматически создаем и внедряем интерфейс GoodRepository и переименовываем в его repo.
    // Вот так и выглядит внедрение зависимостей (dependency injection)
    //Этот метод возвращает список, возможно, фильтруя их по заданному поисковому запросу. Если keyword не равен null, то используется метод search
    // репозитория для поиска по ключевому слову. В противном случае используется метод findAll для получения всех.
    // Сам список берем из нашей баз данных при помощи репозитория


    //поиск и фильтр в системе
    public List<Good> listAll(String keyword) {
        if (keyword != null) {
            //выводим все результаты поиска
            return repo.search(keyword); // Созданный метод в репозитории
        }
        //если ошибка
        return repo.findAll();// Встроенный в коллекцию метод (JPA)
    }

    //Этот метод сохраняет студента в базе данных, используя репозиторий
    public void save(Good good) {
        repo.save(good);
    }

    //Этот метод получает студента по его идентификатору.
    public Good get(Long id) {
        return repo.findById(id).get();
    }

    //Этот метод удаляет студента из базы данных по его идентификатору
    public void delete(Long id) {
        repo.deleteById(id);
    }
}