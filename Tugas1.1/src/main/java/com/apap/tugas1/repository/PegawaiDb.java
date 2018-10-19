package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	List<PegawaiModel> findByInstansi(InstansiModel Instansi);
	
	PegawaiModel findByNip(String Nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirDesc(InstansiModel instansi);
	
	List<PegawaiModel> findByInstansiAndJabatan(@Nullable InstansiModel instansi, @Nullable JabatanModel jabatan);
	
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
}