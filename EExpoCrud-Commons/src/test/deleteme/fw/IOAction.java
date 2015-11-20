package test.deleteme.fw;



public abstract class IOAction<I, O> extends ResultAction<O> {
	I input;
	O output;

	
	protected IOAction(I input) { 
		this.input = input;
	}

	
	protected final O execute(){
		return null;
		//so p obrigar a chamada do execute(I obj);	
	}
	
	protected abstract O execute(I obj);
	
	
	public O output(){
		return output;
	}
	 
	protected IOAction<I, O> output(O output){
		this.output = output;
		return this;
	}
	
	
	
	
	
	
}
