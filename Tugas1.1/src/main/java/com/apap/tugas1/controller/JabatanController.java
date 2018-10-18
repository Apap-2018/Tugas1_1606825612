package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	//fitur 5 menambah jabatan
	@RequestMapping(value = "/jabatan/tambah")
	private String tambahJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan", jabatan);
		return "tambah-jabatan";
	}
	
	//fitur 5 menambah jabatan untuk POST agar bisa diambil datanya
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "success";
	}
	
	/**fitur 6 menampilkan data suatu jabatan GET agar bisa diambil dari list jabatan
	yang dibuat pada jabatan service**/
	@RequestMapping(value="/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="idJabatan") String id, Model model) {
		JabatanModel infoJabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		model.addAttribute("infoJabatan", infoJabatan);
		return "detail-jabatan";
	}
	
	//fitur 9 menampilkan daftar jabatan
	@RequestMapping(value="/jabatan/viewAll")
	private String viewAllJabatan(Model model) {
		List<JabatanModel> daftarJabatan = jabatanService.getAll();
		model.addAttribute("daftarJabatan", daftarJabatan);
		return "daftar-jabatan";
	}
	
	//fitur 9 menampilkan daftar jabatan buat datatable.jsnya
	@RequestMapping(path="/tabledaftarjabatan", method=RequestMethod.GET)
	public @ResponseBody List<JabatanModel> getTableDaftarJabatan() {
		return jabatanService.getAll();
	}
	
	//fitur 7 mengubah data jabatan GET untuk mengambil datanya dari database
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	private String ngubahJabatan(@RequestParam(value="idJabatan") String id, Model model) {
		JabatanModel infoJabatan = jabatanService.getJabatanById(Long.parseLong(id)).get();
		model.addAttribute("infoJabatan", infoJabatan);
		return "ubah-jabatan";
	}
	
	//fitur 7 mengubah data jabatan POST untuk mengubahnya di database
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String ngubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "success";
	}
}
