package textOnlyJam.game.text;

public class Timer {
	
	private long internalTimer;
	private long startTimer;

	public Timer(int minutes, int seconds) {
		internalTimer = seconds * 1000L + minutes * 60000L;
		startTimer = internalTimer;
	}
	
	public void update(long deltaMillis){
		internalTimer -= deltaMillis;
	}
	
	public float getProgress(){
		return 1F - (float)(1F * internalTimer / startTimer);
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
