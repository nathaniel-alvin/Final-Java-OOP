
public interface Transaction {
    void deposit( double amount );
    void withdraw( double amount );
    void transfer(Account target, double amount );
}
