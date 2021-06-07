package it.polito.tdp.PremierLeague.model;

public class PlayerAndGoal {
	
	private Player player;
	private double mediaGoal;
	
	public PlayerAndGoal(Player player, double mediaGoal) {
		super();
		this.player = player;
		this.mediaGoal = mediaGoal;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getMediaGoal() {
		return mediaGoal;
	}

	public void setMediaGoal(double mediaGoal) {
		this.mediaGoal = mediaGoal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
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
		PlayerAndGoal other = (PlayerAndGoal) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	
}
