package edu.unibo.martyadventure.model;

public class PlayerCharacter extends Character {
	private Shoes shoes;
	
	public PlayerCharacter(Shoes shoes) {
		setShoes(shoes);
	}
	
	  // Getter
	  public Shoes getShoes() {
	    return shoes;
	  }

	  // Setter
	  public void setShoes(Shoes shoes) {
	    this.shoes = shoes;
	  }

}
