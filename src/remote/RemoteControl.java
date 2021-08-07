package remote;

public class RemoteControl {

    private Command command;

    public void addCommand(Command command ) {
    	this.command = command;
    }

    public void pushButton() {
    	command.execute();
    }

}
