package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	Optional<JabatanModel> getJabatanById(Long id);
	
	void addJabatan(JabatanModel jabatan);
	
	List<JabatanModel> getAll();
}
