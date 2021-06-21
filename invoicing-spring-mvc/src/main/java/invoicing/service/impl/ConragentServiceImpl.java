package invoicing.service.impl;

import invoicing.dao.ContragentRepository;
import invoicing.entity.Contragent;
import invoicing.exception.EntityNotFoundException;
import invoicing.service.ContragentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ConragentServiceImpl implements ContragentService {
    @Autowired
    private ContragentRepository contragentRepo;

    @Override
    @Transactional(readOnly = true)
    public Collection<Contragent> getAllContragents() {
        return contragentRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Contragent getContragentById(Long id) {
        return contragentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Contragent with ID='%d' not found", id)));
    }

    @Override
    public Contragent addContragent(Contragent contragent) {
        contragent.setId(null);
        Date now = new Date();
//        contragent.setCreated(now);
//        contragent.setModified(now);
        return contragentRepo.save(contragent);
    }

    @Override
    public List<Contragent> addContragentsBatch(List<Contragent> contragents) {
        return contragentRepo.saveAll(contragents);
    }

    @Override
    public long dropAllContragents() {
        long count = getCount();
        contragentRepo.deleteAll();
        return count;
    }

    @Override
    public Contragent updateContragent(Contragent contragent) {
        getContragentById(contragent.getId());
//        contragent.setModified(new Date());
        return contragentRepo.save(contragent);
    }

    @Override
    public Contragent deleteContragentById(Long id) {
        Contragent old = getContragentById(id);
        contragentRepo.deleteById(id);
        return old;
    }

    @Override
    public long getCount() {
        return contragentRepo.count();
    }
}
