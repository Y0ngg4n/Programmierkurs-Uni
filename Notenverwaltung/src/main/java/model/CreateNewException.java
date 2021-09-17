package model;

/**
 * 
 * Exception wird geworfen wenn eine neue Note oder Modulgruppe nicht erzeugt werden kann
 */
public class CreateNewException extends Exception{
	private static final long serialVersionUID = 1L;
	private String message;
	
	public CreateNewException(String errorMessage){
		message = errorMessage;
	}
	
	@Override
	public String getMessage(){
		return message;
	}
}
