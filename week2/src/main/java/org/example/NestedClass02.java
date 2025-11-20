package org.example;

public class NestedClass02 {
    public static void main(String[] args) {
        var config = new DatabaseConnection.Config(
                "localhost",
                12345,
                "user",
                "password"
        );

        var dbConnection = new DatabaseConnection(config);

        Burger bigMac = Burger.builder()
                .setBread("참깨빵")
                .setPatty("순쇠고기 패티")
                .setSauce("특별한 소스")
                .setTopping("양상추")
                .build();

        System.out.println("빅맥: " + bigMac);
    }
}

// 1. 관련있는 클래스를 중첩클래스로 묶어서 정리
class DatabaseConnection {
    static class Config {
        final String host;
        final int port;
        final String username;
        final String password;

        public Config(String host, int port, String username, String password) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }
    }

    private final Config config;

    public DatabaseConnection(Config config) {
        this.config = config;
    }
}

// 2. 객체 생성 방식 - Builder 패턴에 사용됨
class Burger {
    static class Builder {
        private String bread;
        private String patty;
        private String topping;
        private String sauce;

        Builder() {
        }

        public Builder setBread(String bread) {
            this.bread = bread;
            return this;
        }

        public Builder setPatty(String patty) {
            this.patty = patty;
            return this;
        }

        public Builder setTopping(String topping) {
            this.topping = topping;
            return this;
        }

        public Builder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Burger build() {
            return new Burger(bread, patty, topping, sauce);
        }
    }

    private final String bread;
    private final String patty;
    private final String topping;
    private final String sauce;

    public Burger(String bread, String patty, String topping, String sauce) {
        this.bread = bread;
        this.patty = patty;
        this.topping = topping;
        this.sauce = sauce;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "org.example.Burger{" +
                "bread='" + bread + '\'' +
                ", patty='" + patty + '\'' +
                ", topping='" + topping + '\'' +
                ", sauce='" + sauce + '\'' +
                '}';
    }
}