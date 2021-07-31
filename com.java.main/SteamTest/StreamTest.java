package SteamTest;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {

//        boolean flag = (12 & 1) == 0;
//        System.out.println("判断是否是偶数："+flag);
        StreamDemo streamDemo = new StreamDemo();
//        streamDemo.streamFind();
//        streamDemo.streamFiliter();
//        streamDemo.streamFlat();
//        streamDemo.streamReduce();
        streamDemo.streamCollector();

    }
}

class StreamDemo {

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
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);// 求最大值方式1
        Optional<Integer> max1 = list.stream().reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer > integer2 ? integer : integer2;
            }
        });// 求最大值方式1
        Integer max2 = list.stream().reduce(0, Integer::max);// 求最大值写法2

        System.out.println("list求和：" + sum1.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max1.get() + "," + max2);

        List<Person> personList = dataPersonsList();
        Optional<Integer> sumSalary = personList.stream().map(Person::getSalary).reduce(Integer::sum);// 求工资之和方式1：
        Integer sumSalary2 = personList.stream().reduce(0, (integer, person) -> integer + person.getSalary(), new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return 0;
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
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);    // 自然排序
        Optional<Integer> max2 = list1.stream().max(new Comparator<Integer>() { // 自定义排序
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

    public void streamCollector() {

        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> (x & 1) == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> (x & 1) == 0).collect(Collectors.toSet());
        List<Person> personList = dataPersonsList();
        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000).collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);

        Long count = personList.stream().collect(Collectors.counting());    // 求总数
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));    // 求平均工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare)); // 求最高工资
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));    // 求工资之和
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary)); // 一次性统计所有信息

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工最高工资：" + max.get());
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);

        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));    // 将员工按薪资是否高于8000分组
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));   // 将员工按性别分组
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea))); // 将员工先按性别分组，再按地区分组
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);

        String names = personList.stream().map(p -> p.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + names);
        List<String> demoList = Arrays.asList("A", "B", "C");
        String string = demoList.stream().collect(Collectors.joining("-"));
        System.out.println("拼接后的字符串：" + string);

        Integer s = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (i, j) -> (i + j - 5000)));
        System.out.println("员工扣税薪资总和：" + s);
        Optional<Integer> sum2 = personList.stream().map(Person::getSalary).reduce(Integer::sum);// stream的reduce
        System.out.println("员工薪资总和：" + sum2.get());

        List<String> newList = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName).collect(Collectors.toList()); // 按工资升序排序（自然排序）
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).map(Person::getName).collect(Collectors.toList()); // 按工资倒序排序
        List<String> newList3 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());// 先按工资再按年龄升序排序
        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());
        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);

        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};
        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);

        List<String> stream1List = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList()); // concat:合并两个流 distinct：去重
        List<Integer> collect1 = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());  // limit：限制从流中获得前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 4).skip(2).limit(5).collect(Collectors.toList());   // skip：跳过前n个数据
        System.out.println("流合并：" + stream1List);
        System.out.println("limit：" + collect1);
        System.out.println("skip：" + collect2);

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