package lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.service;

import lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.dao.GoodReceivedNoteDao;
import lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.entity.GoodReceivedNote;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "goodReceivedNote")
public class GoodReceivedNoteService implements AbstractService<GoodReceivedNote, Integer> {
    private final GoodReceivedNoteDao goodReceivedNoteDao;

    @Autowired
    public GoodReceivedNoteService(GoodReceivedNoteDao goodReceivedNoteDao) {
        this.goodReceivedNoteDao = goodReceivedNoteDao;
    }

    @Override
    public List<GoodReceivedNote> findAll() {
        return goodReceivedNoteDao.findAll();
    }

    @Override
    public GoodReceivedNote findById(Integer id) {
        return goodReceivedNoteDao.getOne(id);
    }

    @Override
    public GoodReceivedNote persist(GoodReceivedNote goodReceivedNote) {
        return goodReceivedNoteDao.save(goodReceivedNote);
    }

    @Override
    public boolean delete(Integer id) {
        goodReceivedNoteDao.deleteById(id);
        return true;
    }

    @Override
    public List<GoodReceivedNote> search(GoodReceivedNote goodReceivedNote) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<GoodReceivedNote> goodReceivedNoteExample = Example.of(goodReceivedNote, matcher);
        return goodReceivedNoteDao.findAll(goodReceivedNoteExample);
    }
}
