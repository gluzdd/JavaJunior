package org.example.seminar4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.id.Configurable;
import org.hibernate.Transaction;


import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.UUID;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/students";
    private static final String USER = "root";
    private static final String PASSWORD = "heroes15";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            prepareTables(connection);
            run(connection);
//            createSchema(connection);
//            createTableGroup(connection);
//            insertDataGroup(connection);
//            createTableStudents(connection);
//            insertData(connection);
//            searchAllStudents(connection);
//            searchAllStudentsByGroupName(connection, 1);

        }catch (SQLException e) {
            System.err.println("Соединение не удалось " + e.getMessage());
        }
    }
    private static void prepareTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `groups2`(
                    `id` BIGINT PRIMARY KEY,
                    `name` BIGINT NOT NULL
                    );
                    """);
        }
//        try (Statement statement = connection.createStatement()) {
//            statement.execute("""
//                    INSERT INTO `groups2`(`id`,`name`)
//                    VALUES
//                    (1, '111'),
//                    (2, '222'),
//                    (3, '333');
//                    """);
//        }
        try (Statement statement = connection.createStatement()){
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS `students2`(
                    `id` BIGINT PRIMARY KEY,
                    `first_name` VARCHAR(256) NOT NULL,
                    `second_name` VARCHAR(256) NOT NULL,
                    `group_id` BIGINT,
                    FOREIGN KEY (group_id) REFERENCES `groups2`(id)
                    );
                    """);
        }

//        try (Statement statement = connection.createStatement()){
//            statement.execute("""
//                    INSERT INTO `students2`(`id`,`first_name`, `second_name`,`group_id`)
//                    VALUES
//                    (1, 'Kris', 'Bazil', 1),
//                    (2, 'Javokhir', 'Rakhmatov', 2),
//                    (3, 'Anna', 'Kudr', 3);
//                    """);
//        }
    }
    private static void run(Connection connection) throws SQLException {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                Students2 students2 = session.find(Students2.class, 1L);
                System.out.println(students2);
            }
            Students2 students3 = new Students2();
            students3.setId(5L);
            students3.setFirst_name("Artem");
            students3.setSecond_name("Nesterov");
            students3.setGroup_id(1L);
            System.out.println(students3);

            //Добавление в таблицу Students2 новых юзеров
//            try (Session session = sessionFactory.openSession()) {
//                Transaction transaction = session.beginTransaction();
//                session.persist(students3);
//                transaction.commit();
//            }

            // Изменение юзера по секонднейму
//            try (Session session = sessionFactory.openSession()) {
//                students3.setSecond_name("Arturov");
//                Transaction transaction = session.beginTransaction();
//                session.merge(students3);
//                transaction.commit();
//            }

            // Удаление юзера
            try(Session session = sessionFactory.openSession()){
                Transaction tx = session.beginTransaction();
                session.remove(students3);
                tx.commit();
            }
        }
    }
}

