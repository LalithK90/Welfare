package lk.avsec_welfare.asset.censure_file.dao;


import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.censure_file.entity.CensureFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CensureFilesDao extends JpaRepository<CensureFiles, Integer > {
    List< CensureFiles > findByCensureOrderByIdDesc(Censure censure);

    CensureFiles findByName(String filename);

    CensureFiles findByNewName(String filename);

    CensureFiles findByNewId(String filename);

    CensureFiles findByCensure(Censure censure);
}
