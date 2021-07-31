package SteamTest;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, "male", "New York"));
        personList.add(new Person("Jack", 7000, "male", "Washington"));
        personList.add(new Person("Lily", 7800, "female", "Washington"));
        personList.add(new Person("Anni", 8200, "female", "New York"));
        personList.add(new Person("Owen", 9500, "male", "New York"));
        personList.add(new Person("Alisa", 7900, "female", "New York"));

        StreamDemo streamDemo = new StreamDemo();
//        streamDemo.streamFind();
//        streamDemo.streamFiliter();
//        streamDemo.streamFlat();
        streamDemo.streamReduce();

    }
}

class StreamDemo {

    public void streamCreate() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);
        stream2.forEach(System.out::println);
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);
    }

    public void streamFind() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        list.stream().filter(x -> x > 6).forEach(System.out::println);// 遍历输出符合条件的元素
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();// 匹配第一个
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();// 匹配任意（适用于并行流）
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);// 是否包含符合特定条件的元素
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    public void streamFiliter() {
        List<Person> personList = dataPersonsList();
        List<String> fiterList = personList.stream().filter(x -> x.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
        System.out.print("高于8000的员工姓名：" + fiterList);
    }

    private List<Person> dataPersonsList() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        return personList;
    }

    public void streamFlat() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> { // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    public void streamReduce() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        Optional<Integer> sum1 = list.stream().reduce((x, y) -> x + y);// 求和方式1
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);// 求和方式2
        Integer sum3 = list.stream().reduce(10, Integer::sum);// 求和方式3

        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);// 求乘积
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? y : x);// 求最大值方式1
        Integer max2 = list.stream().reduce(12, Integer::max);// 求最大值写法2

        System.out.println("list求和：" + sum1.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);

        List<Person> personList = dataPersonsList();
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);// 求工资之和方式1：
        Integer sumSalary2 = personList.stream().reduce(0, (integer, person) -> integer + person.getSalary(), new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return null;
            }
        });// 求工资之和方式2：
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);// 求工资之和方式3：
        Integer maxSalary = personList.stream().reduce(0, (m1, p) -> m1 > p.getSalary() ? m1 : p.getSalary(), Integer::max);// 求最高工资方式1：
        Integer maxSalary2 = personList.stream().reduce(0, (m1, p) -> m1 > p.getSalary() ? m1 : p.getSalary(), (m1, m2) -> m1 < m2 ? m1 : m2);// 求最高工资方式2：

        System.out.println("工资之和：" + sumSalary.get() + "," + sumSalary2 + "," + sumSalary3);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2);
    }

    public void streamAggregate() {
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("最长的字符串：" + max.get());

        List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);// 自然排序
        Optional<Integer> max2 = list1.stream().max(new Comparator<Integer>() {// 自定义排序
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("自然排序的最大值：" + max1.get());
        System.out.println("自定义排序的最大值：" + max2.get());

        long count = list1.stream().filter(x -> x > 5).count();
        System.out.println("list1集合中大于5的数量：" + count);
    }

}

class Person {
    private String name;  // 姓名
    private int salary; // 薪资
    private int age; // 年龄
    private String sex; //性别
    private String area;  // 地区

    // 构造方法
    public Person(String name, int salary, String sex, String area) {
        this.name = name;
        this.salary = salary;
        this.sex = sex;
        this.area = area;
    }

    public Person(String name, int salary, int age, String sex, String area) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.sex = sex;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}