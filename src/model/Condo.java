package model;

/**
 * Project Phase 1
 * Student 1: 	Quoc Phong Ngo 				- 40230574
 * Student 2: 	Jimil Suchitkumar Prajapati - 40205477
 * Student 3:   Anitha Ramakrishan			- 40231724
 * 
 * Condo class
 */
public class Condo extends Property {

	private int streetNo;
	
	private int unitNo;
	
	public Condo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Condo(int streetNo, int unitNo) {
		super();
		this.streetNo = streetNo;
		this.unitNo = unitNo;
	}

	public int getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	public int getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}
