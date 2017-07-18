package lesson170718;

public class Pointers {

	public static void main(String[] args) {

		A a; // A*

		X x;

		a = new A();

		a.change();

		use(a); // xxx = a;

	}

	private static void use(A xxx) {
//		xxx = new A();
		xxx.change();
	}

}

interface X {

}

class A {

	int state = 0;

	public void change() {
		this.state++;
		this.m();
	}
	
	void m() {
		
	}

}
