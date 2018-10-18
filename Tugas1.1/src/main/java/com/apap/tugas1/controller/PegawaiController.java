package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
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
	
	@RequestMapping(value="/pegawai/termuda-tertua")
	private String findPegawaiTermudaTertuda(@RequestParam(value="idInstansi") String id, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(Long.parseLong(id)).get();
		PegawaiModel pegawaiTermuda = pegawaiService.getPegawaiTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(instansi);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "pegawai-ter";
	}
}
