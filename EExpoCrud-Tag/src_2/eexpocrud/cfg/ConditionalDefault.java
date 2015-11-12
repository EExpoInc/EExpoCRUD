package eexpocrud.cfg;

@SuppressWarnings("serial")
public class ConditionalDefault implements ConditionalI {
	
	private boolean result;
	
	public ConditionalDefault(boolean result) {
		this.result = result;
	}
	
	@Override
	public boolean execute() {
		return result;
	}
}
