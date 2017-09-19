package somePackage;

public class SomeClass {

	public static void main(String[] args) {
		int x = 10;
		{
			System.out.println("inner scope = "+x);
			x = 20;
			
		}
		System.out.println("outer scope = "+x);
	}

}
