package demo;

import lombok.Setter;

@Setter
public class TerminatorQuoter implements Quoter {

    private String message;

    @Override
    public void sayQuote() {
        System.out.println("message = " + message);
    }
}
