package textOnlyJam.game.text;

public class Timer {
	
	private long internalTimer;

	public Timer(int minutes, int seconds) {
		internalTimer = seconds * 1000L + minutes * 60000L;
	}
	
	public void update(long deltaMillis){
		internalTimer -= deltaMillis;
	}
	
	public boolean isFinished(){
		return internalTimer <= 0L;
	}
	
	public int getMinutes(){
		if(!isFinished()){
			return (int)(internalTimer / 60000L) % 60;
		}
		return 0;
	}

	public int getSeconds(){
		if(!isFinished()){
			return (int)(internalTimer / 1000L) % 60;
		}
		return 0;
	}
}
