package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String homePage(Model model) {
		List<JabatanModel> listJabatanAvailable = jabatanService.getAll();
		List<InstansiModel> listInstansiAvailable = instansiService.getAll();
		model.addAttribute("listJabatanAvailable", listJabatanAvailable);
		model.addAttribute("listInstansiAvailable", listInstansiAvailable);
		return "home-page";
	}
	
	//fitur 1 menampilkan detail pegawai
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String findPegawai(@RequestParam(value = "nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		long gajiPegawai = pegawaiService.hitungGaji(pegawai);
		if (pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("gajiPegawai", gajiPegawai);
			return "detail-pegawai";
		}
		return "error";
	}
	
	//fitur 10 menampilkan pegawai termuda dan tertua pada suatu instansi
	@RequestMapping(value="/pegawai/termuda-tertua")
	private String findPegawaiTermudaTertuda(@RequestParam(value="idInstansi") String id, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(Long.parseLong(id)).get();
		PegawaiModel pegawaiTermuda = pegawaiService.getPegawaiTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(instansi);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "pegawai-ter";
	}
	
	//fitur 2 menambahkan data pegawai di suatu instansi
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String addPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		List<InstansiModel> listInstansi = instansiService.getAll();
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatan(new ArrayList<JabatanModel>());
		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params= {"addRow"}, method=RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = pegawai.getInstansi().getId().toString();
		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
		String tahunKerja = pegawai.getTahunMasuk();
		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		String strUrutan;
		if(urutan<10) strUrutan="0"+urutan;
		else strUrutan=""+urutan;
		
		String nip = kode + tanggalLahir + tahunKerja + strUrutan;
		
		pegawai.setNip(nip);
		
		pegawaiService.addPegawai(pegawai);
		
		String msg = "Pegawai dengan NIP "+ nip +" berhasil ditambahkan";
		model.addAttribute("message", msg);
		return "success";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam("nip") String nip, Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		
	    model.addAttribute("pegawai", pegawai);
	    return "ubah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		
		pegawai.getJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    return "ubah-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String oldNip = pegawai.getNip();
		PegawaiModel oldPegawai = pegawaiService.getPegawaiByNip(oldNip);
		
		String newNip;
		if((!oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk())) || 
				(!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir())) || 
				(!oldPegawai.getInstansi().equals(pegawai.getInstansi()))) {
			
			String kode = pegawai.getInstansi().getId().toString();
			
			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
			String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
			
			String tahunKerja = pegawai.getTahunMasuk();
			
			int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
			
			String strUrutan;
			if(urutan<10) strUrutan="0"+urutan;
			else strUrutan=""+urutan;
			
			newNip = kode + tanggalLahir + tahunKerja + strUrutan;
			pegawai.setNip(newNip);
		}
		else {
			 newNip = oldNip;
			 pegawai.setNip(oldNip);
		}
		
		
		pegawaiService.updatePegawai(oldNip, pegawai);
		
		String msg = "Pegawai dengan NIP "+ newNip +" berhasil diubah";
		model.addAttribute("message", msg);
		return "success";
	}
	@RequestMapping(value="/pegawai/cari", method=RequestMethod.GET)
	/**private String cariPegawai(@RequestParam(required=false) Optional<Long> idProvinsi, 
			@RequestParam(required=false) Optional<Long> idJabatan,
			@RequestParam(required=false) Optional<Long> idInstansi, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.findByInstansiIdOrInstansiProvinsiIdOrJabatanId(idInstansi, idProvinsi, idJabatan);
		model.addAttribute("listPegawai", listPegawai);**/
		private String cariPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		List<InstansiModel> listInstansi = instansiService.getAll();
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatan(new ArrayList<JabatanModel>());
		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "cari-pegawai";
	}
	
}

