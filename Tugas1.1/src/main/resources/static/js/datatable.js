$(document).ready( function () {
	 var table = $('#tableDaftarJabatan').DataTable({
			"sAjaxSource": "/tabledaftarjabatan",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    { "mData": "nama"},
		      { "mData": "deskripsi" },
				  { "mData": "gaji_pokok" },
			]
	 })
});