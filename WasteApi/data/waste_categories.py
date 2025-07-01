from model.waste_categories import WasteCategory

WasteCategories = [
    WasteCategory (
        id = 1,
        name = "Baterai",
        description = "Termasuk dalam kategori Limbah B3 (Bahan Berbahaya dan Beracun). Tidak boleh dibuang sembarangan karena mengandung logam berat seperti merkuri dan kadmium yang dapat mencemari tanah dan air.",
        icon_url = "https://www.cohenusa.com/wp-content/uploads/2020/04/battery-recycling.jpg",
        examples = [
            "Baterai AA/AAA",
            "Baterai kancing (jam tangan)",
            "Baterai ponsel bekas",
            "Power bank rusak"
        ],
        recycling_info = "Kumpulkan secara terpisah. Jangan dicampur dengan sampah rumah tangga. Buang di dropbox atau pusat pengumpulan sampah elektronik (e-waste) yang tersedia di beberapa tempat."
    ),
    WasteCategory (
        id = 2,
        name = "Organik / Hayati",
        description = "Sampah yang berasal dari sisa makhluk hidup dan dapat terurai (membusuk) secara alami oleh mikroorganisme. Sangat bermanfaat jika diolah menjadi kompos.",
        icon_url = "https://www.vanguardrenewables.com/_next/image?url=https%3A%2F%2Fwww.datocms-assets.com%2F97698%2F1712773428-shutterstock_1056730958.jpg%3Ffit%3Dclamp&w=3840&q=75",
        examples = [
            "Sisa makanan dan masakan",
            "Kulit buah dan sayuran",
            "Cangkang telur",
            "Daun kering dan potongan rumput",
            "Ampas kopi/teh"
    ],
        recycling_info = "Olah menjadi pupuk kompos menggunakan komposter atau lubang biopori. Ini akan mengurangi volume sampah di TPA dan menyuburkan tanaman."
    ),
    WasteCategory (
        id = 3,
        name = "Kardus",
        description = "Jenis kertas tebal yang biasa digunakan untuk pengemasan. Merupakan salah satu material yang paling mudah dan sering didaur ulang.",
        icon_url = "https://cleanaway2stor.blob.core.windows.net/cleanaway2-blob-container/2019/06/Cardboard.jpg",
        examples = [
            "Kardus paket online",
            "Kotak sereal",
            "Kotak sepatu",
            "Karton gelombang pembungkus"
        ],
        recycling_info = "Lipat atau pipihkan untuk menghemat ruang. Pastikan dalam kondisi kering dan tidak terkontaminasi minyak atau sisa makanan."
    ),
    WasteCategory (
        id = 4,
        name = "Pakaian",
        description = "Limbah tekstil berupa pakaian, kain, atau produk garmen lainnya yang sudah tidak terpakai. Industri fashion adalah salah satu penghasil limbah terbesar.",
        icon_url = "https://www.coventry.ac.uk/contentassets/e0764d99a985459fab1c995b519ed545/image4jo5.png",
        examples = [
            "Baju bekas",
            "Celana sobek",
            "Kain perca",
            "Handuk bekas",
            "Sprei usang"
        ],
        recycling_info = "Donasikan pakaian yang masih layak pakai. Pakaian yang sudah rusak dapat diubah menjadi kain lap, keset, atau diserahkan ke fasilitas daur ulang tekstil."
    ),
    WasteCategory (
        id = 5,
        name = "Kaca",
        description = "Sampah anorganik yang dapat didaur ulang secara terus-menerus tanpa mengurangi kualitasnya. Proses daur ulang kaca menghemat banyak energi.",
        icon_url = "https://www.wastemanaged.co.uk/wp-content/uploads/2024/07/Untitled-design-13-1.jpg",
        examples = [
            "Botol sirup",
            "Botol kecap",
            "Toples selai",
            "Botol minuman kaca",
            "Pecahan kaca jendela"
        ],
        recycling_info = "Bersihkan sisa produk di dalamnya. Lepaskan tutup botol yang bukan dari kaca. Jika memungkinkan, pisahkan berdasarkan warna (bening, hijau, coklat)."
    ),
    WasteCategory (
        id = 6,
        name = "Logam",
        description = "Sampah berbahan logam, baik besi maupun aluminium. Memiliki nilai ekonomis yang tinggi jika didaur ulang.",
        icon_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3WPKUC_QABSMEV8SjvcYgY8uMVo3u0og5QA&s",
        examples = [
            "Kaleng minuman soda",
            "Kaleng sarden",
            "Tutup botol logam",
            "Aluminium foil bersih",
            "Kaleng biskuit"
        ],
        recycling_info = "Bersihkan kaleng dari sisa makanan. Remukkan atau pipihkan kaleng untuk menghemat tempat. Kumpulkan dan bisa dijual ke pengepul barang bekas."
    ),
    WasteCategory (
        id = 7,
        name = "Kertas",
        description = "Sampah yang berasal dari serat kayu atau bahan nabati lainnya. Hindari membuang kertas yang masih bisa dipakai di kedua sisinya.",
        icon_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsdd8KEOTIBE037hDzfPEQJwgYfa08_OI3Ng&s",
        examples = [
            "Koran bekas",
            "Kertas HVS bekas print",
            "Majalah dan brosur",
            "Buku tulis",
            "Amplop"
        ],
        recycling_info = "Pastikan kertas tidak basah, berminyak, atau terkontaminasi. Kertas seperti struk thermal atau kertas berlaminasi plastik tidak dapat didaur ulang."
    ),
    WasteCategory (
        id = 8,
        name = "Plastik",
        description = "Sampah anorganik yang paling umum ditemui dan membutuhkan ratusan tahun untuk terurai. Menjadi sumber pencemaran utama di darat dan laut.",
        icon_url = "https://www.unisort.co.uk/app/uploads/plastic-bottle-waste.png",
        examples = [
            "Botol air mineral (PET)",
            "Gelas plastik (PP)",
            "Kantung kresek (HDPE)",
            "Wadah sampo/sabun",
            "Kemasan makanan ringan"
        ],
        recycling_info = "Bersihkan dari sisa kotoran. Lepaskan label dan tutup botol. Jika memungkinkan, kenali jenis plastik (lihat kode segitiga) untuk pemilahan yang lebih baik."
    ),
    WasteCategory (
        id = 9,
        name = "Sepatu",
        description = "Limbah alas kaki yang materialnya kompleks, terdiri dari karet, kulit, kain, busa, dan plastik, sehingga sulit untuk didaur ulang secara konvensional.",
        icon_url = "https://cdn.shopify.com/s/files/1/0544/0251/5117/files/Frame_1329_600x600.png?v=1711299718",
        examples = [
            "Sepatu olahraga bekas",
            "Sandal jepit putus",
            "Sepatu kulit usang",
            "Sepatu hak tinggi rusak"
        ],
        recycling_info = "Donasikan sepatu yang masih layak pakai. Untuk yang sudah rusak total, beberapa merek atau organisasi memiliki program daur ulang khusus untuk diubah menjadi material lain."
    ),
    WasteCategory (
        id = 10,
        name = "Sampah Residu",
        description = "Sampah sisa yang tidak dapat atau sulit didaur ulang dengan teknologi yang ada saat ini. Jenis sampah inilah yang seharusnya berakhir di TPA (Tempat Pembuangan Akhir).",
        icon_url = "https://www.greeners.co/wp-content/uploads/2019/09/Ecoton-Pemerintah-Diminta-Segera-Tangani-Sampah-Popok-Sekali-Pakai.jpg",
        examples = [
            "Popok sekali pakai",
            "Pembalut wanita",
            "Masker",
            "Sikat gigi bekas",
            "Styrofoam kotor bekas makanan"
        ],
        recycling_info = "Tidak dapat didaur ulang. Cara terbaik mengelolanya adalah dengan mengurangi penggunaannya (reduce). Pastikan hanya sampah jenis ini yang Anda buang ke tempat sampah umum."
    )
]