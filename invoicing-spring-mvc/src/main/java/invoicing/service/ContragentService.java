package invoicing.service;

import invoicing.entity.Contragent;

import java.util.Collection;
import java.util.List;

public interface ContragentService {
    Collection<Contragent> getAllContragents();
    Contragent getContragentById(Long id);
    Contragent addContragent(Contragent contragent);
    List<Contragent> addContragentsBatch(List<Contragent> contragents);
    long dropAllContragents();
    Contragent updateContragent(Contragent contragent);
    Contragent deleteContragentById(Long id);
    long getCount();
}
