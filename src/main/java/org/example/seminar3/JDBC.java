package org.example.seminar3;
/* Повторить все, что было на семинаре на таблице Student с полями
        1. id - bigint
        2. first_name - varchar(256)
        3. second_name - varchar(256)
        4. group - varchar(128)

        Написать запросы:
        1. Создать таблицу
        2. Наполнить таблицу данными (insert)
        3. Поиск всех студентов
        4. Поиск всех студентов по имени группы

        Доп. задания:
        1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
        2. ** Все идентификаторы превратить в UUID

        Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
*/
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.UUID;

public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "heroes15";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createSchema(connection);
            createTableGroup(connection);
            insertDataGroup(connection);
            createTableStudents(connection);
            insertData(connection);
            searchAllStudents(connection);
            searchAllStudentsByGroupName(connection, 1);

        }catch (SQLException e) {
            System.err.println("Соединение не удалось " + e.getMessage());
        }
    }
    static void createSchema (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute("DROP SCHEMA students;");
            statement.execute("CREATE SCHEMA students;");
        }
    }
    static void createTableStudents (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute("""
                CREATE TABLE `students`.`table_students` (
                `id` VARCHAR(36) PRIMARY KEY,
                `first_name` VARCHAR(255) NOT NULL,
                `second_name` VARCHAR(255) NOT NULL,
                `group_id` SMALLINT,
                FOREIGN KEY (`group_id`) REFERENCES `groups`(`id`));
                """);
        }
    }
    static void createTableGroup (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()){
            statement.execute("""
                CREATE TABLE `students`.`groups` (
                `id` SMALLINT PRIMARY KEY,
                `group` VARCHAR(128) NOT NULL);
                """);
        }
    }
    static void insertDataGroup (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    INSERT INTO `students`.`groups` (`id`,`group`)
                    VALUES
                    (1, '35'),
                    (2, '32'),
                    (3, '30'),
                    (4, '38');
                    """);
        }
    }


    static void insertData (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                INSERT INTO `students`.`table_students` (`id`, `first_name`, `second_name`, `group_id`)
                VALUES
                (UUID(), 'Artem', 'Nesterov', 1),
                (UUID(), 'Javokhir', 'Rakhmatov', 2),
                (UUID(), 'Kris', 'Bazil', 3),
                (UUID(), 'Ivan', 'Tretykov', 1);
                """);
        }
    }
    static void searchAllStudents (Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                SELECT `id`, `first_name`, `second_name`, `group_id`
                FROM `students`.`table_students`;
                """);
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String second_name = resultSet.getString("second_name");
                int group = resultSet.getInt("group_id");
                System.out.printf("Результат поиска: %s, %s, %s, %d\n", id, first_name, second_name, group);
            }
        }
    }
    static void searchAllStudentsByGroupName (Connection connection, int groupName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                SELECT `id`, `first_name`, `second_name`, `group_id`
                FROM `students`.`table_students`
                WHERE `group_id` ='""" + groupName + "';");
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String second_name = resultSet.getString("second_name");
                int group = resultSet.getInt("group_id");
                System.out.printf("Результат поиска по имени группы: %s, %s, %s, %d\n", id, first_name, second_name, group);
            }
        }
    }
}
