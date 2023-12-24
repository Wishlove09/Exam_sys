public class Vehicle {// Vehicle类名
    protected String licensePlate;// 成员变量车牌,protected保护型数据变量
    protected String color;// 成员变量颜色
    protected int age;// 成员变量年龄

    public Vehicle() {
    }// 7-8行，无参的构造方法

    public Vehicle(String licensePlate, String color, int age) {
        super();
        this.licensePlate = licensePlate;
        this.color = color;
        this.age = age;
    } // 9-14行，有参的构造方法

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    // 15-32行，get和set方法

    @Override
    public String toString() {
        return "Vehicle [licensePlate=" + licensePlate + "]";
    }

}
