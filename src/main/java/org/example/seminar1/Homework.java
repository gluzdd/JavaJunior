package org.example.seminar1;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Homework {
    public static void main(String[] args) throws IOException {
        int[] array = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1000))
                .map(it -> it + 1)
                .map(it -> it * 5)
                .filter(it -> it > 10)
                .limit(100)
                .toArray();


        List<Department> departments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Department department = departments.get(randomDepartmentIndex);

            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
            person.setDepartment(department);

            persons.add(person);
        }

        //System.out.println(countPersons(persons, 50, 80000));
        //System.out.println(averageSalary(persons, 1));
//        for (Map.Entry<Department, List<Person>> person : groupByDepartment(persons).entrySet().) {
//            System.out.println(person);
//        }

        //for (Map.Entry<Department, Double> person : maxSalaryByDepartment(persons).entrySet()) {
        //    System.out.println(person);
        //}
//        System.out.println(groupPersonNamesByDepartment(persons));
//        for (Map.Entry<Department, List<String>> person : groupPersonNamesByDepartment(persons).entrySet()) {
//            System.out.println(person);
//        }
        for (Person person : minSalaryPersons(persons)) {
            System.out.println(person);
        }


    }

    /**
     * Используя классы Person и Department, реализовать методы ниже:
     */

    static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    static class Department {
        private String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    static int countPersons(List<Person> persons, int x, double d) {
        // TODO: Реализовать метод
        return (int) persons.stream()
                .filter(it -> it.getAge() > x)
                .filter(it -> it.getSalary() > d)
                .peek(System.out::println)
                .count();
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    static OptionalDouble averageSalary(List<Person> persons, int x) {
        // TODO: Реализовать метод
        return persons.stream()
                .filter(it -> it.getDepartment().getName().contains("#" + x))
                .mapToDouble(Person::getSalary)
                .average();
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment,
                        Person::getSalary,
                        Double::max));
    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment,
                        Collectors.mapping(Person::getName,
                                Collectors.toList())));
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    static List<Person> minSalaryPersons(List<Person> persons) {
        // TODO: Реализовать метод
        // В каждом департаменте ищем сотрудника с минимальной зарплатой.
        // Всех таких сотрудников собираем в список и возвращаем из метода.
        //throw new UnsupportedOperationException();
        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment, p -> p,
                        (p1, p2) -> p1.getSalary() <= p2.getSalary() ? p1 : p2))
                .values()
                .stream()
                .toList();
    }


}
