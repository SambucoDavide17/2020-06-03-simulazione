package it.polito.tdp.PremierLeague.model;

public class PlayerEDelta {
	
	private Player p;
	private double delta;
	
	public PlayerEDelta(Player p, double delta) {
		super();
		this.p = p;
		this.delta = delta;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(delta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerEDelta other = (PlayerEDelta) obj;
		if (Double.doubleToLongBits(delta) != Double.doubleToLongBits(other.delta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return p.getName() + "\n";
	}
	
	
}
