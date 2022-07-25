package sg.edu.nus.iss.Workshop14.service;

import sg.edu.nus.iss.Workshop14.model.Contact;

public interface ContactsRepo {
    public void save(final Contact ctc);

    public Contact findById(final String contactId);
}
