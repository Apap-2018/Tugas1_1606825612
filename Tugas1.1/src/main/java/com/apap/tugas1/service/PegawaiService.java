package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;


public interface PegawaiService {
	void addPegawai (PegawaiModel pegawai);
	void deletePegawai (PegawaiModel pegawai);
	void updatePegawai (PegawaiModel pegawai);
	Optional<PegawaiModel> getPegawaiDetailById(Long id);
	PegawaiModel getPegawaiByNip(String nip);
	long hitungGaji(PegawaiModel pegawai);
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);
}