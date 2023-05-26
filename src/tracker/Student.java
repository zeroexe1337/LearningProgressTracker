package tracker;

public class Student {
    private final String firstName;
    private final String lastName;
    private final String email;



    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getHashCode() {
        return email.hashCode();
    }
    @Override
    public String toString() {
        return (this.getFirstName()+ " " + this.getLastName() + " " + this.getEmail());

    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    public boolean isFirstNameValid() {
        String regexp1 = "^([a-zA-Z])([a-zA-Z])(([a-zA-Z]+)?)$";
        String regexp2 = "^([a-zA-Z])(([a-zA-Z]+)?)(['-])([A-Z]?)([a-z]+)(['-]?)(([a-z]+)?)$";
        return this.firstName.matches(regexp1) || firstName.matches(regexp2);
    }
    public boolean isLastNameValid() {
        String regexp1 = "^([a-zA-Z])([a-zA-Z])(([a-zA-Z]+)?)";
        String regexp2 = "^([a-zA-Z])(([a-zA-Z]+)?)(['-])([A-Z]?)([a-z]+)(['-]?)(([a-z]+)?)";
        return this.lastName.matches(regexp1) || lastName.matches(regexp2);

    }
    public boolean isValidEmail() {
        String emailRegex = "^([a-zA-Z0-9\\\\.]+)(@)([a-zA-Z0-9]+)(\\.)([a-z0-9]+)";
        return this.email.matches(emailRegex);
    }

}



