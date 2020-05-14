package lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.dao;

import lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.entity.GoodReceivedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceivedNoteDao extends JpaRepository<GoodReceivedNote, Integer> {
}
