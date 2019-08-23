package com.tax.verify.jpa;

import com.tax.verify.dto.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IndexRepository extends JpaRepository<Data, String> {

    //Tckn ile yapılan sorgudan donen değerleri update eder.
    @Modifying
    @Query("UPDATE Data c SET c.tckn =:tckn, c.unvan=:unvan, c.vdkodu=:vdkodu, c.vkn=:vkn, c.durum_text=:durum_text, tarih=current_timestamp where c.oid=:oid")
    void update(@Param("oid") String oid,@Param("tckn") String tckn,@Param("unvan") String unvan,@Param("vdkodu") String vdkodu, @Param("vkn") String vkn, @Param("durum_text") String durum_text);


}