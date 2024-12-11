package sep3.webshop.service;

import sep3.webshop.model.ContactMessage;

import java.util.List;

public interface CustomerMessageService
{
  List<ContactMessage> getAllCustomerMessages();
  ContactMessage createCustomerMessage(ContactMessage contactMessage);
}
