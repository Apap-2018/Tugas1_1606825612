package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;


public interface PegawaiService {
	void addPegawai (PegawaiModel pegawai);
	void deletePegawai (PegawaiModel pegawai);
	void updatePegawai(String nip, PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiByNip(String nip);
	long hitungGaji(PegawaiModel pegawai);
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiDanJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	List<PegawaiModel> findByInstansiIdOrInstansiProvinsiIdOrJabatanId(long idInstansi, long idProvinsi, long idJabatan);
}