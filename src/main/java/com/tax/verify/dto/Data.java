package com.tax.verify.dto;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vd_tc_index")
@EntityListeners(AuditingEntityListener.class)
public class Data {
    @Column(name = "vd_sorulan")
    private String vd_vkn;
    @Column(name = "unvan_sorulan")
    private String vd_unvan_sorulan;
    @Column(name = "vd_unvan_donen")
    private String vd_unvan_donen;
    @Column(name = "adres_sorulan")
    private String adres_sorulan;
    @Column(name = "ilce_sorulan")
    private String ilce_sorulan;
    @Column(name = "il_sorulan")
    private String cityName;
    @Column(name = "vd_adres_donen")
    private String vd_adres_donen;
    @Column(name = "vd_vergi_dairesi_kodu")
    private String vd_vdkodu;
    @Column(name = "vd_vergi_dairesi_ilce")
    private String vd_vergi_dairesi_ilce;
    @Column(name = "vd_tc_donen")
    private String vd_tc_donen;
    @Column(name = "vd_fiili_durum_donen")
    private String vd_fiili_durum_donen;
    @Column(name = "tutarlilik")
    private String tutarlilik;
    @Column(name = "tc_sorulan")
    private String tckn;
    @Column(name = "tc_unvan_donen")
    private String unvan;
    @Column(name = "tc_adres_donen")
    private String tc_adres_donen;
    @Column(name = "tc_vergi_dairesi_kodu")
    private String vdkodu;
    @Column(name = "tc_vergi_dairesi_ilce")
    private String tc_vergi_dairesi_ilce;
    @Column(name = "tc_vd_donen")
    private String vkn;
    @Column(name = "tc_fiili_durum_donen")
    private String durum_text;
    @Column(name = "point_status_name")
    private String point_status_name;
    @Column(name = "tarih")
    private Date tarih;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "oid")
    private String oid;
    @Column(name = "plaka")
    private String plaka;
    @Column(name = "lastupdated")
    private Date lastupdated;
    @Transient
    private String durum;
    @Transient
    private String sorgulayantckimlik;

    public String getSorgulayantckimlik() {
        return sorgulayantckimlik;
    }

    public void setSorgulayantckimlik(String sorgulayantckimlik) {
        this.sorgulayantckimlik = sorgulayantckimlik;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getTc_adres_donen() {
        return tc_adres_donen;
    }

    public void setTc_adres_donen(String tc_adres_donen) {
        this.tc_adres_donen = tc_adres_donen;
    }

    public String getTc_vergi_dairesi_ilce() {
        return tc_vergi_dairesi_ilce;
    }

    public void setTc_vergi_dairesi_ilce(String tc_vergi_dairesi_ilce) {
        this.tc_vergi_dairesi_ilce = tc_vergi_dairesi_ilce;
    }

    public String getPoint_status_name() {
        return point_status_name;
    }

    public void setPoint_status_name(String point_status_name) {
        this.point_status_name = point_status_name;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date tarih) {
        this.lastupdated = lastupdated;
    }

    public String getVd_vkn() {
        return vd_vkn;
    }

    public void setVd_vkn(String vd_vkn) {
        this.vd_vkn = vd_vkn;
    }

    public String getVd_unvan_sorulan() {
        return vd_unvan_sorulan;
    }

    public void setVd_unvan_sorulan(String vd_unvan_sorulan) {
        this.vd_unvan_sorulan = vd_unvan_sorulan;
    }

    public String getVd_unvan_donen() {
        return vd_unvan_donen;
    }

    public void setVd_unvan_donen(String vd_unvan_donen) {
        this.vd_unvan_donen = vd_unvan_donen;
    }

    public String getAdres_sorulan() {
        return adres_sorulan;
    }

    public void setAdres_sorulan(String adres_sorulan) {
        this.adres_sorulan = adres_sorulan;
    }

    public String getIlce_sorulan() {
        return ilce_sorulan;
    }

    public void setIlce_sorulan(String ilce_sorulan) {
        this.ilce_sorulan = ilce_sorulan;
    }

    public String getVd_adres_donen() {
        return vd_adres_donen;
    }

    public void setVd_adres_donen(String vd_adres_donen) {
        this.vd_adres_donen = vd_adres_donen;
    }

    public String getVd_vdkodu() {
        return vd_vdkodu;
    }

    public void setVd_vdkodu(String vd_vdkodu) {
        this.vd_vdkodu = vd_vdkodu;
    }

    public String getVd_vergi_dairesi_ilce() {
        return vd_vergi_dairesi_ilce;
    }

    public void setVd_vergi_dairesi_ilce(String vd_vergi_dairesi_ilce) {
        this.vd_vergi_dairesi_ilce = vd_vergi_dairesi_ilce;
    }

    public String getVd_tc_donen() {
        return vd_tc_donen;
    }

    public void setVd_tc_donen(String vd_tc_donen) {
        this.vd_tc_donen = vd_tc_donen;
    }

    public String getVd_fiili_durum_donen() {
        return vd_fiili_durum_donen;
    }

    public void setVd_fiili_durum_donen(String vd_fiili_durum_donen) {
        this.vd_fiili_durum_donen = vd_fiili_durum_donen;
    }

    public String getTutarlilik() {
        return tutarlilik;
    }

    public void setTutarlilik(String tutarlilik) {
        this.tutarlilik = tutarlilik;
    }

    public Data() {
    }

    public Data(String vd_vkn, String vd_unvan_sorulan, String vd_unvan_donen, String adres_sorulan, String ilce_sorulan, String cityName, String vd_adres_donen, String vd_vdkodu, String vd_vergi_dairesi_ilce, String vd_tc_donen, String vd_fiili_durum_donen, String tutarlilik, String tckn, String unvan, String tc_adres_donen, String vdkodu, String tc_vergi_dairesi_ilce, String vkn, String durum_text, String point_status_name, Date tarih, String oid, String plaka, String durum, String sorgulayantckimlik) {
        this.vd_vkn = vd_vkn;
        this.vd_unvan_sorulan = vd_unvan_sorulan;
        this.vd_unvan_donen = vd_unvan_donen;
        this.adres_sorulan = adres_sorulan;
        this.ilce_sorulan = ilce_sorulan;
        this.cityName = cityName;
        this.vd_adres_donen = vd_adres_donen;
        this.vd_vdkodu = vd_vdkodu;
        this.vd_vergi_dairesi_ilce = vd_vergi_dairesi_ilce;
        this.vd_tc_donen = vd_tc_donen;
        this.vd_fiili_durum_donen = vd_fiili_durum_donen;
        this.tutarlilik = tutarlilik;
        this.tckn = tckn;
        this.unvan = unvan;
        this.tc_adres_donen = tc_adres_donen;
        this.vdkodu = vdkodu;
        this.tc_vergi_dairesi_ilce = tc_vergi_dairesi_ilce;
        this.vkn = vkn;
        this.durum_text = durum_text;
        this.point_status_name = point_status_name;
        this.lastupdated = lastupdated;
        this.oid = oid;
        this.plaka = plaka;
        this.durum = durum;
        this.sorgulayantckimlik = sorgulayantckimlik;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getDurum_text() {
        return durum_text;
    }

    public void setDurum_text(String durum_text) {
        this.durum_text = durum_text;
    }

    public String getVkn() {
        return vkn;
    }

    public void setVkn(String vkn) {
        this.vkn = vkn;
    }

    public String getVdkodu() {
        return vdkodu;
    }

    public void setVdkodu(String vdkodu) {
        this.vdkodu = vdkodu;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

}
