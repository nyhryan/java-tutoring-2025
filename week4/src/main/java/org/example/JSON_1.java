package org.example;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class JSON_1 {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final List<MyData> dataList = List.of(
            new MyData(2291012L, "남윤혁", 26, "서울시 강남구"),
            new MyData(2291013L, "김철수", 30, "서울시 서초구"),
            new MyData(2291014L, "이영희", 22, "서울시 송파구")
    );

    public static void main(String[] args) {
        var path = Path.of(System.getProperty("user.dir") + File.separator + "mydata.json");
        var someData = new MyData(2291012L, "남윤혁", 26, "서울시 강남구");
        mapper.writeValue(path, someData);
        System.out.println("Data saved to " + path.toAbsolutePath());

        MyData myData = mapper.readValue(path, MyData.class);
        System.out.println("READ myData = " + myData);

        path = Path.of(System.getProperty("user.dir") + File.separator + "mydata_list.json");
        mapper.writeValue(path, dataList);
        System.out.println("List data saved to " + path.toAbsolutePath());

        var valueType = mapper.getTypeFactory().constructCollectionType(List.class, MyData.class);
        List<MyData> readDataList = mapper.readValue(path, valueType);
        System.out.println("READ readDataList = " + readDataList);
    }
}

@JsonPropertyOrder({"id", "name", "age", "address"})
class MyData {
    private long id;
    private String name;
    private int age;
    private String address;

    public MyData() {}

    public MyData(long id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (MyData) obj;
        return this.id == that.id &&
                Objects.equals(this.name, that.name) &&
                this.age == that.age &&
                Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, address);
    }

    @Override
    public String toString() {
        return "MyData[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "age=" + age + ", " +
                "address=" + address + ']';
    }
}