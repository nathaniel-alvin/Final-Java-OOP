import java.io.*;

// class to manage accounts in the file
public class AccountFileMan extends FileMan{
    private AccountListMan accountListManager;

    public AccountFileMan(AccountListMan accountListManager, String fileName) {
        super(fileName);
        this.accountListManager = accountListManager;

        setup();
    }

    // load all accounts from the local file
    private void setup() {
        try {
            String[] tempParse;
            File accountFile = new File(fileName);
            if (!accountFile.exists()) accountFile.createNewFile();
            BufferedReader buffR = new BufferedReader(new FileReader(accountFile));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                addAccountFromFile(tempParse);
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // create a singular account from a line parse
    public void addAccountFromFile(String[] splitLine) {
        Account account;
        AccountNumber accountNumber = new AccountNumber(Long.parseLong(splitLine[2]));

        Pin pin = new Pin(Integer.parseInt(splitLine[1]));
        account = new Account(accountNumber, pin, Double.parseDouble(splitLine[0]));

        accountListManager.add(Long.parseLong(splitLine[2]), account);
    }

    // remove all accounts from the file associated with the specified profile
    public void removeAccounts(Profile profile) {
        try {
            String[] tempParse;
            BufferedReader buffR = new BufferedReader(new FileReader(fileName));

            do {
                tempParse = parseLine(buffR);
                if (tempParse == null) break;
                Long accountNumber = Long.parseLong(tempParse[2]);

                if (profile.checkAccount(accountNumber)){
                    Account account = accountListManager.find(accountNumber);
                    if (account == null) continue;
                    removeLineFromFile(account.toString());
                }
            } while (tempParse != null);

            buffR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add an account to the local file
    public void add(Account account) {
        try {
            //open the buffer in append mode
            BufferedWriter buffW = new BufferedWriter(new FileWriter(fileName, true));

            buffW.write((account.toString()).trim());
            buffW.newLine();

            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // remove an account to the local file
    public void remove(Account account) {
        removeLineFromFile(account.toString());
    }
}