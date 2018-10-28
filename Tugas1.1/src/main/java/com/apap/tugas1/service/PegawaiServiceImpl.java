package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public Optional<PegawaiModel> getPegawaiDetailById(Long id) {
		return pegawaiDb.findById(id);
	}

	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
		
	}

	@Override
	public long hitungGaji(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		List<JabatanPegawaiModel> listJabatan = pegawai.getListJabatanPegawai();
		double gaji = 0.0;
		for(JabatanPegawaiModel jabatan : listJabatan) {
			if(jabatan.getJabatan().getGaji_pokok() > gaji) {
				gaji = jabatan.getJabatan().getGaji_pokok();
			}
		}
		gaji += pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * gaji;
		return (long)gaji;
	}
	
	@Override
	public PegawaiModel getPegawaiTertua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiTertua = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
		return pegawaiTertua.get(0);
	}
	
	@Override
	public PegawaiModel getPegawaiTermuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiTermuda = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
		return pegawaiTermuda.get(pegawaiTermuda.size()-1);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiDanJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndJabatan(instansi, jabatan);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public List<PegawaiModel> findByInstansiIdOrInstansiProvinsiIdOrJabatanId(long idInstansi, long idProvinsi,
			long idJabatan) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiIdOrInstansiProvinsiIdOrJabatanId(idInstansi, idProvinsi, idJabatan);
	}

	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel updatePegawai = pegawaiDb.findByNip(nip);
		updatePegawai.setNama(pegawai.getNama());
		updatePegawai.setNip(pegawai.getNip());
		updatePegawai.setTahunMasuk(pegawai.getTahunMasuk());
		updatePegawai.setJabatan(pegawai.getJabatan());
		updatePegawai.setTanggalLahir(pegawai.getTanggalLahir());
		updatePegawai.setTempatLahir(pegawai.getTempatLahir());
		updatePegawai.setInstansi(pegawai.getInstansi());
		pegawaiDb.save(updatePegawai);
	}

	@Override
	public List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByJabatan(jabatan);
	}

	
	
}