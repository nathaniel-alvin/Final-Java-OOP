import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccountListMan {
    private HashMap<Long, Account> accountList;

    public AccountListMan(HashMap<Long, Account> accountList) {
        this.accountList = accountList;
    }

    public void add(Long key,  Account account) {
        accountList.put(key, account);
    }

    //remove an account from the account list
    public void remove(Long key) {
        accountList.remove(key);
    }

    //find an account in the linked list based off an account number
    public Account find(Long key) {
        Account account;

        account = accountList.get(key);
        if (account != null) return account;

        System.out.println("Account Not Found");
        return null;
    }

    //remove all accounts from the list associated with the specified profile
    public void removeAccounts(Profile profile) {
        Iterator iterator = accountList.entrySet().iterator();
        Long key;

        while (iterator.hasNext()){
            Map.Entry mapElement = (Map.Entry)iterator.next();
            key = (Long)mapElement.getKey();

            if (profile.checkAccount(key)) {
                iterator.remove(); //removes the HashMap set
            }
        }
    }
}