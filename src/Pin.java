public class Pin {
    private int pin;

    // Pin constructor initializes attributes
    public Pin(int pin) {
        if (is4Digit(pin))
            this.pin = pin;
    }

    // check 4-digit pin
    public boolean is4Digit(int pin) {
        if ((pin > 9999) || (pin < 1000)) {
            System.out.println("Invalid Pin: Enter a 4-digit pin");
            return false;
        } else return true;
    }


    // check if user input correct pin
    public boolean verifyPin(int pinAttempt) {
        if (pinAttempt == pin) {
            return true;
        } else {
            System.out.println("Invalid Pin Entry");
            return false;
        }
    }


    // check update pin
    public void updatePin(int newPin) {
        if (!is4Digit(newPin)) return;

        this.pin = newPin;
    }


    public void showInfo() {
        System.out.println("Pin: " + pin);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(pin);
        return sb.toString();
    }
}

