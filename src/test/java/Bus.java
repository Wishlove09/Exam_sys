public class Bus extends Vehicle implements MoneyFare{
	private int num;

	public Bus() {
		
	}
	public Bus(String licensePlate, String color, int age,int num) {
		super(licensePlate,color,age);
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public void charge(int price) {
		System.out.println("公交车("+getLicensePlate()+")票价为："+price+"元/张");
		
	}

	@Override
	public String toString() {
		return "Bus{" +
				"num=" + num +
				", licensePlate='" + licensePlate + '\'' +
				", color='" + color + '\'' +
				", age=" + age +
				'}';
	}
}
